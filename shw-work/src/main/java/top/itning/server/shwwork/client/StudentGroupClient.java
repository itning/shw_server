package top.itning.server.shwwork.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author itning
 * @date 2019/5/1 9:51
 */
@FeignClient(name = "student-group")
@Component
public interface StudentGroupClient {
    /**
     * 根据学号查询学生群组中的群组ID信息
     *
     * @param studentNumber 学号
     * @return 群组ID集合
     */
    @GetMapping("/internal/findGroupIdByStudentNumber/{studentNumber}")
    List<String> findGroupIdByStudentNumber(@PathVariable String studentNumber);
}
