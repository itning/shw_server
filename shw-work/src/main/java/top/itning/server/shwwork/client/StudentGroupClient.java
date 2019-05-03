package top.itning.server.shwwork.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.itning.server.shwwork.client.entity.StudentGroup;

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

    /**
     * 根据群ID查所有学生群组
     *
     * @param groupId 群ID
     * @param page    页码
     * @param size    每页数量
     * @return 学生集合
     */
    @GetMapping("/internal/findAllByGroupID/{groupId}/{page}/{size}")
    List<StudentGroup> findAllByGroupID(@PathVariable String groupId, @PathVariable int page, @PathVariable int size);

    /**
     * 根据群ID计算数量
     *
     * @param groupId 群ID
     * @return 数量
     */
    @GetMapping("/internal/countAllByGroupID/{groupId}")
    long countAllByGroupID(@PathVariable String groupId);
}
