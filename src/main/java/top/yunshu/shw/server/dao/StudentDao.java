package top.yunshu.shw.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.yunshu.shw.server.entity.Student;

/**
 * 学生DAO
 *
 * @author itning
 */
public interface StudentDao extends JpaRepository<Student, String> {
}
