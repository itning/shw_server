package top.itning.server.shwupload.service;

import reactor.core.publisher.Mono;
import top.itning.server.shwupload.entity.Upload;

/**
 * @author itning
 * @date 2019/5/2 16:53
 */
public interface UploadService {
    /**
     * 根据作业ID获取上传信息
     *
     * @param studentId 学生学号
     * @param workId    作业ID
     * @return 上传
     */
    Mono<Upload> getUploadInfoByWorkId(String studentId, String workId);

    /**
     * 根据作业ID 删除作业
     * 作业必须是开启状态
     *
     * @param studentId 学生学号
     * @param workId    作业ID
     * @return 操作完成后的信号
     */
    Mono<Void> delUploadInfoByWorkId(String studentId, String workId);

    /**
     * 获取批阅信息
     *
     * @param workId    作业ID
     * @param studentId 学生ID
     * @return 批阅信息
     */
    Mono<String> reviewWork(String studentId, String workId);
}
