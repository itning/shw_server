package top.itning.server.shwstudentgroup.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;
import top.itning.server.shwstudentgroup.service.StudentGroupService;

/**
 * 消息接收
 *
 * @author itning
 */
@Component
@EnableBinding({DelGroupMessage.class, StudentGroupMessage.class})
public class StreamReceiver {
    private final StudentGroupService studentGroupService;

    @Autowired
    public StreamReceiver(StudentGroupService studentGroupService) {
        this.studentGroupService = studentGroupService;
    }

    /**
     * 教师删除群组消息接收
     *
     * @param groupId 群组ID
     */
    @StreamListener(DelGroupMessage.DELETE)
    public void receiverDelGroupMessage(String groupId) {
        studentGroupService.teacherDelGroupMessage(groupId).block();
    }
}
