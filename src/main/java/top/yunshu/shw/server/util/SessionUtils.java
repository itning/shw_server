package top.yunshu.shw.server.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

public class SessionUtils {
    private SessionUtils() {
    }

    public static RequestAttributes getRequestAttributes() {
        return RequestContextHolder.getRequestAttributes();
    }

    public static String getStringAttributeValueFromSession(String attributeName) {
        return getAttributeValueFromSession(attributeName, String.class);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getAttributeValueFromSession(String attributeName, Class<T> clazz) {
        return (T) getRequestAttributes().getAttribute(attributeName, RequestAttributes.SCOPE_SESSION);
    }

    public static HttpSession getSession() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
    }

    public static void invalidateSession() {
        getSession().invalidate();
    }
}
