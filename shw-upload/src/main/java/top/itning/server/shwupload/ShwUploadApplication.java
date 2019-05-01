package top.itning.server.shwupload;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author itning
 */
@SpringCloudApplication
@EnableFeignClients
public class ShwUploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShwUploadApplication.class, args);
    }

}
