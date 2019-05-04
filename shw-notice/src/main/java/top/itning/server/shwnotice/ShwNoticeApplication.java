package top.itning.server.shwnotice;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author itning
 */
@SpringCloudApplication
@EnableFeignClients
public class ShwNoticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShwNoticeApplication.class, args);
    }

}
