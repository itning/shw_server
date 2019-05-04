package top.itning.server.shwnotice.service;

import reactor.core.publisher.Flux;
import top.itning.server.shwnotice.entity.Notice;

/**
 * @author itning
 * @date 2019/5/4 17:00
 */
public interface NoticeService {
    /**
     * 根据学生学号获取所有通知
     *
     * @param studentId 学号
     * @return 所有通知
     */
    Flux<Notice> getAllNoticeByStudentId(String studentId);
}
