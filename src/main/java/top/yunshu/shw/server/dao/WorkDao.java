package top.yunshu.shw.server.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import top.yunshu.shw.server.entity.Work;

import java.util.List;

/**
 * @author shulu
 */
public interface WorkDao extends JpaRepository<Work, String> {
    /**
     * 根据群组ID 查找所有作业
     *
     * @param groupId 群组ID
     * @return 作业集合
     */
    List<Work> findAllByGroupId(String groupId);

    /**
     * 根据群组ID 查找所有作业
     *
     * @param groupId  群组ID
     * @param pageable {@link Pageable}
     * @return 作业集合
     */
    Page<Work> findAllByGroupId(String groupId, Pageable pageable);

    /**
     * 根据群组ID 查找所有作业
     *
     * @param groupId 群组ID
     * @param enabled 是否开启
     * @return 做业集合
     */
    List<Work> findAllByGroupIdAndEnabled(String groupId, boolean enabled);

    /**
     * 根据群组ID 查找已开启所有作业
     *
     * @param groupId 群组ID
     * @return 做业集合
     */
    List<Work> findAllByGroupIdAndEnabledIsTrue(String groupId);

    /**
     * 根据作业ID查文件命名规范
     *
     * @param workId 作业ID
     * @return 文件名规范
     */
    @SuppressWarnings("SpringDataRepositoryMethodReturnTypeInspection")
    @Query("select w.fileNameFormat from Work w where w.id=?1")
    String findFileNameFormatById(String workId);
}
