package top.itning.server.shwsecurity.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import top.itning.server.common.model.LoginUser;
import top.itning.server.shwsecurity.config.CasProperties;
import top.itning.server.shwsecurity.entity.Student;
import top.itning.server.shwsecurity.repository.StudentRepository;
import top.itning.server.shwsecurity.util.JwtUtils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.*;

/**
 * 登陆处理器
 *
 * @author itning
 * @date 2019/4/30 12:02
 */
@Component
public class LoginHandler {
    private static final Logger logger = LoggerFactory.getLogger(LoginHandler.class);
    private static final String LOGIN_NAME = "loginName";

    private final RestTemplate restTemplate;
    private final StudentRepository studentRepository;
    private final CasProperties casProperties;

    @Autowired
    public LoginHandler(RestTemplate restTemplate, StudentRepository studentRepository, CasProperties casProperties) {
        this.restTemplate = restTemplate;
        this.studentRepository = studentRepository;
        this.casProperties = casProperties;
    }

    @NonNull
    public Mono<ServerResponse> login(@SuppressWarnings("unused") ServerRequest request) {
        return ServerResponse.temporaryRedirect(URI.create(getRedirectLocation())).build();
    }

    @NonNull
    public Mono<ServerResponse> logout(@SuppressWarnings("unused") ServerRequest request) {
        return ServerResponse.temporaryRedirect(casProperties.getLogoutUrl()).build();
    }

    @NonNull
    public Mono<ServerResponse> ticket(ServerRequest request) {
        Optional<String> ticketOptional = request.queryParam("ticket");
        if (ticketOptional.isPresent()) {
            String ticket = ticketOptional.get();
            logger.debug("get ticket {}", ticket);
            try {
                return doLoginWithTicket(ticket);
            } catch (Exception e) {
                logger.debug("catch exception {}", e.getMessage());
                return sendRedirect();
            }
        } else {
            return ServerResponse.badRequest().build();
        }
    }

    /**
     * 使用ticket进行登陆
     *
     * @param ticket ticken
     * @return Mono<ServerResponse>
     */
    private Mono<ServerResponse> doLoginWithTicket(String ticket) {
        Optional<String> bodyOptional = sendRequestAndGetResponseBody(ticket);
        if (bodyOptional.isPresent()) {
            String body = bodyOptional.get();
            logger.debug("body is {}", body);
            Map<String, String> map = analysisBody2Map(body);
            if (map.isEmpty()) {
                logger.debug("map is empty");
                return sendRedirect();
            }
            //解析成功,用户成功登陆
            LoginUser loginUser = map2userLoginEntity(map);
            logger.debug("login user {}", loginUser);
            Mono<Student> studentMono = null;
            if (LoginUser.STUDENT_USER.equals(loginUser.getUserType())) {
                Student student = new Student();
                student.setNo(loginUser.getNo());
                student.setLoginName(loginUser.getLoginName());
                student.setName(loginUser.getName());
                student.setClazzId(loginUser.getClazzId());
                studentMono = studentRepository.save(student);
            }
            try {
                final String jwt = JwtUtils.buildJwt(loginUser);
                final String successUrl = casProperties.getLoginSuccessUrl() + "/token/" + jwt;
                logger.debug("success url {}", successUrl);
                if (studentMono != null) {
                    return studentMono.flatMap(s -> ServerResponse.temporaryRedirect(URI.create(successUrl)).build());
                }
                return ServerResponse.temporaryRedirect(URI.create(successUrl)).build();
            } catch (JsonProcessingException e) {
                logger.error("error ", e);
                throw new RuntimeException(e.getMessage());
            }
        } else {
            throw new RuntimeException("AUTHENTICATION failed : Body is Null");
        }
    }

    /**
     * 重定向请求
     *
     * @return Mono<ServerResponse>
     */
    private Mono<ServerResponse> sendRedirect() {
        String location = getRedirectLocation();
        return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_HTML)
                .body(Mono.just("<!DOCTYPE html><html><head><meta charset=\"UTF-8\">\n" +
                        "<meta http-equiv=\"refresh\" content=\"3;url=" + location + "\">" +
                        "<title>身份信息获取失败</title></head><body>" +
                        "身份信息获取失败，3秒后重试..." +
                        "</body></html>"), String.class);
    }

    /**
     * 解析Body到MAP
     *
     * @param body 响应体
     * @return MAP
     */
    private Map<String, String> analysisBody2Map(String body) {
        Map<String, String> map = new HashMap<>(16);
        try {
            Document doc = DocumentHelper.parseText(body);
            Node successNode = doc.selectSingleNode("//cas:authenticationSuccess");
            if (successNode != null) {
                @SuppressWarnings("unchecked")
                List<Node> attributesNode = doc.selectNodes("//cas:attributes/*");
                attributesNode.forEach(defaultElement -> map.put(defaultElement.getName(), defaultElement.getText()));
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
     * 获取重定向路径
     *
     * @return 重定向路径
     */
    private String getRedirectLocation() {
        try {
            return casProperties.getLoginUrl() + "?service=" + URLEncoder.encode(casProperties.getLocalServerUrl().toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 发送请求并获取响应体
     *
     * @param ticket ticket
     * @return 响应体
     */
    private Optional<String> sendRequestAndGetResponseBody(String ticket) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(casProperties.getServerUrl() + "/serviceValidate?ticket={ticket}&service={local_server_url}", String.class, ticket, casProperties.getLocalServerUrl());
        return Optional.ofNullable(responseEntity.getBody());
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
