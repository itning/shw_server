package top.yunshu.shw.server.cas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import top.itning.cas.CasProperties;
import top.itning.cas.callback.AbstractCasCallBackImpl;
import top.yunshu.shw.server.dao.StudentDao;
import top.yunshu.shw.server.entity.LoginUser;
import top.yunshu.shw.server.entity.RestModel;
import top.yunshu.shw.server.entity.Student;
import top.yunshu.shw.server.util.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;

/**
 * Jwt实现
 *
 * @author itning
 */
@Component
public class JwtCasCallBackImpl extends AbstractCasCallBackImpl {
    private static final Logger logger = LoggerFactory.getLogger(JwtCasCallBackImpl.class);
    private static final String LOGIN_NAME = "loginName";
    private static final String STUDENT_USER_TYPE = "99";

    private final StudentDao studentDao;

    @Autowired
    public JwtCasCallBackImpl(CasProperties casProperties, StudentDao studentDao) {
        super(casProperties);
        this.studentDao = studentDao;
    }

    @Override
    public void onLoginSuccess(HttpServletResponse resp, HttpServletRequest req, Map<String, String> attributesMap) throws IOException {
        if (attributesMap.isEmpty()) {
            sendRefresh2Response(resp);
            return;
        }
        LoginUser loginUser = map2userLoginEntity(attributesMap);
        if (STUDENT_USER_TYPE.equals(loginUser.getUserType())) {
            Student student = new Student();
            student.setNo(loginUser.getNo());
            student.setLoginName(loginUser.getLoginName());
            student.setName(loginUser.getName());
            student.setClazzId(loginUser.getClazzId());
            studentDao.saveAndFlush(student);
        }
        String jwt = JwtUtils.buildJwt(loginUser);
        //重定向到登陆成功需要跳转的地址
        resp.sendRedirect(casProperties.getLoginSuccessUrl().toString() + "/token/" + jwt);
    }

    @Override
    public void onLoginFailure(HttpServletResponse resp, HttpServletRequest req, Exception e) throws IOException {
        sendRefresh2Response(resp);
    }

    @Override
    public void onNeverLogin(HttpServletResponse resp, HttpServletRequest req) throws IOException {
        allowCors(resp, req);
        writeJson2Response(resp, HttpStatus.UNAUTHORIZED, "请先登录");
    }

    /**
     * 将Map集合转换成{@link LoginUser}
     *
     * @param map 要转换的Map集合
     * @return {@link LoginUser}
     */
    private LoginUser map2userLoginEntity(Map<String, String> map) {
        LoginUser loginUser = new LoginUser();
        Class<LoginUser> loginUserClass = LoginUser.class;
        Arrays.stream(loginUserClass.getDeclaredFields()).forEach(field -> {
            try {
                field.setAccessible(true);
                String name = field.getName();
                String value = map.get(name);
                if (LOGIN_NAME.equals(name)) {
                    value = map.get("login_name");
                }
                if (value != null) {
                    field.set(loginUser, value);
                }
            } catch (Exception e) {
                logger.error("map2userLoginEntity error: ", e);
            }
        });
        return loginUser;
    }

    /**
     * 将消息转换成JSON并写入到{@link HttpServletResponse}中
     *
     * @param resp       {@link HttpServletResponse}
     * @param httpStatus {@link HttpStatus}
     * @param msg        消息
     * @throws IOException IOException
     */
    private void writeJson2Response(HttpServletResponse resp, HttpStatus httpStatus, String msg) throws IOException {
        resp.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        resp.setStatus(httpStatus.value());
        RestModel<Void> restModel = new RestModel<>();
        restModel.setCode(httpStatus.value());
        restModel.setMsg(msg);
        String json = MAPPER.writeValueAsString(restModel);
        PrintWriter writer = resp.getWriter();
        writer.write(json);
        writer.flush();
        writer.close();
    }

    /**
     * 登陆失败时
     *
     * @param resp {@link HttpServletResponse}
     * @throws IOException IOException
     */
    private void sendRefresh2Response(HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("utf-8");
        String location = casProperties.getLoginUrl() + "?service=" + URLEncoder.encode(casProperties.getLocalServerUrl().toString(), "UTF-8");
        PrintWriter writer = resp.getWriter();
        writer.write("<!DOCTYPE html><html><head><meta charset=\"UTF-8\">\n" +
                "<meta http-equiv=\"refresh\" content=\"3;url=" + location + "\">" +
                "<title>身份信息获取失败</title></head><body>" +
                "身份信息获取失败，3秒后重试..." +
                "</body></html>");
        writer.flush();
        writer.close();
    }
}
