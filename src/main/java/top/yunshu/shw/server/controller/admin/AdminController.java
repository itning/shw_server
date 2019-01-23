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

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 管理员控制层
 *
 * @author itning
 * @date 2018/12/21
 */
@Controller
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private static final String USER = "user";

    private final ConfigService configService;

    @Autowired
    public AdminController(ConfigService configService) {
        this.configService = configService;
    }

    /**
     * 登陆页面
     *
     * @return login.html
     */
    @GetMapping("/config/login")
    public String login() {
        //如果用户名不存在
        //重定向到初始化页面
        if (!configService.getConfig(Config.ConfigKey.USERNAME).isPresent()) {
            return "redirect:/config/init";
        }
        return "login";
    }

    /**
     * 注销登陆
     *
     * @param session {@link HttpSession}
     * @return "redirect:/config/login";
     */
    @GetMapping("/config/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(USER);
        session.invalidate();
        return "redirect:/config/login";
    }

    /**
     * 提交登陆方法
     *
     * @param username 用户名
     * @param password 密码
     * @param model    {@link Model}
     * @param session  {@link HttpSession}
     * @return ...
     */
    @PostMapping("/config/login")
    public String doLogin(String username, String password, Model model, HttpSession session) {
        Optional<String> usernameOptional = configService.getConfig(Config.ConfigKey.USERNAME);
        Optional<String> passwordOptional = configService.getConfig(Config.ConfigKey.PASSWORD);
        //如果用户名或者密码不存在
        //重定向到初始化页面
        if (!usernameOptional.isPresent() || !passwordOptional.isPresent()) {
            return "redirect:/config/init";
        }
        String u = usernameOptional.get();
        String p = passwordOptional.get();
        if (u.equals(username)) {
            if (p.equals(password)) {
                session.setAttribute(USER, username);
                //重定向到主页
                return "redirect:/config";
            } else {
                model.addAttribute("error", "密码错误");
                //返回登陆页面
                return "login";
            }
        } else {
            //返回登陆页面
            model.addAttribute("error", "用户不存在");
            return "login";
        }
    }

    /**
     * 初始化页面
     *
     * @return init.html
     */
    @GetMapping("/config/init")
    public String init() {
        Optional<String> usernameOptional = configService.getConfig(Config.ConfigKey.USERNAME);
        Optional<String> passwordOptional = configService.getConfig(Config.ConfigKey.PASSWORD);
        if (usernameOptional.isPresent() && passwordOptional.isPresent()) {
            return "redirect:/config/login";
        }
        return "init";
    }

    /**
     * 提交初始化方法
     *
     * @param username 用户名
     * @param password 密码
     * @param session  {@link HttpSession}
     * @return ...
     */
    @PostMapping("/config/init")
    public String doInit(String username, String password, HttpSession session) {
        if (StringUtils.isNoneBlank(username, password)) {
            configService.saveConfig(Config.ConfigKey.USERNAME, username);
            configService.saveConfig(Config.ConfigKey.PASSWORD, password);
            session.setAttribute(USER, username);
            return "redirect:/config";
        }
        //重定向到初始化页面
        return "redirect:/config/init";
    }

    /**
     * 设置用户名密码
     *
     * @param username 用户名
     * @param password 密码
     * @param session  {@link HttpSession}
     * @return ...
     */
    @PostMapping("/config/user")
    public String user(String username, String password, HttpSession session) {
        if (session.getAttribute(USER) == null) {
            return "redirect:/config/login";
        }
        if (StringUtils.isNoneBlank(username, password)) {
            configService.saveConfig(Config.ConfigKey.USERNAME, username);
            configService.saveConfig(Config.ConfigKey.PASSWORD, password);
        }
        return "redirect:/config";
    }

    @GetMapping("/config")
    public String index(Model model, HttpSession session) {
        if (session.getAttribute(USER) == null) {
            return "redirect:/config/login";
        }
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
    public String saveFilePath(String name, HttpSession session) {
        if (session.getAttribute(USER) == null) {
            return "redirect:/config/login";
        }
        if (StringUtils.isNotBlank(name)) {
            configService.saveConfig(Config.ConfigKey.FILE_REPOSITORY_PATH, name);
        }
        return "redirect:/config";
    }

    @PostMapping("/config/casServerUrl")
    public String saveCasServerUrl(String name, HttpSession session) {
        if (session.getAttribute(USER) == null) {
            return "redirect:/config/login";
        }
        if (StringUtils.isNotBlank(name)) {
            configService.saveConfig(Config.ConfigKey.CAS_SERVER_URL, name);
        }
        return "redirect:/config";
    }

    @PostMapping("/config/casLoginUrl")
    public String saveCasLoginUrl(String name, HttpSession session) {
        if (session.getAttribute(USER) == null) {
            return "redirect:/config/login";
        }
        if (StringUtils.isNotBlank(name)) {
            configService.saveConfig(Config.ConfigKey.CAS_LOGIN_URL, name);
        }
        return "redirect:/config";
    }

    @PostMapping("/config/casLogoutUrl")
    public String saveCasLogoutUrl(String name, HttpSession session) {
        if (session.getAttribute(USER) == null) {
            return "redirect:/config/login";
        }
        if (StringUtils.isNotBlank(name)) {
            configService.saveConfig(Config.ConfigKey.CAS_LOGOUT_URL, name);
        }
        return "redirect:/config";
    }

    @PostMapping("/config/loginSuccessUrl")
    public String saveLoginSuccessUrl(String name, HttpSession session) {
        if (session.getAttribute(USER) == null) {
            return "redirect:/config/login";
        }
        if (StringUtils.isNotBlank(name)) {
            configService.saveConfig(Config.ConfigKey.LOGIN_SUCCESS_URL, name);
        }
        return "redirect:/config";
    }

    @PostMapping("/config/localServerUrl")
    public String saveLocalServerUrl(String name, HttpSession session) {
        if (session.getAttribute(USER) == null) {
            return "redirect:/config/login";
        }
        if (StringUtils.isNotBlank(name)) {
            configService.saveConfig(Config.ConfigKey.LOCAL_SERVER_URL, name);
        }
        return "redirect:/config";
    }
}
