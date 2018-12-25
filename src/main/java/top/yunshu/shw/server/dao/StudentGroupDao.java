package top.yunshu.shw.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.yunshu.shw.server.entity.StudentGroup;
import top.yunshu.shw.server.entity.StudentGroupPrimaryKey;

import java.util.List;

/**
 * 学生群组
 *
 * @author shulu
 */
public interface StudentGroupDao extends JpaRepository<StudentGroup, StudentGroupPrimaryKey> {
    /**
     * 根据studentNumber查询学生所在所有群组
     *
     * @param studentNumber 学生ID
     * @return 学生集合
     */
    List<StudentGroup> findAllByStudentNumber(String studentNumber);

    /**
     * 根据GroupID查询群组内学生
     *
     * @param groupId 群组ID
     * @return 学生集合
     */
    List<StudentGroup> findAllByGroupID(String groupId);
}
