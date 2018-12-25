package top.yunshu.shw_server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;
import top.yunshu.shw_server.entity.group.Group;

import java.util.List;

/**
 * @author shulu
 */
@RestController
public interface GroupDao extends JpaRepository<Group, String> {
    /**
     * 通过邀请码查询群组
     * @param code 邀请码
     * @return 群组
     */
    Group findByCode(String code);

    /**
     * 通过教师ID查询群组
     * @param id 教师ID
     * @return 群组
     */
    List<Group> findByTeacherNumber(String id);

    /**
     * 查询是否有此验证码
     * @param code 邀请码
     * @return 存在返回true
     */
    boolean existsAllByCode(String code);
}
