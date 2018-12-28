package top.yunshu.shw.server.cas;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.URI;

/**
 * CAS 配置
 *
 * @author itning
 */
@Component
@ConfigurationProperties(prefix = "cas")
public class CasProperties {
    /**
     * CAS服务端地址
     */
    private URI serverUrl;
    /**
     * 登陆地址(CAS服务端地址)
     */
    private URI loginUrl;
    /**
     * 登出网址(CAS服务端地址)
     */
    private URI logoutUrl;

    /**
     * 登陆成功后跳转的网址
     */
    private URI loginSuccessUrl;

    /**
     * 本地服务端地址(该项目地址)
     */
    private URI localServerUrl;

    public URI getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(URI serverUrl) {
        this.serverUrl = serverUrl;
    }

    public URI getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(URI loginUrl) {
        this.loginUrl = loginUrl;
    }

    public URI getLogoutUrl() {
        return logoutUrl;
    }

    public void setLogoutUrl(URI logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    public URI getLoginSuccessUrl() {
        return loginSuccessUrl;
    }

    public void setLoginSuccessUrl(URI loginSuccessUrl) {
        this.loginSuccessUrl = loginSuccessUrl;
    }

    public URI getLocalServerUrl() {
        return localServerUrl;
    }

    public void setLocalServerUrl(URI localServerUrl) {
        this.localServerUrl = localServerUrl;
    }
}
