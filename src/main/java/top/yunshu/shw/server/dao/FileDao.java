package top.yunshu.shw.server.dao;

import org.springframework.web.multipart.MultipartFile;
import top.yunshu.shw.server.exception.FileException;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * 文件CRUD操作规范接口
 *
 * @author itning
 */
public interface FileDao {
    /**
     * 持久化文件（必须成功）
     *
     * @param file      {@link MultipartFile}
     * @param workId    作业ID
     * @param studentId 学号
     * @param fileName  文件名（不含扩展名）
     * @throws FileException 文件创建失败
     */
    void createFile(MultipartFile file, String workId, String studentId, String fileName);

    /**
     * 删除一个文件
     *
     * @param workId       作业ID
     * @param studentId    学号
     * @param fullFileName 文件名（包含扩展名）
     * @return 是否删除成功
     */
    boolean deleteFile(String workId, String studentId, String fullFileName);

    /**
     * 查找一个文件，可能不存在
     *
     * @param workId       作业ID
     * @param studentId    学号
     * @param fullFileName 文件名（包含扩展名）
     * @return 文件
     */
    Optional<File> findFile(String workId, String studentId, String fullFileName);

    /**
     * 根据作业ID获取所有作业文件
     *
     * @param workId 作业ID
     * @return 所有作业文件集合
     */
    List<File> getWorkOfFiles(String workId);
}
