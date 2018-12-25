package top.yunshu.shw_server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.yunshu.shw_server.entity.Work;

/**
 * @author shulu
 */
public interface WorkDao extends JpaRepository<Work, String> {
}
