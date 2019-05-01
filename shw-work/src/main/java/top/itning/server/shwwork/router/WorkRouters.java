package top.itning.server.shwwork.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import top.itning.server.shwwork.handler.WorkHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.all;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author itning
 * @date 2019/5/1 9:42
 */
@Configuration
public class WorkRouters {
    @Bean
    RouterFunction<ServerResponse> userRouter(WorkHandler workHandler) {
        return nest(
                all(),
                route()
                        .GET("/un_done", workHandler::getAllUnDoneWorks)
                        .GET("/done", workHandler::getAllDoneWorks)
                        .GET("/works", workHandler::getTeacherWorks)
                        .GET("/work/{groupId}", workHandler::getTeacherWork)
                        .POST("/", workHandler::addWork)
                        .PATCH("/enabled/{workId}/{enabled}", workHandler::updateWorkEnabled)
                        .PATCH("/name/{workId}/{workName}", workHandler::updateWorkName)
                        .DELETE("/{workId}", workHandler::deleteWork)
                        .build()
        );
    }
}
