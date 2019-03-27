package top.yunshu.shw.server.service.file;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * 文件服务
 *
 * @author itning
 */
public interface FileService {
    /**
     * 获取文件
     *
     * @param studentNumber 学号
     * @param workId        作业ID
     * @return 获取到的文件
     */
    Optional<File> getFile(String studentNumber, String workId);

    /**
     * 获取所有文件集合
     *
     * @param workId 作业ID
     * @return 文件集合
     */
    List<File> getAllFiles(String workId);

    /**
     * 打包文件
     *
     * @param workId 作业ID
     * @deprecated 存在线程安全问题
     */
    @Deprecated
    void unpackFiles(String workId);
}
