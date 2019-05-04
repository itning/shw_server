package top.itning.server.shwwork.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author itning
 * @date 2019/5/4 14:19
 */
public interface DelGroupMessage {
    String DELETE = "group_delete";

    /**
     * 群组删除消息
     *
     * @return {@link MessageChannel}
     */
    @Input(DELETE)
    SubscribableChannel input();
}
