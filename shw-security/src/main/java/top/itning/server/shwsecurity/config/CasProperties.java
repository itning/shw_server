package top.itning.server.shwsecurity.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.URI;

/**
 * @author itning
 * @date 2019/4/30 13:28
 */
@ConfigurationProperties(prefix = "cas")
@Component
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
    /**
     * 登陆地址(访问该地址会跳转到loginUrl)
     */
    private String clientLoginPath = "/login";
    /**
     * 登出地址(访问该地址会跳转到登出地址)
     */
    private String clientLogoutPath = "/logout";
    /**
     * 请求读超时(ms)
     */
    private int requestReadTimeout = 5000;
    /**
     * 请求连接超时(ms)
     */
    private int requestConnectTimeout = 15000;

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

    public String getClientLoginPath() {
        return clientLoginPath;
    }

    public void setClientLoginPath(String clientLoginPath) {
        this.clientLoginPath = clientLoginPath;
    }

    public String getClientLogoutPath() {
        return clientLogoutPath;
    }

    public void setClientLogoutPath(String clientLogoutPath) {
        this.clientLogoutPath = clientLogoutPath;
    }

    public int getRequestReadTimeout() {
        return requestReadTimeout;
    }

    public void setRequestReadTimeout(int requestReadTimeout) {
        this.requestReadTimeout = requestReadTimeout;
    }

    public int getRequestConnectTimeout() {
        return requestConnectTimeout;
    }

    public void setRequestConnectTimeout(int requestConnectTimeout) {
        this.requestConnectTimeout = requestConnectTimeout;
    }

    @Override
    public String toString() {
        return "CasProperties{" +
                "serverUrl=" + serverUrl +
                ", loginUrl=" + loginUrl +
                ", logoutUrl=" + logoutUrl +
                ", loginSuccessUrl=" + loginSuccessUrl +
                ", localServerUrl=" + localServerUrl +
                ", clientLoginPath='" + clientLoginPath + '\'' +
                ", clientLogoutPath='" + clientLogoutPath + '\'' +
                ", requestReadTimeout=" + requestReadTimeout +
                ", requestConnectTimeout=" + requestConnectTimeout +
                '}';
    }
}
