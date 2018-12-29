package top.yunshu.shw.server.service.student.group;

/**
 * 学生群组服务接口
 *
 * @author shulu
 */
public interface StudentGroupService {
    /**
     * 检查学生是否有群组
     *
     * @param studentNumber 学号
     * @return 有群组返回真
     */
    boolean isHaveGroup(String studentNumber);
}
