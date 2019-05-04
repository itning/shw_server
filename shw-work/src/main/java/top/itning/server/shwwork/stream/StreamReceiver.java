package top.itning.server.shwwork.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;
import top.itning.server.shwwork.service.WorkService;

/**
 * 消息接收
 *
 * @author itning
 */
@Component
@EnableBinding({DelGroupMessage.class, DelWorkMessage.class})
public class StreamReceiver {
    private final WorkService workService;

    @Autowired
    public StreamReceiver(WorkService workService) {
        this.workService = workService;
    }

    /**
     * 教师删除群组消息接收
     *
     * @param groupId 群组ID
     */
    @StreamListener(DelGroupMessage.DELETE)
    public void receiverDelGroupMessage(String groupId) {
        workService.teacherDelGroupFromMessage(groupId).block();
    }
}
