package top.yunshu.shw.server.cas;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.URI;

/**
 * CAS 属性
 *
 * @author itning
 */
@Component
@ConfigurationProperties(prefix = "greathiit")
public class GlobalConstants {
    /**
     * 应用ID
     */
    private String appId;
    /**
     * 私有密钥
     */
    private String privateKey;
    /**
     * 公共密钥
     */
    private String publicKey;
    /**
     * CAS接口网址
     */
    private String url;
    /**
     * 登陆成功后跳转的网址
     */
    private URI loginSuccessUrl;
    /**
     * 登出网址
     */
    private URI logoutUrl;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public URI getLoginSuccessUrl() {
        return loginSuccessUrl;
    }

    public void setLoginSuccessUrl(URI loginSuccessUrl) {
        this.loginSuccessUrl = loginSuccessUrl;
    }

    public URI getLogoutUrl() {
        return logoutUrl;
    }

    public void setLogoutUrl(URI logoutUrl) {
        this.logoutUrl = logoutUrl;
    }
}
