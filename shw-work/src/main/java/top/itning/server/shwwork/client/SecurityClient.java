package top.itning.server.shwwork.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.itning.server.shwwork.client.entity.Student;

import java.util.Optional;

/**
 * @author itning
 * @date 2019/5/3 15:57
 */
@FeignClient(name = "security")
@Component
public interface SecurityClient {
    /**
     * 根据学号查询学生信息
     *
     * @param id 学号
     * @return 学生信息
     */
    @GetMapping("/internal/findStudentById/{id}")
    Optional<Student> findStudentById(@PathVariable String id);
}
