package top.itning.server.shwfile.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author itning
 * @date 2019/5/3 12:20
 */
public interface UploadMessage {
    String UPLOAD = "file_upload";
    String DELETE = "file_delete";

    /**
     * 删除文件消息接收
     *
     * @return {@link SubscribableChannel}
     */
    @Input(DELETE)
    SubscribableChannel input();

    /**
     * 上传文件消息
     *
     * @return {@link MessageChannel}
     */
    @Output(UPLOAD)
    MessageChannel uploadOutput();
}
