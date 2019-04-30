package top.itning.server.shwsecurity.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import top.itning.server.shwsecurity.handler.LoginHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.all;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author itning
 * @date 2019/4/30 12:02
 */
@Configuration
public class LoginRouters {
    @Bean
    RouterFunction<ServerResponse> userRouter(LoginHandler loginHandler) {
        return nest(
                all(),
                route()
                        .GET("/", loginHandler::ticket)
                        .GET("/login", loginHandler::login)
                        .GET("/logout", loginHandler::logout)
                        .build()
        );
    }
}
