package top.itning.server.shwupload.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * 删除作业消息
 *
 * @author itning
 * @date 2019/5/4 13:42
 */
public interface DelWorkMessage {
    String DELETE = "work_delete";

    /**
     * 作业删除消息
     *
     * @return {@link MessageChannel}
     */
    @Input(DELETE)
    SubscribableChannel input();
}
