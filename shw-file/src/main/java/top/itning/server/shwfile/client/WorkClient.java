package top.itning.server.shwfile.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.itning.server.shwfile.client.entity.Work;

import java.util.Optional;

/**
 * 作业服务远程调用接口
 *
 * @author itning
 * @date 2019/5/5 19:47
 */
@FeignClient(name = "work")
@Component
public interface WorkClient {
    /**
     * 获取一个作业信息
     *
     * @param id 作业ID
     * @return 作业信息
     */
    @GetMapping("/internal/getOneWorkById/{id}")
    Optional<Work> getOneWorkById(@PathVariable String id);
}
