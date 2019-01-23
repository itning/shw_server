package top.yunshu.shw.server.service.upload;

import org.springframework.web.multipart.MultipartFile;
import top.yunshu.shw.server.entity.Upload;

/**
 * 上传历史服务
 *
 * @author itning
 */
public interface UploadService {
    /**
     * 根据作业ID获取上传信息
     *
     * @param studentId 学生学号
     * @param workId    作业ID
     * @return 上传
     */
    Upload getUploadInfoByWorkId(String studentId, String workId);

    /**
     * 根据作业ID 删除作业
     * 作业必须是开启状态
     *
     * @param studentId 学生学号
     * @param workId    作业ID
     */
    void delUploadInfoByWorkId(String studentId, String workId);

    /**
     * 上传文件
     *
     * @param file          {@link MultipartFile}
     * @param studentNumber 学号
     * @param workId        作业ID
     */
    void uploadFile(MultipartFile file, String studentNumber, String workId);
}
