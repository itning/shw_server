package top.itning.server.shwsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author itning
 */
@SpringCloudApplication
@EnableFeignClients
public class ShwSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShwSecurityApplication.class, args);
    }

}
