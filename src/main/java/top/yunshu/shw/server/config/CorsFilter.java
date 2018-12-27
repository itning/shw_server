package top.yunshu.shw.server.config;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author itning
 */
public class CorsFilter implements Filter {

    private static final String OPTIONS = "OPTIONS";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        if (OPTIONS.equals(req.getMethod())) {
            String origin = req.getHeader("Origin");
            resp.setHeader("Access-Control-Allow-Credentials", "true");
            resp.setHeader("Access-Control-Allow-Origin", origin);
            resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS,DELETE,PUT,PATCH");
            resp.setHeader("Access-Control-Allow-Headers", req.getHeader("Access-Control-Request-Headers"));
            return;
        }
        chain.doFilter(request, response);
    }
}
