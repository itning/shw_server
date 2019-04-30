package top.itning.server.shwstudentgroup.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.itning.server.shwstudentgroup.client.entrty.Group;

import java.util.Optional;

/**
 * @author itning
 * @date 2019/4/30 18:20
 */
@FeignClient(name = "group")
@Component
public interface GroupClient {
    /**
     * 获取某个群组信息
     *
     * @param id 群组ID
     * @return 群组
     */
    @GetMapping("/internal/findOneGroupById/{id}")
    Optional<Group> findOneGroupById(@PathVariable String id);
}
