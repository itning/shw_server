package top.itning.server.shwupload.stream;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.stereotype.Component;

/**
 * 消息接收
 *
 * @author itning
 */
@Component
@EnableBinding(UploadMessage.class)
public class UploadStreamReceiver {

}
