package top.itning.server.shwstudentgroup.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import top.itning.server.shwstudentgroup.handler.StudentGroupHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.all;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author itning
 * @date 2019/4/30 19:38
 */
@Configuration
public class StudentGroupRouters {
    @Bean
    RouterFunction<ServerResponse> userRouter(StudentGroupHandler studentGroupHandler) {
        return nest(
                all(),
                route()
                        .POST("/", studentGroupHandler::addGroup)
                        .build()
        );
    }
}
