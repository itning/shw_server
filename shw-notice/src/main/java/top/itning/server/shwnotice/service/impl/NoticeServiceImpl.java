package top.itning.server.shwnotice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import top.itning.server.shwnotice.entity.Notice;
import top.itning.server.shwnotice.repository.NoticeRepository;
import top.itning.server.shwnotice.service.NoticeService;

/**
 * @author itning
 * @date 2019/5/4 17:04
 */
@Service
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;

    @Autowired
    public NoticeServiceImpl(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    @Override
    public Flux<Notice> getAllNoticeByStudentId(String studentId) {
        Notice notice = new Notice();
        notice.setReceiveId(studentId);
        return noticeRepository.findAll(Example.of(notice));
    }
}
