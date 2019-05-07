package top.itning.server.shwsecurity.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import top.itning.server.shwsecurity.entity.Student;
import top.itning.server.shwsecurity.handler.LoginHandler;
import top.itning.server.shwsecurity.repository.StudentRepository;

import static org.springframework.web.reactive.function.server.RequestPredicates.all;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * 登陆路由
 *
 * @author itning
 * @date 2019/4/30 12:02
 */
@Configuration
public class LoginRouters {
    private final StudentRepository studentRepository;

    public LoginRouters(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Bean
    RouterFunction<ServerResponse> userRouter(LoginHandler loginHandler) {
        return nest(
                all(),
                route()
                        .GET("/", loginHandler::ticket)
                        .GET("/login", loginHandler::login)
                        .GET("/logout", loginHandler::logout)
                        .build())
                .andNest(
                        path("/internal"),
                        route()
                                .GET("/findStudentById/{id}", serverRequest -> ServerResponse.ok().body(studentRepository.findById(serverRequest.pathVariable("id")), Student.class))
                                .build()
                );
    }
}
