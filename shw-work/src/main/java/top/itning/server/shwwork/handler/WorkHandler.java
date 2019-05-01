package top.itning.server.shwwork.handler;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import top.itning.server.shwwork.service.WorkService;

import static top.itning.server.common.model.RestModel.ok;
import static top.itning.server.common.util.Preconditions.getNo;
import static top.itning.server.common.util.Preconditions.mustStudentLogin;

/**
 * @author itning
 * @date 2019/5/1 9:42
 */
@Component
public class WorkHandler {
    private final WorkService workService;

    @Autowired
    public WorkHandler(WorkService workService) {
        this.workService = workService;
    }

    @NonNull
    public Mono<ServerResponse> getAllUnDoneWorks(ServerRequest request) {
        mustStudentLogin(request);
        int page = NumberUtils.toInt(request.queryParam("page").orElse("0"));
        int size = NumberUtils.toInt(request.queryParam("size").orElse("20"));
        return ok(workService.getStudentUnDoneWork(getNo(request), page, size));

    }

    @NonNull
    public Mono<ServerResponse> getAllDoneWorks(ServerRequest request) {
        mustStudentLogin(request);
        int page = NumberUtils.toInt(request.queryParam("page").orElse("0"));
        int size = NumberUtils.toInt(request.queryParam("size").orElse("20"));
        return ok(workService.getStudentDoneWork(getNo(request), page, size));
    }
}
