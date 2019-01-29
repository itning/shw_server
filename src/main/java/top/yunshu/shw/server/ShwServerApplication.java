package top.yunshu.shw.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 学生作业管理系统
 *
 * @author itning
 * @date 2018/12/21
 */
@SpringBootApplication
@EnableSwagger2
@EnableScheduling
public class ShwServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShwServerApplication.class, args);
    }

}

