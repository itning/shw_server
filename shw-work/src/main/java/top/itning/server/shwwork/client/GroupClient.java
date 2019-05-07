package top.itning.server.shwwork.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.itning.server.shwwork.client.entity.Group;

import java.util.List;
import java.util.Optional;

/**
 * 群组远程调用
 *
 * @author itning
 * @date 2019/5/1 14:23
 */
@FeignClient(name = "group")
@Component
public interface GroupClient {
    /**
     * 通过教师ID查询群组
     *
     * @param id 教师ID
     * @return 群组
     */
    @GetMapping("/internal/findByTeacherNumber/{id}")
    List<Group> findByTeacherNumber(@PathVariable String id);

    /**
     * 获取某个群组信息
     *
     * @param id 群组ID
     * @return 群组
     */
    @GetMapping("/internal/findOneGroupById/{id}")
    Optional<Group> findOneGroupById(@PathVariable String id);


    /**
     * 根据群ID查询群名称
     *
     * @param id 群ID
     * @return 群名称
     */
    @GetMapping("/internal/findGroupNameByGroupId/{id}")
    String findGroupNameByGroupId(@PathVariable String id);

    /**
     * 根据群ID查询群
     *
     * @param id 群ID
     * @return 教师名
     */
    @GetMapping("/internal/findTeacherNameById/{id}")
    String findTeacherNameById(@PathVariable String id);
}
