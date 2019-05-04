package top.itning.server.shwstudentgroup.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author itning
 * @date 2019/5/4 12:04
 */
public interface StudentGroupMessage {
    String DROP_STUDENT_GROUP = "drop_student_group";

    /**
     * 学生退出群组消息
     *
     * @return {@link MessageChannel}
     */
    @Output(DROP_STUDENT_GROUP)
    MessageChannel dropOutStudentGroupOutput();
}
