package top.yunshu.shw_server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.yunshu.shw_server.entity.student.Student;
import top.yunshu.shw_server.entity.student.StudentPrimaryKey;

import java.util.List;

/**
 * 学生群组控制
 *
 * @author shulu
 */
public interface StudentDao extends JpaRepository<Student, StudentPrimaryKey> {
    /**
     * 根据studentNumber查询学生所在所有群组
     * @param studentNumber 学生ID
     * @return 学生集合
     */
    List<Student> findAllByStudentNumber(String studentNumber);

    /**
     * 根据GroupID查询群组内学生
     *
     * @param groupId 群组ID
     * @return 学生集合
     */
    List<Student> findAllByGroupID(String groupId);
}
