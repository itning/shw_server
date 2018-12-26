package top.yunshu.shw.server.cas;

import com.google.gson.Gson;
import com.greathiit.common.util.HttpClientUtil;
import com.greathiit.common.util.SecureRequest;
import com.greathiit.common.util.SecureUtil;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.yunshu.shw.server.entity.LoginUser;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * CAS Filter
 *
 * @author itning
 */
@Component
public class AutoSetUserAdapterFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(AutoSetUserAdapterFilter.class);
    private static final Gson GSON = new Gson();
    private static final String LOGIN_USER = "loginUser";

    @Override
    public final void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final HttpSession session = request.getSession();
        if (session.getAttribute(LOGIN_USER) != null) {
            filterChain.doFilter(request, response);
            return;
        }
        Assertion assertion = (Assertion) session.getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
        String loginName = assertion.getPrincipal().getName();
        logger.debug("login name: " + loginName);
        GlobalConstants globalConstants = SpringContextHelper.getBean(GlobalConstants.class);
        String appId = globalConstants.getAppId();
        String ourPrivateKey = globalConstants.getPrivateKey();
        String otherPublicKey = globalConstants.getPublicKey();
        String greathiitUrl = globalConstants.getUrl();
        Map<String, String> requestMap;
        try {
            requestMap = SecureUtil.encryptTradeInfo(appId, loginName, ourPrivateKey, otherPublicKey);
            String requestVal = new Gson().toJson(requestMap);
            String responseVal = HttpClientUtil.postJson(greathiitUrl.concat("/api/getUser"), requestVal);
            logger.debug("responseVal: " + responseVal);
            SecureRequest secureRequest = GSON.fromJson(responseVal, SecureRequest.class);
            String retVal = SecureUtil.decryptTradeInfo(appId, secureRequest.getCER(), secureRequest.getDATA(), secureRequest.getSIGN(), ourPrivateKey, otherPublicKey);
            logger.debug("return str: " + retVal);
            LoginUser loginUser = GSON.fromJson(retVal, LoginUser.class);
            session.setAttribute(LOGIN_USER, loginUser);
            logger.debug("success set session attribute: " + loginUser);
        } catch (Exception e) {
            //TODO 登陆后获取用户信息失败，反馈给用户
            logger.error("doFilter method invoke error: ", e);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}
