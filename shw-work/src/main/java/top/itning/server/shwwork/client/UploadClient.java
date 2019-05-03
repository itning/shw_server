package top.itning.server.shwwork.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.itning.server.shwwork.client.entity.Upload;

import java.util.Optional;

/**
 * @author itning
 * @date 2019/5/3 14:27
 */
@FeignClient(name = "upload")
@Component
public interface UploadClient {
    /**
     * 根据作业ID查询上传是否存在
     *
     * @param id 作业ID
     * @return 存在返回<code>true</code>
     */
    @GetMapping("/internal/existsById/{id}")
    Boolean existsById(@PathVariable String id);

    /**
     * 根据ID查询上传信息
     *
     * @param id 上传信息ID
     * @return 上传信息
     */
    @GetMapping("/internal/findOneById/{id}")
    Optional<Upload> findOneById(@PathVariable String id);
}
