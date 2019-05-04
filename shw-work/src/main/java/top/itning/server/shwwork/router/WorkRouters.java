package top.itning.server.shwwork.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import top.itning.server.shwwork.entity.Work;
import top.itning.server.shwwork.handler.WorkHandler;
import top.itning.server.shwwork.service.WorkService;

import static org.springframework.web.reactive.function.server.RequestPredicates.all;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author itning
 * @date 2019/5/1 9:42
 */
@Configuration
public class WorkRouters {
    private final WorkService workService;

    @Autowired
    public WorkRouters(WorkService workService) {
        this.workService = workService;
    }

    @Bean
    RouterFunction<ServerResponse> userRouter(WorkHandler workHandler) {
        return nest(
                all(),
                route()
                        .GET("/un_done", workHandler::getAllUnDoneWorks)
                        .GET("/done", workHandler::getAllDoneWorks)
                        .GET("/works", workHandler::getTeacherWorks)
                        .GET("/{groupId}", workHandler::getTeacherWork)
                        .GET("/detail/{workId}", workHandler::getTeacherWorkDetails)
                        .POST("/", workHandler::addWork)
                        .PATCH("/enabled/{workId}/{enabled}", workHandler::updateWorkEnabled)
                        .PATCH("/name/{workId}/{workName}", workHandler::updateWorkName)
                        .DELETE("/{workId}", workHandler::deleteWork)
                        .build())
                .andNest(
                        path("/internal"),
                        route()
                                .GET("/getAllWorkInfoByGroupId/{id}", serverRequest -> ServerResponse.ok().body(workService.getAllWorkInfoByGroupId(serverRequest.pathVariable("id")), Work.class))
                                .build()
                );
    }
}
