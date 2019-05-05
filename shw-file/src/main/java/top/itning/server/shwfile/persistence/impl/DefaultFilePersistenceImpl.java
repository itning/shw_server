package top.itning.server.shwfile.persistence.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import top.itning.server.common.exception.FileException;
import top.itning.server.shwfile.persistence.FilePersistence;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author itning
 * @date 2019/5/3 11:56
 */
@Component
public class DefaultFilePersistenceImpl implements FilePersistence {
    private static final Logger logger = LoggerFactory.getLogger(DefaultFilePersistenceImpl.class);
    private String dir = "C:\\Users\\wangn\\Desktop";

    @Override
    public boolean file2disk(MultipartFile file, String workId, String studentNumber) {
        File saveFile = new File(createDirIfNoneExists(dir + File.separator + workId) + File.separator + studentNumber);
        try {
            file.transferTo(saveFile);
            return saveFile.exists() && saveFile.length() == file.getSize();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean fileDel(String workId, String studentNumber) {
        File saveFile = new File(dir + File.separator + workId + File.separator + studentNumber);
        return saveFile.delete();
    }

    @Override
    public Optional<File> getFile(String workId, String studentNumber) {
        File saveFile = new File(dir + File.separator + workId + File.separator + studentNumber);
        if (!saveFile.exists()) {
            return Optional.empty();
        }
        if (!saveFile.canRead()) {
            return Optional.empty();
        }
        return Optional.of(saveFile);
    }

    @Override
    public List<File> getFiles(String workId) {
        File workDir = new File(dir + File.separator + workId);
        File[] files = workDir.listFiles();
        if (files != null) {
            return Arrays.asList(files);
        } else {
            return Collections.emptyList();
        }
    }

    private File createDirIfNoneExists(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            boolean mkdir = file.mkdir();
            if (!mkdir) {
                logger.error("create dir failure and dir path: {}", dirPath);
                throw new FileException("创建作业文件夹失败", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return file;
        }
        if (file.isFile()) {
            boolean delete = file.delete();
            if (delete) {
                boolean mkdir = file.mkdir();
                if (!mkdir) {
                    logger.error("create dir failure and dir path: {}", dirPath);
                    throw new FileException("创建作业文件夹失败", HttpStatus.INTERNAL_SERVER_ERROR);
                }
                return file;
            } else {
                logger.error("del file failure and dir path: {}", dirPath);
                throw new FileException("创建作业文件夹失败", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return file;
        }
    }
}
