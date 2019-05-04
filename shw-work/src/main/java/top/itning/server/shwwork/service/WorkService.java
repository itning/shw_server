package top.itning.server.shwwork.service;

import org.springframework.data.domain.Page;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.itning.server.shwwork.dto.WorkDTO;
import top.itning.server.shwwork.dto.WorkDetailsDTO;
import top.itning.server.shwwork.entity.Work;

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

    /**
     * 创建作业
     *
     * @param teacherNumber 教师编号
     * @param workName      作业名
     * @param groupId       所属群ID
     * @param format        文件名规范
     * @param enabled       是否开启
     * @return 创建好的作业
     */
    Mono<Work> createWork(String teacherNumber, String workName, String groupId, String format, boolean enabled);

    /**
     * 更改作业开启状态
     *
     * @param teacherNumber 教师ID
     * @param workId        作业ID
     * @param enabled       是否开启
     * @return 操作完成后的信号
     */
    Mono<Work> changeWorkEnabledStatus(String teacherNumber, String workId, boolean enabled);

    /**
     * 更改作业名称
     *
     * @param teacherNumber 教师ID
     * @param workId        作业ID
     * @param workName      新作业名
     * @return 操作完成后的信号
     */
    Mono<Work> changeWorkName(String teacherNumber, String workId, String workName);

    /**
     * 删除作业
     *
     * @param workId        作业ID
     * @param teacherNumber 教师编号
     * @return 操作完成后的信号
     */
    Mono<Void> delWork(String workId, String teacherNumber);

    /**
     * 根据作业ID查询作业上交情况
     *
     * @param teacherNumber 教师编号
     * @param workId        作业ID
     * @param page          页码
     * @param size          每页数量
     * @return 作业上交情况集合
     */
    Mono<Page<WorkDetailsDTO>> getWorkDetailByWorkId(String teacherNumber, String workId, int page, int size);

    /**
     * 根据群组ID获取所有作业信息
     *
     * @param groupId 群组ID
     * @return 作业
     */
    Flux<Work> getAllWorkInfoByGroupId(String groupId);

    /**
     * 教师删除群消息
     *
     * @param groupId 群ID
     * @return 操作完成后的信号
     */
    Mono<Void> teacherDelGroupFromMessage(String groupId);
}
