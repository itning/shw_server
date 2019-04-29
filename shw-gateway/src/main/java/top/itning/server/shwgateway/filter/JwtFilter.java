package top.itning.server.shwgateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import top.itning.server.common.exception.CasException;
import top.itning.server.common.model.LoginUser;
import top.itning.server.shwgateway.util.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * @author itning
 * @date 2019/4/29 22:48
 */
@Component
public class JwtFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isBlank(authorizationHeader)) {
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            HttpServletResponse response = requestContext.getResponse();
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            try (PrintWriter writer = response.getWriter()) {
                writer.write("{\"code\": 401,\"msg\": \"请先登陆\",\"data\": \"\"}");
                writer.flush();
                requestContext.setResponse(response);
            } catch (IOException e) {
                throw new ZuulException(e.getMessage(), 500, "");
            }
        } else {
            try {
                LoginUser loginUser = JwtUtils.getLoginUser(authorizationHeader);
                Map<String, List<String>> qp = new HashMap<>(6);
                qp.put("id", Collections.singletonList(loginUser.getId()));
                qp.put("no", Collections.singletonList(loginUser.getNo()));
                qp.put("loginName", Collections.singletonList(loginUser.getLoginName()));
                qp.put("userType", Collections.singletonList(loginUser.getUserType()));
                qp.put("name", Collections.singletonList(loginUser.getName()));
                qp.put("loginIp", Collections.singletonList(loginUser.getLoginIp()));
                requestContext.setRequestQueryParams(qp);
            } catch (CasException e) {
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(e.getCode().value());
                HttpServletResponse response = requestContext.getResponse();
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                try (PrintWriter writer = response.getWriter()) {
                    writer.write("{" +
                            "\"code\":" +
                            e.getCode().value() +
                            "," +
                            "\"msg\":\"" +
                            e.getMsg() +
                            "\",\"data\":\"\"}");
                    writer.flush();
                    requestContext.setResponse(response);
                } catch (IOException ex) {
                    throw new ZuulException(e.getMessage(), 500, "");
                }
            }
        }
        return null;
    }
}
