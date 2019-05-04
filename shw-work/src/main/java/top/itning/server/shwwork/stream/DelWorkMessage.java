package top.itning.server.shwwork.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
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
    @Output(DELETE)
    MessageChannel delOutput();
}
