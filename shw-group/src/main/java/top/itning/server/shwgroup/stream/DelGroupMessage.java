package top.itning.server.shwgroup.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

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
    @Output(DELETE)
    MessageChannel delOutput();
}
