package top.itning.server.shwgateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.http.HttpHeaders.*;

/**
 * 跨域请求过滤器
 * 该过滤器过滤{@link HttpMethod#OPTIONS}方法
 * 返回准许跨域的响应
 *
 * @author itning
 * @date 2019/5/2 19:57
 */
@Component
public class CorsFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 2;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        return HttpMethod.OPTIONS.matches(request.getMethod());
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        allowCors(requestContext.getResponse(), requestContext.getRequest());
        requestContext.setSendZuulResponse(false);
        return null;
    }

    /**
     * 允许跨域(不管客户端地址是什么，全部允许)
     *
     * @param resp {@link HttpServletResponse}
     * @param req  {@link HttpServletRequest}
     */
    private void allowCors(HttpServletResponse resp, HttpServletRequest req) {
        String origin = req.getHeader(ORIGIN);
        resp.setHeader(ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        resp.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, origin);
        resp.setHeader(ACCESS_CONTROL_ALLOW_METHODS, "POST,GET,OPTIONS,DELETE,PUT,PATCH");
        resp.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, req.getHeader(ACCESS_CONTROL_REQUEST_HEADERS));
        resp.setIntHeader(ACCESS_CONTROL_MAX_AGE, 2592000);
    }
}
