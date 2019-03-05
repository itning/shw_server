package top.yunshu.shw.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import top.yunshu.shw.server.entity.LoginUser;
import top.yunshu.shw.server.util.JwtUtils;

import javax.servlet.http.HttpServletRequest;

import static top.yunshu.shw.server.util.RoleUtils.checkRoleIsStudent;

/**
 * 登陆用户参数
 *
 * @author itning
 */
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    private static final Logger logger = LoggerFactory.getLogger(LoginUserArgumentResolver.class);
    private static final String STUDENT = "/student";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String authorization = webRequest.getHeader("Authorization");
        LoginUser loginUser = JwtUtils.getLoginUser(authorization);
        logger.info("get login user: " + loginUser);
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith(STUDENT)) {
            checkRoleIsStudent(loginUser);
        }
        return loginUser;
    }
}
