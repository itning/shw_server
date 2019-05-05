package top.itning.server.shwfile.persistence;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * 文件持久化
 *
 * @author itning
 * @date 2019/5/3 11:54
 */
public interface FilePersistence {
    /**
     * 将文件持久化
     *
     * @param file          {@link MultipartFile}
     * @param workId        作业ID
     * @param studentNumber 学生学号
     * @return 持久化成功返回<code>true</code>
     */
    boolean file2disk(MultipartFile file, String workId, String studentNumber);

    /**
     * 删除文件
     *
     * @param workId        作业ID
     * @param studentNumber 学生学号
     * @return 删除成功返回<code>true</code>
     */
    boolean fileDel(String workId, String studentNumber);

    /**
     * 查找一个文件
     *
     * @param workId        作业ID
     * @param studentNumber 学生学号
     * @return 文件
     */
    Optional<File> getFile(String workId, String studentNumber);

    /**
     * 查找所有文件
     *
     * @param workId 作业ID
     * @return 文件集合
     */
    List<File> getFiles(String workId);
}
