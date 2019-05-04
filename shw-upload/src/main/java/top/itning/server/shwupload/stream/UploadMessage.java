package top.itning.server.shwupload.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author itning
 * @date 2019/5/3 12:20
 */
public interface UploadMessage {
    String DELETE = "file_delete";

    /**
     * 上传删除消息
     *
     * @return {@link MessageChannel}
     */
    @Output(DELETE)
    MessageChannel delOutput();
}
