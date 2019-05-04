package top.itning.server.shwupload.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author itning
 * @date 2019/5/4 12:04
 */
public interface StudentGroupMessage {
    String DROP_STUDENT_GROUP = "drop_student_group";

    /**
     * 上传文件消息接收
     *
     * @return {@link SubscribableChannel}
     */
    @Input(DROP_STUDENT_GROUP)
    SubscribableChannel input();
}
