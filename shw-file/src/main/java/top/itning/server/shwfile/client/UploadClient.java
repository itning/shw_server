package top.itning.server.shwfile.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import top.itning.server.shwfile.pojo.FileUploadMetaData;

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
}
