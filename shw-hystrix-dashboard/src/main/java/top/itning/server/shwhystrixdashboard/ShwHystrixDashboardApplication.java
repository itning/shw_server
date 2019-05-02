package top.itning.server.shwhystrixdashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * @author itning
 */
@SpringBootApplication
@EnableHystrixDashboard
@EnableTurbine
public class ShwHystrixDashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShwHystrixDashboardApplication.class, args);
    }

}
