package top.yunshu.shw.server.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import top.yunshu.shw.server.entity.Group;

import java.util.List;

/**
 * 群组Dao
 *
 * @author shulu
 */
public interface GroupDao extends JpaRepository<Group, String> {
    /**
     * 通过邀请码查询群组
     *
     * @param code 邀请码
     * @return 群组
     */
    Group findByCode(String code);

    /**
     * 通过教师ID查询群组
     *
     * @param teacherNumber 教师ID
     * @return 群组
     */
    List<Group> findByTeacherNumber(String teacherNumber);

    /**
     * 通过教师ID查询群组(分页)
     *
     * @param teacherNumber 教师ID
     * @param pageable      {@link Pageable}
     * @return 群组
     */
    Page<Group> findByTeacherNumber(String teacherNumber, Pageable pageable);

    /**
     * 查询是否有此验证码
     *
     * @param code 邀请码
     * @return 存在返回true
     */
    boolean existsAllByCode(String code);

    /**
     * 根据群ID查询群名称
     *
     * @param groupId 群ID
     * @return 群名称
     */
    @SuppressWarnings("SpringDataRepositoryMethodReturnTypeInspection")
    @Query(value = "select g.groupName from Group g where g.id=?1")
    String findNameById(String groupId);

    /**
     * 根据群ID查询教师名
     *
     * @param groupId 群ID
     * @return 教师名
     */
    @SuppressWarnings("SpringDataRepositoryMethodReturnTypeInspection")
    @Query(value = "select g.teacherName from Group g where g.id=?1")
    String findTeacherNameById(String groupId);
}
