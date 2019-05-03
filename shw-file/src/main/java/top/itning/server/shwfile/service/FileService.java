package top.itning.server.shwfile.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author itning
 * @date 2019/5/3 11:16
 */
public interface FileService {
    /**
     * 上传文件
     *
     * @param file          {@link MultipartFile}
     * @param studentNumber 学号
     * @param workId        作业ID
     */
    void uploadFile(MultipartFile file, String studentNumber, String workId);
}
