package top.itning.server.shwfile.persistence;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件持久化
 *
 * @author itning
 * @date 2019/5/3 11:54
 */
public interface FilePersistence {
    boolean file2disk(MultipartFile file,String saveFileName);
}
