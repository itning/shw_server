package top.yunshu.shw.server.service.notice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import top.yunshu.shw.server.dao.NoticeDao;
import top.yunshu.shw.server.entity.Notice;
import top.yunshu.shw.server.exception.NoSuchFiledValueException;
import top.yunshu.shw.server.service.notice.NoticeService;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 通知服务实现类
 *
 * @author itning
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class NoticeServiceImpl implements NoticeService {

    private final NoticeDao noticeDao;

    @Autowired
    public NoticeServiceImpl(NoticeDao noticeDao) {
        this.noticeDao = noticeDao;
    }

    @Override
    public List<Notice> getAllNoticeByStudentId(String studentId) {
        return noticeDao.findByReceiveId(studentId);
    }

    @Override
    public void delNoticeById(String noticeId) {
        if (!noticeDao.existsById(noticeId)) {
            throw new NoSuchFiledValueException("ID: " + noticeId + "不存在", HttpStatus.NOT_FOUND);
        }
        noticeDao.deleteById(noticeId);
    }
}
