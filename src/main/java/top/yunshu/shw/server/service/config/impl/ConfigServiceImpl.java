package top.yunshu.shw.server.service.config.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.itning.cas.CasProperties;
import top.yunshu.shw.server.dao.ConfigDao;
import top.yunshu.shw.server.entity.Config;
import top.yunshu.shw.server.service.config.ConfigService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * 配置服务实现
 *
 * @author itning
 */
@Service
public class ConfigServiceImpl implements ConfigService {
    private final ConfigDao configDao;
    private final CasProperties casProperties;

    @Autowired
    public ConfigServiceImpl(ConfigDao configDao, CasProperties casProperties) {
        this.configDao = configDao;
        this.casProperties = casProperties;
        init();
    }

    private void init() {
        if (!configDao.existsById(Config.ConfigKey.CAS_SERVER_URL.getKey())) {
            Config config = new Config();
            config.setName(Config.ConfigKey.CAS_SERVER_URL.getKey());
            config.setValue(casProperties.getServerUrl().toString());
            configDao.saveAndFlush(config);
        }
        if (!configDao.existsById(Config.ConfigKey.CAS_LOGIN_URL.getKey())) {
            Config config = new Config();
            config.setName(Config.ConfigKey.CAS_LOGIN_URL.getKey());
            config.setValue(casProperties.getLoginUrl().toString());
            configDao.saveAndFlush(config);
        }
        if (!configDao.existsById(Config.ConfigKey.CAS_LOGOUT_URL.getKey())) {
            Config config = new Config();
            config.setName(Config.ConfigKey.CAS_LOGOUT_URL.getKey());
            config.setValue(casProperties.getLogoutUrl().toString());
            configDao.saveAndFlush(config);
        }
        if (!configDao.existsById(Config.ConfigKey.LOGIN_SUCCESS_URL.getKey())) {
            Config config = new Config();
            config.setName(Config.ConfigKey.LOGIN_SUCCESS_URL.getKey());
            config.setValue(casProperties.getLoginSuccessUrl().toString());
            configDao.saveAndFlush(config);
        }
        if (!configDao.existsById(Config.ConfigKey.LOCAL_SERVER_URL.getKey())) {
            Config config = new Config();
            config.setName(Config.ConfigKey.LOCAL_SERVER_URL.getKey());
            config.setValue(casProperties.getLocalServerUrl().toString());
            configDao.saveAndFlush(config);
        }
    }

    @Override
    public List<Config> getAllConfigs() {
        return configDao.findAll();
    }

    @Override
    public Optional<String> getConfig(Config.ConfigKey configKey) {
        return configDao.findById(configKey.getKey()).map(Config::getValue);
    }

    @Override
    public void saveConfig(Config.ConfigKey configKey, String value) {
        Config config = new Config();
        config.setName(configKey.getKey());
        config.setValue(value);
        switch (configKey) {
            case CAS_LOGIN_URL:
                casProperties.setLoginUrl(URI.create(value));
                configDao.save(config);
                break;
            case CAS_LOGOUT_URL:
                casProperties.setLogoutUrl(URI.create(value));
                configDao.save(config);
                break;
            case CAS_SERVER_URL:
                casProperties.setServerUrl(URI.create(value));
                configDao.save(config);
                break;
            case LOCAL_SERVER_URL:
                casProperties.setLocalServerUrl(URI.create(value));
                configDao.save(config);
                break;
            case LOGIN_SUCCESS_URL:
                casProperties.setLoginSuccessUrl(URI.create(value));
                configDao.save(config);
                break;
            default:
                configDao.save(config);
        }
    }
}
