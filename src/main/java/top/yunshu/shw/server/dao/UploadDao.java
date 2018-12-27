package top.yunshu.shw.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.yunshu.shw.server.entity.Upload;
import top.yunshu.shw.server.entity.UploadPrimaryKey;

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
    boolean existsByWorkIdAndWorkId(String studentId, String workId);

    /**
     * 根据学生学号和作业ID获取上传信息
     *
     * @param studentId 学号
     * @param workId    作业ID
     * @return 上传
     */
    Upload findUploadByStudentIdAndWorkId(String studentId, String workId);
}
