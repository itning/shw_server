package top.yunshu.shw.server.util;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件工具类
 *
 * @author itning
 */
public class FileUtils {
    private FileUtils() {
    }

    /**
     * 获取文件扩展名
     *
     * @param file {@link MultipartFile}
     * @return 文件扩展名
     */
    public static String getExtensionName(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String extensionName = "";
        if (originalFilename != null) {
            int i = originalFilename.lastIndexOf(".");
            if (i != -1) {
                extensionName = file.getOriginalFilename().substring(i);
            }
        }
        return extensionName;
    }
}
