package top.itning.server.shwupload.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author itning
 * @date 2019/5/3 12:20
 */
public interface UploadMessage {
    String UPLOAD = "upload_input";

    /**
     * 上传文件消息接收
     *
     * @return {@link SubscribableChannel}
     */
    @Input(UPLOAD)
    SubscribableChannel input();
}
