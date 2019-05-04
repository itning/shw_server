package top.itning.server.shwupload.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.itning.server.shwupload.client.entity.Work;

import java.util.List;

/**
 * @author itning
 * @date 2019/5/4 12:42
 */
@FeignClient(name = "work")
@Component
public interface WorkClient {
    /**
     * 根据群组ID获取所有作业信息
     *
     * @param id 群组ID
     * @return 作业
     */
    @GetMapping("/internal/getAllWorkInfoByGroupId/{id}")
    List<Work> getAllWorkInfoByGroupId(@PathVariable String id);
}
