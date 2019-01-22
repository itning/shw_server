package top.yunshu.shw.server.service.work;

import top.yunshu.shw.server.entity.Work;
import top.yunshu.shw.server.model.WorkDetailsModel;

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

    /**
     * 获取教师的所有作业信息
     *
     * @param teacherNumber 教师编号
     * @return 作业信息集合
     */
    List<Work> getTeacherAllWork(String teacherNumber);

    /**
     * 根据群ID获取教师的作业信息
     *
     * @param teacherNumber 教师编号
     * @param groupId       群ID
     * @return 作业信息集合
     */
    List<Work> getTeacherWork(String teacherNumber, String groupId);

    /**
     * 创建作业
     *
     * @param workName 作业名
     * @param groupId  所属群ID
     * @param format   文件名规范
     * @param enabled  是否开启
     * @return 创建好的作业
     */
    Work createWork(String workName, String groupId, String format, boolean enabled);

    /**
     * 更改作业开启状态
     *
     * @param workId  作业ID
     * @param enabled 是否开启
     */
    void changeEnabledWord(String workId, boolean enabled);

    /**
     * 删除作业
     *
     * @param workId        作业ID
     * @param teacherNumber 教师编号
     */
    void delWork(String workId, String teacherNumber);

    /**
     * 根据作业ID查询作业上交情况
     *
     * @param teacherNumber 教师编号
     * @param workId        作业ID
     * @return 作业上交情况集合
     */
    List<WorkDetailsModel> getWorkDetailByWorkId(String teacherNumber, String workId);
}
