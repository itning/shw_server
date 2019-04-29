package top.itning.server.shwgroup.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import top.itning.server.shwgroup.handler.GroupHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author itning
 * @date 2019/4/29 14:26
 */
@Configuration
public class GroupRouters {
    @Bean
    RouterFunction<ServerResponse> userRouter(GroupHandler groupHandler) {
        return nest(
                path("/group"),
                route()
                        .POST("/", groupHandler::addGroup)
                        .build()
        );
    }
}
