package top.yunshu.shw.server.service.upload;

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
}
