package top.itning.server.shwgroup.handler;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import top.itning.server.common.exception.NullFiledException;
import top.itning.server.shwgroup.service.GroupService;

import static top.itning.server.common.model.RestModel.*;
import static top.itning.server.common.util.Preconditions.*;

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
        mustTeacherLogin(request);
        return request.formData()
                .flatMap(m -> Mono.justOrEmpty(m.getFirst("groupName")))
                .switchIfEmpty(Mono.error(new NullFiledException("群组名不能为空", HttpStatus.BAD_REQUEST)))
                .flatMap(s -> created(groupService.createGroup(s, getName(request), getNo(request))));
    }

    @NonNull
    public Mono<ServerResponse> isTeacherHaveAnyGroup(ServerRequest request) {
        mustTeacherLogin(request);
        return ok(groupService.isHaveAnyGroup(getNo(request)));
    }

    @NonNull
    public Mono<ServerResponse> deleteGroup(ServerRequest request) {
        mustTeacherLogin(request);
        return groupService.deleteGroup(getNo(request), request.pathVariable("id")).thenReturn(noContent()).flatMap(s -> s);
    }

    @NonNull
    public Mono<ServerResponse> updateGroupName(ServerRequest request) {
        mustTeacherLogin(request);
        return groupService.updateGroupName(getNo(request), request.pathVariable("id"), request.pathVariable("name")).thenReturn(noContent()).flatMap(s -> s);
    }

    @NonNull
    public Mono<ServerResponse> getTeacherCreateGroups(ServerRequest request) {
        mustTeacherLogin(request);
        int page = NumberUtils.toInt(request.queryParam("page").orElse("0"));
        int size = NumberUtils.toInt(request.queryParam("size").orElse("20"), 1);
        return ok(groupService.findTeacherAllGroups(getNo(request), page, size));
    }
}
