package top.yunshu.shw.server.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import top.yunshu.shw.server.dao.FileDao;
import top.yunshu.shw.server.entity.Config;
import top.yunshu.shw.server.exception.FileException;
import top.yunshu.shw.server.service.config.ConfigService;
import top.yunshu.shw.server.util.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 直接将文件写入到磁盘中
 *
 * @author itning
 */
@Repository
public class SimpleFileDaoImpl implements FileDao {
    private final ConfigService configService;

    @Autowired
    public SimpleFileDaoImpl(ConfigService configService) {
        this.configService = configService;
    }

    /**
     * 创建个目录
     *
     * @param file 路径
     * @throws FileException 目录创建失败或者目录路径是个文件
     */
    private void createDir(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                throw new FileException("所创建的目录存在但是个文件，联系管理员解决此问题", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            boolean mkdir = file.mkdir();
            if (!mkdir) {
                throw new FileException("目录创建失败，联系管理员解决此问题", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @Override
    public void createFile(MultipartFile file, String workId, String studentId, String fileName) {
        Optional<String> pathOption = configService.getConfig(Config.ConfigKey.FILE_REPOSITORY_PATH);
        if (pathOption.isPresent()) {
            String path = pathOption.get();
            try {
                File f = new File(path);
                if (f.isFile()) {
                    throw new RuntimeException("存储目录错误，请联系管理员");
                }
                File workDir = new File(f + File.separator + workId);
                if (!workDir.exists()) {
                    createDir(workDir);
                }
                String extensionName = FileUtils.getExtensionName(file);
                File newFile = new File(workDir + File.separator + fileName + extensionName);
                file.transferTo(newFile);
                if (!newFile.exists()) {
                    throw new RuntimeException("上传失败");
                }
            } catch (Exception e) {
                throw new FileException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            throw new FileException("存储目录不存在，请联系管理员", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean deleteFile(String workId, String studentId, String fullFileName) {
        return configService.getConfig(Config.ConfigKey.FILE_REPOSITORY_PATH)
                .map(path -> new File(path + File.separator + workId + File.separator + fullFileName).delete())
                .orElse(false);
    }

    @Override
    public Optional<File> findFile(String workId, String studentId, String fullFileName) {
        Optional<String> pathOption = configService.getConfig(Config.ConfigKey.FILE_REPOSITORY_PATH);
        if (pathOption.isPresent()) {
            String path = pathOption.get();
            return Optional.of(new File(path + File.separator + workId + File.separator + fullFileName));
        }
        return Optional.empty();
    }

    @Override
    public List<File> getWorkOfFiles(String workId) {
        Optional<String> pathOption = configService.getConfig(Config.ConfigKey.FILE_REPOSITORY_PATH);
        if (pathOption.isPresent()) {
            String path = pathOption.get();
            File workDir = new File(path + File.separator + workId);
            File[] files = workDir.listFiles();
            if (files != null) {
                return Arrays.asList(files);
            } else {
                return new ArrayList<>(0);
            }
        }
        throw new FileException("存储目录不存在，请联系管理员", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
