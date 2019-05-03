package top.itning.server.shwupload.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import top.itning.server.shwupload.entity.Upload;
import top.itning.server.shwupload.handler.UploadHandler;
import top.itning.server.shwupload.service.UploadService;

import static org.springframework.web.reactive.function.server.RequestPredicates.all;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author itning
 * @date 2019/5/2 16:53
 */
@Configuration
public class UploadRouters {
    private final UploadService uploadService;

    @Autowired
    public UploadRouters(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @Bean
    RouterFunction<ServerResponse> userRouter(UploadHandler uploadHandler) {
        return nest(
                all(),
                route()
                        .GET("/{workId}", uploadHandler::getUpLoadInfoByWorkId)
                        .DELETE("/{workId}", uploadHandler::deleteUploadWork)
                        .GET("/review/{studentId}/{workId}", uploadHandler::getReviewInfoByWorkId)
                        .GET("/review/{workId}", uploadHandler::getReviewInfoByWorkId)
                        .build())
                .andNest(
                        path("/internal"),
                        route()
                                .GET("/existsById/{id}", serverRequest -> ServerResponse.ok().body(uploadService.existsById(serverRequest.pathVariable("id")), Boolean.class))
                                .GET("/findOneById/{id}", serverRequest -> ServerResponse.ok().body(uploadService.findOneById(serverRequest.pathVariable("id")), Upload.class))
                                .build()
                );
    }
}
