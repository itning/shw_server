package top.itning.server.shwstudentgroup.service;

import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;
import top.itning.server.shwstudentgroup.dto.StudentGroupDTO;
import top.itning.server.shwstudentgroup.entity.StudentGroup;

/**
 * @author itning
 * @date 2019/4/30 18:14
 */
public interface StudentGroupService {
    /**
     * 学生加群
     *
     * @param code          邀请码
     * @param studentNumber 学号
     * @return 加入的群组
     */
    Mono<StudentGroup> joinGroup(String code, String studentNumber);

    /**
     * 学生退出群组
     *
     * @param groupId   要退出的群组ID
     * @param studentId 学生ID
     * @return 操作完成后的信号
     */
    Mono<Void> dropOutGroup(String groupId, String studentId);

    /**
     * 获取学生所在的所有群组
     *
     * @param studentNumber 学生学号
     * @param page          页码
     * @param size          每页数量
     * @return 群组分页信息
     */
    Mono<Page<StudentGroupDTO>> findStudentAllGroups(String studentNumber, int page, int size);
}
