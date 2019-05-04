package top.itning.server.shwnotice.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import top.itning.server.shwnotice.service.NoticeService;

import static top.itning.server.common.model.RestModel.ok;
import static top.itning.server.common.util.Preconditions.getNo;
import static top.itning.server.common.util.Preconditions.mustStudentLogin;

/**
 * @author itning
 * @date 2019/5/4 17:08
 */
@Component
public class NoticeHandler {
    private final NoticeService noticeService;

    @Autowired
    public NoticeHandler(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @NonNull
    public Mono<ServerResponse> getAllNotices(ServerRequest request) {
        mustStudentLogin(request);
        return ok(noticeService.getAllNoticeByStudentId(getNo(request)).collectList());
    }
}
