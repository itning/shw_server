package top.itning.server.shwgroup.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import top.itning.server.common.exception.NullFiledException;
import top.itning.server.common.exception.PermissionsException;
import top.itning.server.shwgroup.service.GroupService;

import static top.itning.server.common.model.LoginUser.mustTeacherLogin;
import static top.itning.server.common.model.RestModel.created;

/**
 * @author itning
 * @date 2019/4/29 14:08
 */
@Component
public class GroupHandler {
    private final GroupService groupService;

    @Autowired
    public GroupHandler(GroupService groupService) {
        this.groupService = groupService;
    }

    @NonNull
    public Mono<ServerResponse> addGroup(ServerRequest request) {
        mustTeacherLogin(request.queryParam("userType").orElse(null));
        String teacherName = request.queryParam("name").orElseThrow(() -> new PermissionsException("教师名为空"));
        String teacherID = request.queryParam("no").orElseThrow(() -> new PermissionsException("教师ID为空"));
        return request.formData()
                .flatMap(m -> Mono.justOrEmpty(m.getFirst("groupName")))
                .switchIfEmpty(Mono.error(new NullFiledException("群组名不能为空", HttpStatus.BAD_REQUEST)))
                .flatMap(s -> created(groupService.createGroup(s, teacherName, teacherID)));
    }
}
