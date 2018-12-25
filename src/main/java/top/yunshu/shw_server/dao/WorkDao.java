package top.yunshu.shw_server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.yunshu.shw_server.entity.Work;

public interface WorkDao extends JpaRepository<Work, String> {
}
