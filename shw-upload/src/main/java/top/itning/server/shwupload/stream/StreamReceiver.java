package top.itning.server.shwupload.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;
import top.itning.server.shwupload.service.UploadService;

/**
 * 消息接收
 *
 * @author itning
 */
@Component
@EnableBinding({UploadMessage.class, StudentGroupMessage.class, DelWorkMessage.class})
public class StreamReceiver {
    private static final Logger logger = LoggerFactory.getLogger(StreamReceiver.class);
    private static final int ID_SPLIT_LENGTH = 2;

    private final UploadService uploadService;

    @Autowired
    public StreamReceiver(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    /**
     * 学生退出群组消息接收
     *
     * @param id studentId + "|" + groupId
     */
    @StreamListener(StudentGroupMessage.DROP_STUDENT_GROUP)
    public void receiverDropStudentGroupMessage(String id) {
        String[] split = id.split("\\|");
        if (ID_SPLIT_LENGTH == split.length) {
            uploadService.studentDropGroupFromMessage(split[0], split[1]).block();
        } else {
            logger.warn("get message error: message body is {}", id);
        }
    }

    /**
     * 教师删除作业消息接收
     *
     * @param workId 作业ID
     */
    @StreamListener(DelWorkMessage.DELETE)
    public void receiverDelWorkMessage(String workId) {
        uploadService.teacherDelWorkFromMessage(workId).block();
    }
}
