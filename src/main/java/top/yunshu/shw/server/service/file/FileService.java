package top.yunshu.shw.server.service.file;

import org.springframework.web.multipart.MultipartFile;
import top.yunshu.shw.server.exception.FileException;

import java.io.File;
import java.util.Optional;

/**
 * 文件服务
 *
 * @author itning
 */
public interface FileService {
    /**
     * 上传文件
     *
     * @param file          {@link MultipartFile}
     * @param studentNumber 学号
     * @param workId        作业ID
     * @throws FileException 文件异常
     */
    void uploadFile(MultipartFile file, String studentNumber, String workId);

    /**
     * 删除文件
     *
     * @param studentNumber 学号
     * @param workId        作业ID
     */
    void delFile(String studentNumber, String workId);

    /**
     * 获取文件
     *
     * @param studentNumber 学号
     * @param workId        作业ID
     * @return 获取到的文件
     */
    Optional<File> getFile(String studentNumber, String workId);
}
