package top.itning.server.shwwork.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.itning.server.shwwork.client.entity.Group;

import java.util.List;
import java.util.Optional;

/**
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
}
