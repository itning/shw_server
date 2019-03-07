package top.yunshu.shw.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.yunshu.shw.server.entity.Notice;

import java.util.List;

/**
 * 通知Dao
 *
 * @author itning
 */
public interface NoticeDao extends JpaRepository<Notice, String> {
    /**
     * 根据接收者ID获取所有通知
     *
     * @param receiveId 接收者ID
     * @return 通知集合
     */
    List<Notice> findByReceiveId(String receiveId);
}
