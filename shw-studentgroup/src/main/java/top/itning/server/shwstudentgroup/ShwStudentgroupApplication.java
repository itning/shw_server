package top.itning.server.shwstudentgroup;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author itning
 */
@SpringCloudApplication
@EnableFeignClients
public class ShwStudentgroupApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShwStudentgroupApplication.class, args);
    }

}
