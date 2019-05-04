package top.itning.server.shwfile.persistence;

import org.springframework.web.multipart.MultipartFile;

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
     * @param file         {@link MultipartFile}
     * @param saveFileName 文件名
     * @return 持久化成功返回<code>true</code>
     */
    boolean file2disk(MultipartFile file, String saveFileName);

    /**
     * 删除文件
     *
     * @param saveFileName 文件名
     * @return 删除成功返回<code>true</code>
     */
    boolean fileDel(String saveFileName);
}
