package top.itning.server.shwupload.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;
import top.itning.server.shwupload.entity.Upload;
import top.itning.server.shwupload.repository.UploadRepository;
import top.itning.server.shwupload.service.UploadService;

/**
 * 消息接收
 *
 * @author itning
 */
@Component
@EnableBinding(UploadMessage.class)
public class UploadStreamReceiver {
    private final UploadRepository uploadRepository;

    @Autowired
    public UploadStreamReceiver(UploadRepository uploadRepository) {
        this.uploadRepository = uploadRepository;
    }

    /**
     * 上传文件消息接收
     *
     * @param u 消息转换成{@link Upload}
     */
    @StreamListener(UploadMessage.UPLOAD)
    public void receiver(Upload u) {
        //must invoked init method before do anything
        Upload upload = u.init();
        uploadRepository.save(upload).block();
    }
}
