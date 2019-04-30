package top.itning.server.shwgroup.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import top.itning.server.shwgroup.handler.GroupHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.all;
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
                all(),
                route()
                        .POST("/", groupHandler::addGroup)
                        .GET("/groups", groupHandler::getTeacherCreateGroups)
                        .GET("/exist", groupHandler::isTeacherHaveAnyGroup)
                        .DELETE("/{id}", groupHandler::deleteGroup)
                        .PATCH("/{id}/{name}", groupHandler::updateGroupName)
                        .build()
        );
    }
}
