package top.yunshu.shw.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import top.yunshu.shw.server.entity.Student;

/**
 * 学生DAO
 *
 * @author itning
 */
public interface StudentDao extends JpaRepository<Student, String> {
    /**
     * 根据学号查姓名
     *
     * @param no 学号
     * @return 姓名
     */
    @SuppressWarnings("SpringDataRepositoryMethodReturnTypeInspection")
    @Query("select s.name from Student s where s.no=?1")
    String findNameByNo(String no);
}
