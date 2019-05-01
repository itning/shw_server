package top.itning.server.shwwork.service;

import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;
import top.itning.server.shwwork.dto.WorkDTO;

/**
 * @author itning
 * @date 2019/5/1 9:43
 */
public interface WorkService {
    /**
     * 获取学生未交作业
     *
     * @param studentId 学生学号
     * @param page      页码
     * @param size      每页数量
     * @return 未交作业集合
     */
    Mono<Page<WorkDTO>> getStudentUnDoneWork(String studentId, int page, int size);

    /**
     * 获取学生已交作业
     *
     * @param studentId 学生学号
     * @param page      页码
     * @param size      每页数量
     * @return 已交作业集合
     */
    Mono<Page<WorkDTO>> getStudentDoneWork(String studentId, int page, int size);

    /**
     * 获取教师的所有作业信息
     *
     * @param teacherNumber 教师编号
     * @param page          页码
     * @param size          每页数量
     * @return 作业信息集合
     */
    Mono<Page<WorkDTO>> getTeacherAllWork(String teacherNumber, int page, int size);

    /**
     * 根据群ID获取教师的作业信息
     *
     * @param teacherNumber 教师编号
     * @param groupId       群ID
     * @param page          页码
     * @param size          每页数量
     * @return 作业信息集合
     */
    Mono<Page<WorkDTO>> getTeacherWork(String teacherNumber, String groupId, int page, int size);
}
