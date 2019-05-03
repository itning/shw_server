package top.itning.server.shwfile.stream;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * 消息接收
 *
 * @author itning
 */
@Component
@EnableBinding(UploadMessage.class)
public class UploadStreamReceiver {

    /**
     * 删除文件消息接收
     *
     * @param id ID
     */
    @StreamListener(UploadMessage.DELETE)
    public void receiver(String id) {
        System.out.println(id);
        //TODO receiver
    }
}
