package top.itning.server.shwstudentgroup.handler;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import top.itning.server.common.exception.NullFiledException;
import top.itning.server.shwstudentgroup.service.StudentGroupService;

import static top.itning.server.common.model.RestModel.*;
import static top.itning.server.common.util.Preconditions.getNo;
import static top.itning.server.common.util.Preconditions.mustStudentLogin;

/**
 * @author itning
 * @date 2019/4/30 19:37
 */
@Component
public class StudentGroupHandler {
    private final StudentGroupService studentGroupService;

    @Autowired
    public StudentGroupHandler(StudentGroupService studentGroupService) {
        this.studentGroupService = studentGroupService;
    }

    @NonNull
    public Mono<ServerResponse> addGroup(ServerRequest request) {
        mustStudentLogin(request);
        return request.formData().flatMap(m -> Mono.justOrEmpty(m.getFirst("code")))
                .switchIfEmpty(Mono.error(new NullFiledException("邀请码不能为空", HttpStatus.BAD_REQUEST)))
                .flatMap(s -> created(studentGroupService.joinGroup(s, getNo(request)), "加入成功"));
    }

    @NonNull
    public Mono<ServerResponse> dropOutGroup(ServerRequest request) {
        mustStudentLogin(request);
        String groupId = request.pathVariable("groupId");
        return studentGroupService.dropOutGroup(groupId, getNo(request)).flatMap(s -> noContent());
    }

    @NonNull
    public Mono<ServerResponse> getAllGroups(ServerRequest request) {
        mustStudentLogin(request);
        int page = NumberUtils.toInt(request.queryParam("page").orElse("0"));
        int size = NumberUtils.toInt(request.queryParam("size").orElse("20"));
        return ok(studentGroupService.findStudentAllGroups(getNo(request), page, size));
    }

    @NonNull
    public Mono<ServerResponse> isStudentJoinAnyStudentGroup(ServerRequest request) {
        mustStudentLogin(request);
        return ok(studentGroupService.isHaveGroup(getNo(request)));
    }
}
