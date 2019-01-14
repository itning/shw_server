package top.yunshu.shw.server.cas;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import top.yunshu.shw.server.entity.LoginUser;
import top.yunshu.shw.server.entity.RestModel;
import top.yunshu.shw.server.util.JwtUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>实现CAS客户端功能并允许跨域请求</p>
 * <p>前端项目请求<code>/login</code>进行登陆</p>
 * <p>前端项目请求<code>/logout</code>进行登出</p>
 * <p>该过滤器应优先级最高</p>
 *
 * @author itning
 * @see io.jsonwebtoken.Jwts
 * @see javax.servlet.Filter
 */
public class CasFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(CasFilter.class);
    /**
     * 过滤器是否开启
     */
    private static final boolean ENABLED = true;
    public static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String LOGIN_NAME = "loginName";
    private static final String LOGIN_URL = "/login";
    private static final String LOGOUT_URL = "/logout";
    private static final String CAS_HEADER = "Authorization";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!ENABLED) {
            chain.doFilter(request, response);
            return;
        }
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        //OPTIONS请求为跨域 全部释放
        if (HttpMethod.OPTIONS.matches(req.getMethod())) {
            allowCors(resp, req);
            return;
        }
        //登陆登出
        if (HttpMethod.GET.matches(req.getMethod())) {
            //login
            if (LOGIN_URL.equals(req.getServletPath())) {
                CasProperties casProperties = SpringContextHelper.getBean(CasProperties.class);
                //重定向到登陆地址
                resp.sendRedirect(casProperties.getLoginUrl() + "?service=" + casProperties.getLocalServerUrl());
                return;
            }
            //logout
            if (LOGOUT_URL.equals(req.getServletPath())) {
                CasProperties casProperties = SpringContextHelper.getBean(CasProperties.class);
                //重定向到登出地址
                resp.sendRedirect(casProperties.getLogoutUrl().toString());
                return;
            }
        }
        //CAS Start
        String ticket = req.getParameter("ticket");
        if (ticket != null) {
            logger.debug("Get Ticket: " + ticket);
            CasProperties casProperties = SpringContextHelper.getBean(CasProperties.class);
            RestTemplate restTemplate = SpringContextHelper.getBean(RestTemplate.class);
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(casProperties.getServerUrl() + "/serviceValidate?ticket={ticket}&service={local_server_url}", String.class, ticket, casProperties.getLocalServerUrl());
            if (responseEntity.getBody() != null) {
                Map<String, String> map = analysisBody2Map(responseEntity.getBody());
                if (!map.isEmpty()) {
                    //解析成功,用户成功登陆
                    redirectSuccessUrl(resp, casProperties, map);
                    return;
                }
            } else {
                logger.error("AUTHENTICATION failed : Body is Null");
            }
            allowCors(resp, req);
            resp.setHeader("Retry-After", "10");
            writeJson2Response(resp, HttpStatus.SERVICE_UNAVAILABLE, "身份认证失败,请重试");
            return;
        }
        //用户未登录
        if (StringUtils.isBlank(req.getHeader(CAS_HEADER))) {
            allowCors(resp, req);
            writeJson2Response(resp, HttpStatus.UNAUTHORIZED, "请先登录");
            return;
        }
        //Else Request
        chain.doFilter(request, response);
    }

    /**
     * 重定向到登陆地址
     *
     * @param resp          {@link HttpServletResponse}
     * @param casProperties {@link CasProperties}
     * @param map           {@link CasFilter#analysisBody2Map(String)}
     * @throws IOException IOException
     */
    private void redirectSuccessUrl(HttpServletResponse resp, CasProperties casProperties, Map<String, String> map) throws IOException {
        LoginUser loginUser = map2userLoginEntity(map);
        String jwt = JwtUtils.buildJwt(loginUser);
        Cookie cookie = new Cookie("Authorization", jwt);
        cookie.setMaxAge(60);
        resp.addCookie(cookie);
        //重定向到登陆成功需要跳转的地址
        resp.sendRedirect(casProperties.getLoginSuccessUrl().toString());
    }

    /**
     * 解析响应体
     *
     * @param body 响应体
     * @return 解析完成的映射
     */
    private Map<String, String> analysisBody2Map(String body) {
        Map<String, String> map = new HashMap<>(16);
        try {
            Document doc = DocumentHelper.parseText(body);
            Node successNode = doc.selectSingleNode("//cas:authenticationSuccess");
            if (successNode != null) {
                List<Node> attributesNode = doc.selectNodes("//cas:attributes/*");
                attributesNode.forEach(defaultElement -> map.put(defaultElement.getName(), defaultElement.getText()));
                logger.debug("Get Map: " + map);
            } else {
                //认证失败
                logger.error("AUTHENTICATION failed : cas:authenticationSuccess Not Found");
            }
        } catch (Exception e) {
            logger.error("AUTHENTICATION failed and Catch Exception: ", e);
        }
        return map;
    }

    /**
     * 允许跨域
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
}
