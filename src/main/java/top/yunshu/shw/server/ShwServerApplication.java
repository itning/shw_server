package top.yunshu.shw.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
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
@EnableCaching
public class ShwServerApplication {
    private static final Logger logger = LoggerFactory.getLogger(ShwServerApplication.class);

    public static void main(String[] args) {
        logger.info("MYSQL::url: {}", System.getenv("MYSQL_URL"));
        logger.info("MYSQL::username: {}", System.getenv("MYSQL_USERNAME"));
        logger.info("MYSQL::password: {}", System.getenv("MYSQL_PASSWORD"));
        logger.info("REDIS::HOST: {}", System.getenv("REDIS_HOST"));
        logger.info("REDIS::PORT: {}", System.getenv("REDIS_PORT"));
        logger.info("ADMIN_SERVER::URL: {}", System.getenv("ADMIN_SERVER_URL"));
        logger.info("ADMIN_SERVER::USERNAME: {}", System.getenv("ADMIN_SERVER_USERNAME"));
        logger.info("ADMIN_SERVER::PASSWORD: {}", System.getenv("ADMIN_SERVER_PASSWORD"));
        SpringApplication.run(ShwServerApplication.class, args);
    }

}

