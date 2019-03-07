package top.yunshu.shw.server.service.notice;

import top.yunshu.shw.server.entity.Notice;

import java.util.List;

/**
 * 通知服务
 *
 * @author itning
 */
public interface NoticeService {
    /**
     * 根据学生学号获取所有通知
     *
     * @param studentId 学号
     * @return 所有通知
     */
    List<Notice> getAllNoticeByStudentId(String studentId);

    /**
     * 根据通知ID删除通知
     *
     * @param noticeId 通知ID
     */
    void delNoticeById(String noticeId);
}
