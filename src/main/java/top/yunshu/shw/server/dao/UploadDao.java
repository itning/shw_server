package top.yunshu.shw.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import top.yunshu.shw.server.entity.Upload;
import top.yunshu.shw.server.entity.UploadPrimaryKey;

import java.util.List;

/**
 * 上传DAO
 *
 * @author itning
 */
public interface UploadDao extends JpaRepository<Upload, UploadPrimaryKey> {
    /**
     * 根据作业ID查询上传是否存在
     *
     * @param studentId 学号
     * @param workId    作业ID
     * @return 存在返回<code>true</code>
     */
    boolean existsByStudentIdAndWorkId(String studentId, String workId);

    /**
     * 根据学生学号和作业ID获取上传信息
     *
     * @param studentId 学号
     * @param workId    作业ID
     * @return 上传
     */
    Upload findUploadByStudentIdAndWorkId(String studentId, String workId);

    /**
     * 根据作业ID查所有大小
     *
     * @param workId 作业ID
     * @return 上传信息大小集合
     */
    @Query("select u.size from Upload u where u.workId=?1")
    List<Long> findSizeByWorkId(String workId);

    /**
     * 根据学生学号和作业ID获取批阅信息
     *
     * @param studentId 学号
     * @param workId    作业ID
     * @return 批阅信息
     */
    @SuppressWarnings("SpringDataRepositoryMethodReturnTypeInspection")
    @Query("select u.review from Upload u where u.studentId=?1 and u.workId=?2")
    String findReviewByStudentIdAndWorkId(String studentId, String workId);
}
