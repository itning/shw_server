package top.yunshu.shw.server.service.config;

import top.yunshu.shw.server.entity.Config;

import java.util.List;
import java.util.Optional;

/**
 * 配置服务
 *
 * @author itning
 */
public interface ConfigService {
    /**
     * 获取所有配置
     *
     * @return 配置集合
     */
    List<Config> getAllConfigs();

    /**
     * 获取配置信息
     *
     * @param configKey 键
     * @return 值
     */
    Optional<String> getConfig(Config.ConfigKey configKey);

    /**
     * 保存配置
     *
     * @param configKey 键
     * @param value     值
     */
    void saveConfig(Config.ConfigKey configKey, String value);
}
