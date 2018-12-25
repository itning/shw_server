package top.yunshu.shw.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.yunshu.shw.server.entity.Work;

/**
 * @author shulu
 */
public interface WorkDao extends JpaRepository<Work, String> {
}
