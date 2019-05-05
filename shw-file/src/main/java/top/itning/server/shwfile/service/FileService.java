package top.itning.server.shwfile.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

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

    /**
     * 删除文件
     *
     * @param studentNumber 学号
     * @param workId        作业ID
     */
    void delFile(String studentNumber, String workId);

    /**
     * 下载文件
     *
     * @param studentNumber 学号
     * @param workId        作业ID
     * @param range         {@link org.springframework.http.HttpHeaders#RANGE}
     * @param response      {@link HttpServletResponse}
     */
    void downFile(String studentNumber, String workId, String range, HttpServletResponse response);

    /**
     * 下载所有作业
     *
     * @param workId   作业ID
     * @param response {@link HttpServletResponse}
     */
    void downFiles(String workId, HttpServletResponse response);
}
