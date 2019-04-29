package top.itning.server.shwgroup.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import top.itning.server.common.exception.NullFiledException;
import top.itning.server.shwgroup.entity.Group;
import top.itning.server.shwgroup.service.GroupService;

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
        return request.formData()
                .flatMap(m -> Mono.justOrEmpty(m.getFirst("groupName")))
                .switchIfEmpty(Mono.error(new NullFiledException("群组名不能为空", HttpStatus.BAD_REQUEST)))
                .flatMap(s -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .body(groupService.createGroup(s, "", ""), Group.class));
    }
}
