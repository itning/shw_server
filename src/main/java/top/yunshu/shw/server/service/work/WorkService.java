package top.yunshu.shw.server.service.work;

import top.yunshu.shw.server.entity.Work;

import java.util.List;

/**
 * 作业服务
 *
 * @author itning
 */
public interface WorkService {
    /**
     * 获取学生未交作业
     *
     * @param studentId 学生学号
     * @return 未交作业集合
     */
    List<Work> getStudentUnDoneWork(String studentId);

    /**
     * 获取学生已交作业
     *
     * @param studentId 学生学号
     * @return 已交作业集合
     */
    List<Work> getStudentDoneWork(String studentId);
}
