package top.yunshu.shw.server.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;
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
@Api(tags = {"管理员接口"})
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
    @ApiOperation("登陆页面")
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
    @ApiOperation("注销登陆")
    @GetMapping("/config/logout")
    public String logout(@ApiIgnore HttpSession session) {
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
    @ApiOperation("提交登陆方法")
    @PostMapping("/config/login")
    public String doLogin(@ApiParam(value = "用户名", required = true) @RequestParam String username,
                          @ApiParam(value = "密码", required = true) @RequestParam String password,
                          @ApiIgnore Model model,
                          @ApiIgnore HttpSession session) {
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
    @ApiOperation("第一次进入控制面板")
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
    @ApiOperation("提交初始化方法")
    @PostMapping("/config/init")
    public String doInit(@ApiParam(value = "新用户名", required = true) @RequestParam String username,
                         @ApiParam(value = "新密码", required = true) @RequestParam String password,
                         @ApiIgnore HttpSession session) {
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
    @ApiOperation("设置用户名密码")
    @PostMapping("/config/user")
    public String user(@ApiParam(value = "新用户名", required = true) @RequestParam String username,
                       @ApiParam(value = "新密码", required = true) @RequestParam String password,
                       @ApiIgnore HttpSession session) {
        if (session.getAttribute(USER) == null) {
            return "redirect:/config/login";
        }
        if (StringUtils.isNoneBlank(username, password)) {
            configService.saveConfig(Config.ConfigKey.USERNAME, username);
            configService.saveConfig(Config.ConfigKey.PASSWORD, password);
        }
        return "redirect:/config";
    }

    /**
     * 主页
     *
     * @param model   {@link Model}
     * @param session {@link HttpSession}
     * @return ...
     */
    @ApiOperation("控制面板主页")
    @GetMapping("/config")
    public String index(@ApiIgnore Model model, @ApiIgnore HttpSession session) {
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
        String tempDir = configService.getConfig(Config.ConfigKey.TEMP_DIR).orElse(System.getProperty("java.io.tmpdir"));
        model.addAttribute("tempDirPath", tempDir);
        model.addAttribute("tempDirFreeSpace", new File(tempDir).getFreeSpace());
        Map<String, String> map = new HashMap<>(16);
        configService.getAllConfigs().forEach(config -> map.put(config.getName(), config.getValue()));
        model.addAllAttributes(map);
        return "config";
    }

    /**
     * 设置文件目录
     *
     * @param name    目录
     * @param session {@link HttpSession}
     * @return "redirect:/config"
     */
    @ApiOperation("设置文件目录")
    @PostMapping("/config/filePath")
    public String saveFilePath(@ApiParam(value = "文件目录地址", required = true) @RequestParam String name,
                               @ApiIgnore HttpSession session) {
        if (session.getAttribute(USER) == null) {
            return "redirect:/config/login";
        }
        if (StringUtils.isNotBlank(name)) {
            configService.saveConfig(Config.ConfigKey.FILE_REPOSITORY_PATH, name);
        }
        return "redirect:/config";
    }

    /**
     * 设置临时目录
     *
     * @param name    目录
     * @param session {@link HttpSession}
     * @return "redirect:/config"
     */
    @ApiOperation("设置临时目录")
    @PostMapping("/config/tempDir")
    public String saveTempDir(@ApiParam(value = "打包临时目录地址", required = true) String name,
                              @ApiIgnore HttpSession session) {
        if (session.getAttribute(USER) == null) {
            return "redirect:/config/login";
        }
        if (StringUtils.isNotBlank(name)) {
            configService.saveConfig(Config.ConfigKey.TEMP_DIR, name);
        }
        return "redirect:/config";
    }

    /**
     * 设置CAS SERVER
     *
     * @param name    URL
     * @param session {@link HttpSession}
     * @return "redirect:/config"
     */
    @ApiOperation("设置CAS服务器地址")
    @PostMapping("/config/casServerUrl")
    public String saveCasServerUrl(@ApiParam(value = "CAS服务器地址", required = true) @RequestParam String name,
                                   @ApiIgnore HttpSession session) {
        if (session.getAttribute(USER) == null) {
            return "redirect:/config/login";
        }
        if (StringUtils.isNotBlank(name)) {
            configService.saveConfig(Config.ConfigKey.CAS_SERVER_URL, name);
        }
        return "redirect:/config";
    }

    /**
     * 设置CAS LOGIN
     *
     * @param name    URL
     * @param session {@link HttpSession}
     * @return "redirect:/config"
     */
    @ApiOperation("设置CAS登陆地址")
    @PostMapping("/config/casLoginUrl")
    public String saveCasLoginUrl(@ApiParam(value = "CAS登陆地址", required = true) @RequestParam String name,
                                  @ApiIgnore HttpSession session) {
        if (session.getAttribute(USER) == null) {
            return "redirect:/config/login";
        }
        if (StringUtils.isNotBlank(name)) {
            configService.saveConfig(Config.ConfigKey.CAS_LOGIN_URL, name);
        }
        return "redirect:/config";
    }

    /**
     * 设置CAS LOGOUT
     *
     * @param name    URL
     * @param session {@link HttpSession}
     * @return "redirect:/config"
     */
    @ApiOperation("设置CAS登出地址")
    @PostMapping("/config/casLogoutUrl")
    public String saveCasLogoutUrl(@ApiParam(value = "CAS登出地址", required = true) @RequestParam String name,
                                   @ApiIgnore HttpSession session) {
        if (session.getAttribute(USER) == null) {
            return "redirect:/config/login";
        }
        if (StringUtils.isNotBlank(name)) {
            configService.saveConfig(Config.ConfigKey.CAS_LOGOUT_URL, name);
        }
        return "redirect:/config";
    }

    /**
     * 设置CAS LOGIN SUCCESS
     *
     * @param name    URL
     * @param session {@link HttpSession}
     * @return "redirect:/config"
     */
    @ApiOperation("设置CAS登陆成功跳转地址")
    @PostMapping("/config/loginSuccessUrl")
    public String saveLoginSuccessUrl(@ApiParam(value = "CAS登陆成功跳转地址", required = true) @RequestParam String name,
                                      @ApiIgnore HttpSession session) {
        if (session.getAttribute(USER) == null) {
            return "redirect:/config/login";
        }
        if (StringUtils.isNotBlank(name)) {
            configService.saveConfig(Config.ConfigKey.LOGIN_SUCCESS_URL, name);
        }
        return "redirect:/config";
    }

    /**
     * 设置CAS LOCAL SERVER
     *
     * @param name    URL
     * @param session {@link HttpSession}
     * @return "redirect:/config"
     */
    @ApiOperation("设置本地服务器地址")
    @PostMapping("/config/localServerUrl")
    public String saveLocalServerUrl(@ApiParam(value = "本地服务器地址", required = true) @RequestParam String name,
                                     @ApiIgnore HttpSession session) {
        if (session.getAttribute(USER) == null) {
            return "redirect:/config/login";
        }
        if (StringUtils.isNotBlank(name)) {
            configService.saveConfig(Config.ConfigKey.LOCAL_SERVER_URL, name);
        }
        return "redirect:/config";
    }
}
