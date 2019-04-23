package top.yunshu.shw.server.service.file.impl;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import top.yunshu.shw.server.controller.teacher.TeacherController;
import top.yunshu.shw.server.dao.FileDao;
import top.yunshu.shw.server.dao.StudentDao;
import top.yunshu.shw.server.dao.UploadDao;
import top.yunshu.shw.server.dao.WorkDao;
import top.yunshu.shw.server.entity.Config;
import top.yunshu.shw.server.exception.FileException;
import top.yunshu.shw.server.exception.NoSuchFiledValueException;
import top.yunshu.shw.server.service.config.ConfigService;
import top.yunshu.shw.server.service.file.FileService;
import top.yunshu.shw.server.util.FileNameSpecificationUtils;

import javax.transaction.Transactional;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 简单文件服务实现
 *
 * @author itning
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class FileServiceImpl implements FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    private final ConfigService configService;

    private final UploadDao uploadDao;

    private final FileDao fileDao;

    private final StudentDao studentDao;

    private final WorkDao workDao;

    @Autowired
    public FileServiceImpl(ConfigService configService, UploadDao uploadDao, FileDao fileDao, StudentDao studentDao, WorkDao workDao) {
        this.configService = configService;
        this.uploadDao = uploadDao;
        this.fileDao = fileDao;
        this.studentDao = studentDao;
        this.workDao = workDao;
    }

    @Override
    public Optional<File> getFile(String studentNumber, String workId) {
        if (!uploadDao.existsByStudentIdAndWorkId(studentNumber, workId)) {
            throw new NoSuchFiledValueException("作业不存在", HttpStatus.NOT_FOUND);
        }
        String[] safeString = FileNameSpecificationUtils.safeGetStudentNameAndFileNameFormat(studentDao, workDao, studentNumber, workId);
        String extensionName = uploadDao.findExtensionNameByStudentIdAndWorkId(studentNumber, workId);
        String fileName = FileNameSpecificationUtils.getFileName(studentNumber, safeString[0], safeString[1]);
        return fileDao.findFile(workId, studentNumber, fileName + extensionName);
    }

    @Override
    public List<File> getAllFiles(String workId) {
        return fileDao.getWorkOfFiles(workId);
    }

    @Async("asyncServiceExecutor")
    @Override
    public synchronized void unpackFiles(String workId) {
        try {
            long sum = uploadDao.findSizeByWorkId(workId).stream().mapToLong(Long::longValue).sum();
            String temp = configService.getConfig(Config.ConfigKey.TEMP_DIR).orElse(System.getProperty("java.io.tmpdir"));
            String tempFilePath = temp + File.separator + workId + sum + ".zip";
            if (!new File(tempFilePath).exists()) {
                File tempDir = new File(temp);
                List<File> fileList = this.getAllFiles(workId);
                long filesSize = fileList.stream().mapToLong(File::length).sum();
                if (tempDir.getFreeSpace() < filesSize) {
                    try {
                        //noinspection ConstantConditions,ResultOfMethodCallIgnored
                        Arrays.stream(tempDir.listFiles()).forEach(File::delete);
                    } catch (Exception e) {
                        //
                    }
                    if (tempDir.getFreeSpace() < filesSize) {
                        logger.error("临时目录剩余: {} MB空间", (tempDir.getFreeSpace() / 1024 / 1024));
                        logger.error("临时目录太小,需要 {} MB空间", (filesSize / 1024 / 1024));
                        TeacherController.packMap.put(workId, "ERROR:" + "临时目录太小,需要 " + (filesSize / 1024 / 1024) + "MB空间");
                        throw new FileException("临时目录太小,需要 " + (filesSize / 1024 / 1024) + "MB空间", HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
                TeacherController.packMap.put(workId, "0/" + fileList.size());
                AtomicInteger i = new AtomicInteger();
                ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(tempFilePath));
                long start = System.currentTimeMillis();
                fileList.forEach(file -> {
                    try (InputStream input = new FileInputStream(file)) {
                        zipOut.putNextEntry(new ZipEntry(file.getName()));
                        IOUtils.copy(input, zipOut);
                        TeacherController.packMap.put(workId, i.incrementAndGet() + "/" + fileList.size() + " " + file.getName());
                    } catch (IOException e) {
                        logger.error("file zip error: ", e);
                        TeacherController.packMap.put(workId, "ERROR:" + e.getMessage());
                    }
                });
                logger.debug("spend: {}s", (System.currentTimeMillis() - start) / 1000);
                zipOut.flush();
                zipOut.close();
                TeacherController.packMap.put(workId, "OK");
            } else {
                TeacherController.packMap.put(workId, "OK");
            }
        } catch (Exception e) {
            TeacherController.packMap.put(workId, "ERROR:" + e.getMessage());
            throw new FileException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
