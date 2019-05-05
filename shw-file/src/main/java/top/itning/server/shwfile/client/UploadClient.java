package top.itning.server.shwfile.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import top.itning.server.shwfile.client.entity.Upload;
import top.itning.server.shwfile.pojo.FileUploadMetaData;

import java.util.List;
import java.util.Optional;

/**
 * @author itning
 * @date 2019/5/4 10:38
 */
@FeignClient(name = "upload")
@Component
public interface UploadClient {
    /**
     * 保存上传信息
     *
     * @param upload {@link FileUploadMetaData}
     */
    @PostMapping("/internal")
    void saveOne(@RequestBody FileUploadMetaData upload);

    /**
     * 根据上传ID查询上传信息
     *
     * @param id 上传ID
     * @return 上传信息
     */
    @GetMapping("/internal/findOneById/{id}")
    Optional<Upload> findOneById(@PathVariable String id);

    /**
     * 根据作业ID查询所有上传信息
     *
     * @param id 作业ID
     * @return 上传信息
     */
    @GetMapping("/internal/getAllUploadByWorkId/{id}")
    List<Upload> getAllUploadByWorkId(@PathVariable String id);
}
