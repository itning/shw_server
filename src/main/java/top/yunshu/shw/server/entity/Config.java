package top.yunshu.shw.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 配置实体
 *
 * @author itning
 */

@Entity
public class Config implements Serializable {
    public enum ConfigKey {
        /**
         * 控制面板用户名
         */
        USERNAME("username"),
        /**
         * 控制面板密码
         */
        PASSWORD("password"),
        /**
         * 文件仓库地址
         */
        FILE_REPOSITORY_PATH("file_repository_path"),
        /**
         * 临时打包目录
         */
        TEMP_DIR("temp_dir"),
        /**
         * CAS服务器地址
         */
        CAS_SERVER_URL("cas_server_url"),
        /**
         * CAS服务器登陆地址
         */
        CAS_LOGIN_URL("cas_login_url"),
        /**
         * CAS服务器登出地址
         */
        CAS_LOGOUT_URL("cas_logout_url"),
        /**
         * 登陆成功后跳转的地址
         */
        LOGIN_SUCCESS_URL("login_success_url"),
        /**
         * 本地服务地址
         */
        LOCAL_SERVER_URL("local_server_url"),
        /**
         * 字体文件目录
         */
        FONT_DIR("font_dir");
        private String key;

        ConfigKey(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    /**
     * 键
     */
    @Id
    @Column(length = 50)
    private String name;
    /**
     * 值
     */
    @Column(nullable = false)
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Config{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
