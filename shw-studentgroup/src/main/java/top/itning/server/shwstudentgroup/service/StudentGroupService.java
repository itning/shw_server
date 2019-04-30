package top.itning.server.shwstudentgroup.service;

import reactor.core.publisher.Mono;
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
}
