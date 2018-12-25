package top.yunshu.shw_server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.yunshu.shw_server.entity.student.Student;
import top.yunshu.shw_server.entity.student.StudentPrimaryKey;

import java.util.List;

/**
 * @author shulu
 */
public interface StudentDao extends JpaRepository<Student, StudentPrimaryKey> {
    List<Student> findAllByStudentNumber(String studentNumber);

    List<Student> findAllByGroupID(String groupId);
}
