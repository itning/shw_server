package top.yunshu.shw.server.service.file.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.yunshu.shw.server.dao.UploadDao;
import top.yunshu.shw.server.entity.Config;
import top.yunshu.shw.server.entity.Upload;
import top.yunshu.shw.server.exception.FileException;
import top.yunshu.shw.server.exception.NoSuchFiledValueException;
import top.yunshu.shw.server.service.config.ConfigService;
import top.yunshu.shw.server.service.file.FileService;
import top.yunshu.shw.server.util.FileUtils;

import java.io.File;
import java.util.Optional;

/**
 * 简单文件服务实现
 *
 * @author itning
 */
@Service
public class SimpleFileServiceImpl implements FileService {
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
        Optional<String> pathOption = configService.getConfig(Config.ConfigKey.FILE_REPOSITORY_PATH);
        if (pathOption.isPresent()) {
            String path = pathOption.get();
            Upload upload = uploadDao.findUploadByStudentIdAndWorkId(studentNumber, workId);
            new File(path + File.separator + workId + File.separator + studentNumber + upload.getExtensionName()).delete();
        }
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
}
