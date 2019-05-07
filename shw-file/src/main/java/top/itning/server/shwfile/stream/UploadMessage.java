package top.itning.server.shwfile.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 上传消息
 *
 * @author itning
 * @date 2019/5/3 12:20
 */
public interface UploadMessage {
    String DELETE = "file_delete";

    /**
     * 删除文件消息接收
     *
     * @return {@link SubscribableChannel}
     */
    @Input(DELETE)
    SubscribableChannel input();
}
