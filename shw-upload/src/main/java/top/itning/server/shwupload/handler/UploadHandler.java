package top.itning.server.shwupload.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import top.itning.server.shwupload.service.UploadService;

import static top.itning.server.common.model.RestModel.noContent;
import static top.itning.server.common.model.RestModel.ok;
import static top.itning.server.common.util.Preconditions.*;

/**
 * 上传处理器
 *
 * @author itning
 * @date 2019/5/2 16:53
 */
@Component
public class UploadHandler {
    private final UploadService uploadService;

    @Autowired
    public UploadHandler(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @NonNull
    public Mono<ServerResponse> getUpLoadInfoByWorkId(ServerRequest request) {
        mustStudentLogin(request);
        return ok(uploadService.getUploadInfoByWorkId(getNo(request), request.pathVariable("workId")));
    }

    @NonNull
    public Mono<ServerResponse> deleteUploadWork(ServerRequest request) {
        mustStudentLogin(request);
        return uploadService.delUploadInfoByWorkId(getNo(request), request.pathVariable("workId")).thenReturn(noContent()).flatMap(s -> s);
    }

    @NonNull
    public Mono<ServerResponse> getReviewInfoByWorkId(ServerRequest request) {
        String studentId;
        try {
            studentId = request.pathVariable("studentId");
            mustTeacherLogin(request);
        } catch (IllegalArgumentException e) {
            studentId = getNo(request);
            mustStudentLogin(request);
        }
        return ok(uploadService.reviewWork(studentId, request.pathVariable("workId")));
    }
}
