package top.yunshu.shw.server.cas;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import top.itning.cas.CasProperties;
import top.itning.cas.ICasCallback;
import top.yunshu.shw.server.entity.LoginUser;
import top.yunshu.shw.server.entity.RestModel;
import top.yunshu.shw.server.util.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;

/**
 * Jwt实现
 *
 * @author itning
 */
@Component
public class JwtCasCallBackImpl implements ICasCallback {
    private static final Logger logger = LoggerFactory.getLogger(JwtCasCallBackImpl.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String LOGIN_NAME = "loginName";

    private final CasProperties casProperties;

    @Autowired
    public JwtCasCallBackImpl(CasProperties casProperties) {
        this.casProperties = casProperties;
    }

    @Override
    public void onLoginSuccess(HttpServletResponse resp, HttpServletRequest req, Map<String, String> attributesMap) throws IOException {
        LoginUser loginUser = map2userLoginEntity(attributesMap);
        String jwt = JwtUtils.buildJwt(loginUser);
        //重定向到登陆成功需要跳转的地址
        resp.sendRedirect(casProperties.getLoginSuccessUrl().toString() + "/token/" + jwt);
    }

    @Override
    public void onLoginFailure(HttpServletResponse resp, HttpServletRequest req, Exception e) throws IOException {
        allowCors(resp, req);
        resp.setHeader("Retry-After", "10");
        writeJson2Response(resp, HttpStatus.SERVICE_UNAVAILABLE, "身份认证失败,请重试");
    }

    @Override
    public void onNeverLogin(HttpServletResponse resp, HttpServletRequest req) throws IOException {
        allowCors(resp, req);
        writeJson2Response(resp, HttpStatus.UNAUTHORIZED, "请先登录");
    }

    @Override
    public void onOptionsHttpMethodRequest(HttpServletResponse resp, HttpServletRequest req) {
        allowCors(resp, req);
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
     * 允许跨域(不管客户端地址是什么，全部允许)
     *
     * @param resp {@link HttpServletResponse}
     * @param req  {@link HttpServletRequest}
     */
    private void allowCors(HttpServletResponse resp, HttpServletRequest req) {
        String origin = req.getHeader("Origin");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Origin", origin);
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS,DELETE,PUT,PATCH");
        resp.setHeader("Access-Control-Allow-Headers", req.getHeader("Access-Control-Request-Headers"));
    }
}
