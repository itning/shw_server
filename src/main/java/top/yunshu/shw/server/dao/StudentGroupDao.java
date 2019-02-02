package top.yunshu.shw.server.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
     * 根据studentNumber查询学生所在所有群组
     *
     * @param studentNumber 学生ID
     * @param pageable      {@link Pageable}
     * @return 学生集合
     */
    Page<StudentGroup> findAllByStudentNumber(String studentNumber, Pageable pageable);

    /**
     * 根据studentNumber查询学生所在所有群组的数量
     *
     * @param studentNumber 学生ID
     * @return 数量
     */
    long countAllByStudentNumber(String studentNumber);

    /**
     * 根据GroupID查询群组内学生
     *
     * @param groupId 群组ID
     * @return 学生集合
     */
    List<StudentGroup> findAllByGroupID(String groupId);

    /**
     * 根据GroupID查询群组内学生
     *
     * @param groupId  群组ID
     * @param pageable {@link Pageable}
     * @return 学生集合
     */
    Page<StudentGroup> findAllByGroupID(String groupId, Pageable pageable);

    /**
     * 根据GroupID查询群组内学生数量
     *
     * @param groupId 群组ID
     * @return 数量
     */
    long countAllByGroupID(String groupId);

    /**
     * 根据学生学号查询所加入的群ID
     *
     * @param studentNumber 学生学号
     * @return 群ID集合
     */
    @SuppressWarnings("SpringDataRepositoryMethodReturnTypeInspection")
    @Query(value = "select groupID from StudentGroup s where s.studentNumber=?1")
    List<String> findGroupIdByStudentNumber(String studentNumber);

    /**
     * 根据学号和群ID查找学生群组
     *
     * @param studentNumber 学号
     * @param groupId       群ID
     * @return StudentGroup
     */
    StudentGroup findByStudentNumberAndGroupID(String studentNumber, String groupId);

    /**
     * 根据学号查询该学生是否有学生群组
     *
     * @param studentNumber 学号
     * @return 有返回真
     */
    boolean existsAllByStudentNumber(String studentNumber);
}
