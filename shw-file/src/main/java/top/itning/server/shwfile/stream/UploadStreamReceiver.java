package top.itning.server.shwfile.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;
import top.itning.server.shwfile.service.FileService;

/**
 * 消息接收
 *
 * @author itning
 */
@Component
@EnableBinding(UploadMessage.class)
public class UploadStreamReceiver {
    private static final Logger logger = LoggerFactory.getLogger(UploadStreamReceiver.class);
    private static final int ID_SPLIT_LENGTH = 2;
    private final FileService fileService;

    @Autowired
    public UploadStreamReceiver(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * 删除文件消息接收
     *
     * @param id ID
     */
    @StreamListener(UploadMessage.DELETE)
    public void receiver(String id) {
        String[] split = id.split("\\|");
        if (ID_SPLIT_LENGTH == split.length) {
            fileService.delFile(split[0], split[1]);
        } else {
            logger.warn("get message error: message body is {}", id);
        }
    }
}
