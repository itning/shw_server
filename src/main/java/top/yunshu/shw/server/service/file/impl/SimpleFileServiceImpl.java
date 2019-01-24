package top.yunshu.shw.server.service.file.impl;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.yunshu.shw.server.controller.teacher.TeacherController;
import top.yunshu.shw.server.dao.UploadDao;
import top.yunshu.shw.server.entity.Config;
import top.yunshu.shw.server.entity.Upload;
import top.yunshu.shw.server.exception.FileException;
import top.yunshu.shw.server.exception.NoSuchFiledValueException;
import top.yunshu.shw.server.service.config.ConfigService;
import top.yunshu.shw.server.service.file.FileService;
import top.yunshu.shw.server.util.FileUtils;

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
public class SimpleFileServiceImpl implements FileService {
    private static final Logger logger = LoggerFactory.getLogger(SimpleFileServiceImpl.class);
    private final ConfigService configService;

    private final UploadDao uploadDao;

    @Autowired
    public SimpleFileServiceImpl(ConfigService configService, UploadDao uploadDao) {
        this.configService = configService;
        this.uploadDao = uploadDao;
    }

    @Override
    public void uploadFile(MultipartFile file, String studentNumber, String workId) {
        // Path:
        // FILE_REPOSITORY_PATH/workId/file
        Optional<String> pathOption = configService.getConfig(Config.ConfigKey.FILE_REPOSITORY_PATH);
        if (pathOption.isPresent()) {
            String path = pathOption.get();
            String extensionName = FileUtils.getExtensionName(file);
            try {
                File f = new File(path);
                if (f.isDirectory()) {
                    File workDir = new File(f + File.separator + workId);
                    if (!workDir.exists()) {
                        if (!workDir.mkdir()) {
                            throw new RuntimeException("创建存储目录错误，请联系管理员");
                        }
                    }
                    file.transferTo(new File(workDir + File.separator + studentNumber + extensionName));
                    if (!new File(workDir + File.separator + studentNumber + extensionName).exists()) {
                        throw new RuntimeException("上传失败");
                    }
                    System.gc();
                } else {
                    throw new RuntimeException("存储目录错误，请联系管理员");
                }
            } catch (Exception e) {
                throw new FileException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            throw new FileException("存储目录不存在，请联系管理员", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void delFile(String studentNumber, String workId) {
        if (!uploadDao.existsByStudentIdAndWorkId(studentNumber, workId)) {
            throw new NoSuchFiledValueException("作业不存在", HttpStatus.NOT_FOUND);
        }
        configService.getConfig(Config.ConfigKey.FILE_REPOSITORY_PATH).ifPresent(path -> {
            Upload upload = uploadDao.findUploadByStudentIdAndWorkId(studentNumber, workId);
            //noinspection ResultOfMethodCallIgnored
            new File(path + File.separator + workId + File.separator + studentNumber + upload.getExtensionName()).delete();
        });
    }

    @Override
    public Optional<File> getFile(String studentNumber, String workId) {
        if (!uploadDao.existsByStudentIdAndWorkId(studentNumber, workId)) {
            throw new NoSuchFiledValueException("作业不存在", HttpStatus.NOT_FOUND);
        }
        Optional<String> pathOption = configService.getConfig(Config.ConfigKey.FILE_REPOSITORY_PATH);
        if (pathOption.isPresent()) {
            String path = pathOption.get();
            Upload upload = uploadDao.findUploadByStudentIdAndWorkId(studentNumber, workId);
            return Optional.of(new File(path + File.separator + workId + File.separator + studentNumber + upload.getExtensionName()));
        }
        return Optional.empty();
    }

    @Override
    public List<File> getAllFiles(String workId) {
        Optional<String> config = configService.getConfig(Config.ConfigKey.FILE_REPOSITORY_PATH);
        if (config.isPresent()) {
            String path = config.get();
            File workDir = new File(path + File.separator + workId);
            File[] files = workDir.listFiles();
            if (files != null) {
                return Arrays.asList(files);
            } else {
                throw new FileException("作业未找到", HttpStatus.NOT_FOUND);
            }
        }
        throw new FileException("存储目录不存在，请联系管理员", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Async("asyncServiceExecutor")
    @Override
    public synchronized void unpackFiles(String workId) {
        try {
            long sum = uploadDao.findSizeByWorkId(workId).stream().mapToLong(Long::longValue).sum();
            String tempFilePath = System.getProperty("java.io.tmpdir") + File.separator + workId + sum + ".zip";
            if (!new File(tempFilePath).exists()) {
                File tempDir = new File(System.getProperty("java.io.tmpdir"));
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
                        logger.error("临时目录剩余: " + (tempDir.getFreeSpace() / 1024 / 1024) + "MB空间");
                        logger.error("临时目录太小,需要 " + (filesSize / 1024 / 1024) + "MB空间");
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
                logger.debug("spend: " + (System.currentTimeMillis() - start) / 1000 + "s");
                zipOut.flush();
                zipOut.close();
                TeacherController.packMap.put(workId, "OK");
            } else {
                TeacherController.packMap.put(workId, "OK");
            }
        } catch (IOException e) {
            TeacherController.packMap.put(workId, "ERROR:" + e.getMessage());
            throw new FileException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
