package top.itning.server.shwnotice.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import top.itning.server.shwnotice.handler.NoticeHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.all;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * 通知路由
 *
 * @author itning
 * @date 2019/5/4 17:08
 */
@Configuration
public class NoticeRouters {
    @Bean
    RouterFunction<ServerResponse> userRouter(NoticeHandler noticeHandler) {
        return nest(
                all(),
                route()

                        .GET("/", noticeHandler::getAllNotices)
                        .build()
        );
    }
}
