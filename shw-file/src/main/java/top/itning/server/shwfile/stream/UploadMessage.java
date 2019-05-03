package top.itning.server.shwfile.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author itning
 * @date 2019/5/3 12:20
 */
public interface UploadMessage {
    String UPLOAD = "upload_output";

    /**
     * 上传文件消息
     *
     * @return {@link MessageChannel}
     */
    @Output(UPLOAD)
    MessageChannel uploadOutput();
}
