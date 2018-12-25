package top.yunshu.shw_server.service.group;

import top.yunshu.shw_server.entity.Group;
import top.yunshu.shw_server.exception.NoCodeException;

import java.util.List;

/**
 * 群组服务
 *
 * @author shulu
 * @author itning
 */
public interface GroupService {
    /**
     * 获取学生所在的所有群组
     *
     * @param id 学生学号
     * @return 学生加入群组集合
     */
    List<Group> findStudentAllGroups(String id);

    /**
     * 通过邀请码加入群组
     *
     * @param code      邀请码
     * @param studentId 学生ID
     * @return 返回加入成功的群组
     * @throws NoCodeException NO CODE
     */
    Group joinGroup(String code, String studentId) throws NoCodeException;

    /**
     * 学生退出群组
     *
     * @param groupId   要退出的群组ID
     * @param studentId 学生ID
     */
    void dropOutGroup(String groupId, String studentId);

    /**
     * 获取教师所创建所有群组
     *
     * @param id 教师ID
     * @return 群组集合
     */
    List<Group> findTeacherAllGroups(String id);

    /**
     * 创建新群组
     *
     * @param groupName 新建群组名
     * @return 返回创建后群组
     */
    Group createGroup(String groupName, String teacherName, String teacherId);

    /**
     * 删除已有群组
     *
     * @param id 群组ID
     */
    void deleteGroup(String id);

    /**
     * 修改已有群组名
     *
     * @param id   需要修改的群组ID
     * @param name 新群组名
     * @return 修改后的群组
     * @throws top.yunshu.shw_server.exception.NoSuchIdException Id不存在
     */
    Group updateGroup(String id, String name);

}
