package top.itning.server.shweureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author itning
 */
@SpringBootApplication
@EnableEurekaServer
public class ShwEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShwEurekaApplication.class, args);
    }

}
