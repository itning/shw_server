package top.itning.server.shwgroup.service;

import org.springframework.data.domain.Page;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.itning.server.common.exception.NoSuchFiledValueException;
import top.itning.server.shwgroup.entity.Group;

/**
 * @author itning
 * @date 2019/4/29 12:27
 */
public interface GroupService {
    /**
     * 创建新群组
     *
     * @param groupName   新建群组名
     * @param teacherId   教师ID
     * @param teacherName 教师姓名
     * @return 返回创建后群组
     */
    Mono<Group> createGroup(String groupName, String teacherName, String teacherId);

    /**
     * 删除已有群组
     *
     * @param teacherId 教师ID
     * @param groupId   群组ID
     * @return 操作完成后的信号
     * @throws NoSuchFiledValueException 当教师或者群组不存在时
     */
    Mono<Void> deleteGroup(String teacherId, String groupId);

    /**
     * 修改已有群组名
     *
     * @param teacherId 教师ID
     * @param groupId   需要修改的群组ID
     * @param newName   新群组名
     * @return 修改后的群组
     * @throws NoSuchFiledValueException Id不存在
     */
    Mono<Group> updateGroupName(String teacherId, String groupId, String newName);

    /**
     * 检查教师是否有群组
     *
     * @param teacherNumber 教师ID
     * @return 有返回<code>true</code>
     */
    Mono<Boolean> isHaveAnyGroup(String teacherNumber);

    /**
     * 分页获取教师群组
     *
     * @param teacherNumber 教师ID
     * @param page          页码
     * @param size          每页数量
     * @return 教师群组
     */
    Mono<Page<Group>> findTeacherAllGroups(String teacherNumber, int page, int size);

    /**
     * 获取某个群组信息
     *
     * @param groupId 群组ID
     * @return 群组
     */
    Mono<Group> findOneGroupById(String groupId);

    /**
     * 通过教师ID查询群组
     *
     * @param teacherNumber 教师ID
     * @return 群组
     */
    Flux<Group> findByTeacherNumber(String teacherNumber);

    /**
     * 根据群ID查询群名称
     *
     * @param groupId 群ID
     * @return 群名称
     */
    Mono<String> findGroupNameByGroupId(String groupId);

    /**
     * 根据群ID查询群
     *
     * @param groupId 群ID
     * @return 教师名
     */
    Mono<String> findTeacherNameById(String groupId);
}
