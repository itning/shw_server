package top.yunshu.shw.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.yunshu.shw.server.entity.Config;

/**
 * 配置DAO
 *
 * @author itning
 */
public interface ConfigDao extends JpaRepository<Config, String> {
}
