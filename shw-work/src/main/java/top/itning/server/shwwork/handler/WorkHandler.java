package top.itning.server.shwwork.handler;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import top.itning.server.common.exception.NullFiledException;
import top.itning.server.shwwork.service.WorkService;

import static top.itning.server.common.model.RestModel.*;
import static top.itning.server.common.util.Preconditions.*;

/**
 * 作业处理器
 *
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
        int size = NumberUtils.toInt(request.queryParam("size").orElse("20"), 1);
        return ok(workService.getStudentUnDoneWork(getNo(request), page, size));

    }

    @NonNull
    public Mono<ServerResponse> getAllDoneWorks(ServerRequest request) {
        mustStudentLogin(request);
        int page = NumberUtils.toInt(request.queryParam("page").orElse("0"));
        int size = NumberUtils.toInt(request.queryParam("size").orElse("20"), 1);
        return ok(workService.getStudentDoneWork(getNo(request), page, size));
    }

    @NonNull
    public Mono<ServerResponse> getTeacherWorks(ServerRequest request) {
        mustTeacherLogin(request);
        int page = NumberUtils.toInt(request.queryParam("page").orElse("0"));
        int size = NumberUtils.toInt(request.queryParam("size").orElse("20"), 1);
        return ok(workService.getTeacherAllWork(getNo(request), page, size));
    }

    @NonNull
    public Mono<ServerResponse> getTeacherWork(ServerRequest request) {
        mustTeacherLogin(request);
        int page = NumberUtils.toInt(request.queryParam("page").orElse("0"));
        int size = NumberUtils.toInt(request.queryParam("size").orElse("20"), 1);
        return ok(workService.getTeacherWork(getNo(request), request.pathVariable("groupId"), page, size));
    }

    @NonNull
    public Mono<ServerResponse> addWork(ServerRequest request) {
        mustTeacherLogin(request);
        return request.formData()
                .flatMap(g -> {
                    String workName = getOrThrowIfNull("workName", "作业名不能为空", g);
                    String groupId = getOrThrowIfNull("groupId", "群组ID不能为空", g);
                    String fileFormat = g.getFirst("fileFormat");
                    if (fileFormat == null) {
                        fileFormat = "0";
                    }
                    return created(workService.createWork(getNo(request), workName, groupId, fileFormat, true));
                });
    }

    @NonNull
    public Mono<ServerResponse> updateWorkEnabled(ServerRequest request) {
        mustTeacherLogin(request);
        Boolean enabled = BooleanUtils.toBooleanObject(request.pathVariable("enabled"));
        if (enabled == null) {
            throw new NullFiledException("开启状态参数值不正确", HttpStatus.BAD_REQUEST);
        }
        return workService.changeWorkEnabledStatus(getNo(request), request.pathVariable("workId"), enabled)
                .thenReturn(noContent())
                .flatMap(m -> m);
    }

    @NonNull
    public Mono<ServerResponse> updateWorkName(ServerRequest request) {
        mustTeacherLogin(request);
        return workService.changeWorkName(getNo(request), request.pathVariable("workId"), request.pathVariable("workName")).thenReturn(noContent()).flatMap(s -> s);
    }

    @NonNull
    public Mono<ServerResponse> deleteWork(ServerRequest request) {
        mustTeacherLogin(request);
        return workService.delWork(request.pathVariable("workId"), getNo(request)).thenReturn(noContent()).flatMap(s -> s);
    }

    @NonNull
    public Mono<ServerResponse> getTeacherWorkDetails(ServerRequest request) {
        mustTeacherLogin(request);
        int page = NumberUtils.toInt(request.queryParam("page").orElse("0"));
        int size = NumberUtils.toInt(request.queryParam("size").orElse("20"), 1);
        return ok(workService.getWorkDetailByWorkId(getNo(request), request.pathVariable("workId"), page, size));
    }

    private String getOrThrowIfNull(String key, String msg, MultiValueMap<String, String> map) {
        String v = map.getFirst(key);
        if (StringUtils.isBlank(v)) {
            throw new NullFiledException(msg, HttpStatus.BAD_REQUEST);
        }
        return v;
    }
}
