package top.itning.server.shwgroup;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author itning
 */
@SpringCloudApplication
@EnableFeignClients
public class ShwGroupApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShwGroupApplication.class, args);
    }

}
