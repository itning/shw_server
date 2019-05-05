package top.itning.server.shwfile.service.impl;


import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.itning.server.common.exception.FileException;
import top.itning.server.common.exception.NoSuchFiledValueException;
import top.itning.server.shwfile.client.SecurityClient;
import top.itning.server.shwfile.client.UploadClient;
import top.itning.server.shwfile.client.WorkClient;
import top.itning.server.shwfile.client.entity.Student;
import top.itning.server.shwfile.client.entity.Upload;
import top.itning.server.shwfile.client.entity.Work;
import top.itning.server.shwfile.persistence.FilePersistence;
import top.itning.server.shwfile.pojo.FileUploadMetaData;
import top.itning.server.shwfile.service.FileService;
import top.itning.server.shwfile.util.FileUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author itning
 * @date 2019/5/3 11:16
 */
@Service
public class FileServiceImpl implements FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    private static final String IGNORE_STR = "中止";

    private final FilePersistence filePersistence;
    private final UploadClient uploadClient;
    private final SecurityClient securityClient;
    private final WorkClient workClient;

    public FileServiceImpl(FilePersistence filePersistence, UploadClient uploadClient, SecurityClient securityClient, WorkClient workClient) {
        this.filePersistence = filePersistence;
        this.uploadClient = uploadClient;
        this.securityClient = securityClient;
        this.workClient = workClient;
    }

    @Override
    public void uploadFile(MultipartFile file, String studentNumber, String workId) {
        boolean saved = filePersistence.file2disk(file, workId, studentNumber);
        if (saved) {
            FileUploadMetaData fileUploadMetaData = new FileUploadMetaData();
            fileUploadMetaData.setStudentId(studentNumber);
            fileUploadMetaData.setWorkId(workId);
            fileUploadMetaData.setMime(file.getContentType());
            fileUploadMetaData.setExtensionName(FileUtils.getExtensionName(file));
            fileUploadMetaData.setSize(file.getSize());
            uploadClient.saveOne(fileUploadMetaData);
        } else {
            throw new RuntimeException("未知存储失败");
        }
    }

    @Override
    public void delFile(String studentNumber, String workId) {
        boolean deleted = filePersistence.fileDel(workId, studentNumber);
        if (!deleted) {
            logger.warn("student number {} del work id {} failure", studentNumber, workId);
        }
    }

    @Override
    public void downFile(String studentNumber, String workId, String range, HttpServletResponse response) {
        Upload upload = uploadClient.findOneById(studentNumber + "|" + workId).orElseThrow(() -> new NoSuchFiledValueException("上传信息不存在", HttpStatus.NOT_FOUND));
        Student student = securityClient.findStudentById(studentNumber).orElseGet(() -> {
            Student s = new Student();
            s.setName("未知学生姓名");
            return s;
        });
        File file = filePersistence.getFile(workId, studentNumber).orElseThrow(() -> new NoSuchFiledValueException("作业不存在或已被删除", HttpStatus.NOT_FOUND));
        final String extensionName = upload.getExtensionName();
        final String fileName = student.getName() + studentNumber + extensionName;
        FileUtils.breakpointResume(file, fileName, upload.getMime(), range, response);
    }

    @Override
    public void downFiles(String workId, HttpServletResponse response) {
        Work work = workClient.getOneWorkById(workId).orElseThrow(() -> new NoSuchFiledValueException("作业不存在", HttpStatus.NOT_FOUND));
        List<Upload> uploadList = uploadClient.getAllUploadByWorkId(workId);
        Map<String, Upload> uploadMap = new HashMap<>(uploadList.size());
        uploadList.forEach(upload -> uploadMap.put(upload.getStudentId(), upload));
        String fileName = new String((work.getWorkName() + ".zip").getBytes(), StandardCharsets.ISO_8859_1);
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setContentType("application/octet-stream");
        try (ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream())) {
            filePersistence.getFiles(workId).forEach(file -> {
                try (InputStream input = new FileInputStream(file)) {
                    Student student = securityClient.findStudentById(file.getName()).orElse(new Student());
                    String entryFileName = student.getName() + file.getName() + uploadMap.get(file.getName()).getExtensionName();
                    zipOut.putNextEntry(new ZipEntry(entryFileName));
                    IOUtils.copy(input, zipOut);
                } catch (Exception e) {
                    throw new RuntimeException(e.getClass().getName() + "::" + e.getMessage());
                }
            });
        } catch (Exception e) {
            if (e.getMessage().contains(IGNORE_STR)) {
                return;
            }
            logger.error("Copy File To Zip File Error: ", e);
            throw new FileException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
