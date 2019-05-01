package top.itning.server.shwwork;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author itning
 */
@SpringCloudApplication
@EnableFeignClients
public class ShwWorkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShwWorkApplication.class, args);
    }

}
