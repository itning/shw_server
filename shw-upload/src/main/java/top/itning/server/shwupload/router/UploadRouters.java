package top.itning.server.shwupload.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import top.itning.server.shwupload.handler.UploadHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.all;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author itning
 * @date 2019/5/2 16:53
 */
@Configuration
public class UploadRouters {
    @Bean
    RouterFunction<ServerResponse> userRouter(UploadHandler uploadHandler) {
        return nest(
                all(),
                route()
                        .GET("/{workId}", uploadHandler::getUpLoadInfoByWorkId)
                        .DELETE("/{workId}", uploadHandler::deleteUploadWork)
                        .GET("/review/{studentId}/{workId}", uploadHandler::getReviewInfoByWorkId)
                        .GET("/review/{workId}", uploadHandler::getReviewInfoByWorkId)
                        .build()
        );
    }
}
