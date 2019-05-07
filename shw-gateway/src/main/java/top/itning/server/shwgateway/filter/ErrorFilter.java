package top.itning.server.shwgateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.ERROR_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SEND_ERROR_FILTER_ORDER;

/**
 * 错误信息过滤
 *
 * @author itning
 * @date 2019/4/30 2:53
 */
@Component
public class ErrorFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        return SEND_ERROR_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return ctx.getThrowable() != null;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        Throwable throwable = ctx.getThrowable();
        String msg = throwable.getMessage();
        Throwable cause = throwable.getCause();
        if (cause != null) {
            msg = cause.getMessage();
        }
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(500);
        HttpServletResponse response = ctx.getResponse();
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try (PrintWriter writer = response.getWriter()) {
            writer.write("{\"code\":500,\"msg\":\"" + msg + "\",\"data\":\"\"}");
            writer.flush();
            ctx.setResponse(response);
        } catch (IOException e) {
            throw new ZuulException(e.getMessage(), 500, "");
        }
        return null;
    }
}
