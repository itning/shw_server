package top.itning.server.shwfile;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author itning
 */
@SpringCloudApplication
@EnableFeignClients
public class ShwFileApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShwFileApplication.class, args);
    }

}
