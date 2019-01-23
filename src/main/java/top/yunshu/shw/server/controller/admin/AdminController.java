package top.yunshu.shw.server.controller.admin;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import top.yunshu.shw.server.entity.Config;
import top.yunshu.shw.server.service.config.ConfigService;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 管理员控制层
 *
 * @author itning
 * @date 2018/12/21
 */
@Controller
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final ConfigService configService;

    @Autowired
    public AdminController(ConfigService configService) {
        this.configService = configService;
    }

    @GetMapping("/config")
    public String index(Model model) {
        configService.getConfig(Config.ConfigKey.FILE_REPOSITORY_PATH).ifPresent(s -> {
            try {
                File file = new File(s);
                if (file.isDirectory()) {
                    model.addAttribute("freeSpace", file.getFreeSpace());
                } else {
                    model.addAttribute("freeSpace", -1L);
                }
            } catch (Exception e) {
                model.addAttribute("freeSpace", -1L);
            }
        });
        Map<String, String> map = new HashMap<>(16);
        configService.getAllConfigs().forEach(config -> map.put(config.getName(), config.getValue()));
        model.addAllAttributes(map);
        return "config";
    }

    @PostMapping("/config/filePath")
    public String saveFilePath(String name) {
        if (StringUtils.isNotBlank(name)) {
            configService.saveConfig(Config.ConfigKey.FILE_REPOSITORY_PATH, name);
        }
        return "redirect:/config";
    }

    @PostMapping("/config/casServerUrl")
    public String saveCasServerUrl(String name) {
        if (StringUtils.isNotBlank(name)) {
            configService.saveConfig(Config.ConfigKey.CAS_SERVER_URL, name);
        }
        return "redirect:/config";
    }

    @PostMapping("/config/casLoginUrl")
    public String saveCasLoginUrl(String name) {
        if (StringUtils.isNotBlank(name)) {
            configService.saveConfig(Config.ConfigKey.CAS_LOGIN_URL, name);
        }
        return "redirect:/config";
    }

    @PostMapping("/config/casLogoutUrl")
    public String saveCasLogoutUrl(String name) {
        if (StringUtils.isNotBlank(name)) {
            configService.saveConfig(Config.ConfigKey.CAS_LOGOUT_URL, name);
        }
        return "redirect:/config";
    }

    @PostMapping("/config/loginSuccessUrl")
    public String saveLoginSuccessUrl(String name) {
        if (StringUtils.isNotBlank(name)) {
            configService.saveConfig(Config.ConfigKey.LOGIN_SUCCESS_URL, name);
        }
        return "redirect:/config";
    }

    @PostMapping("/config/localServerUrl")
    public String saveLocalServerUrl(String name) {
        if (StringUtils.isNotBlank(name)) {
            configService.saveConfig(Config.ConfigKey.LOCAL_SERVER_URL, name);
        }
        return "redirect:/config";
    }
}
