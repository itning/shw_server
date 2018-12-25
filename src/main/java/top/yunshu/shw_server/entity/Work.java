package top.yunshu.shw_server.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author shulu
 */
@Entity
@Table(name = "work", indexes = {
        @Index(name = "group_id_index", columnList = "group_id")
})
public class Work implements Serializable {
    /**
     * 作业ID，标识唯一作业
     */
    @Id
    private String id;
    /**
     * 群组ID，该作业所属群
     */
    @Column(name = "group_id", nullable = false)
    private String groupId;
    /**
     * 作业名
     */
    @Column(nullable = false)
    private String workName;
    /**
     * 作业启用状态
     */
    @Column(nullable = false)
    private boolean isEnabled = true;
    /**
     * 创建时间
     */
    @Column(nullable = false)
    @CreationTimestamp
    private Date createTime;
    /**
     * 更新时间
     */
    @Column(nullable = false)
    @UpdateTimestamp
    private Date updateTime;

    public Work() {
    }

    public Work(String id, String groupId, String workName, boolean isEnabled, Date createTime, Date updateTime) {
        this.id = id;
        this.groupId = groupId;
        this.workName = workName;
        this.isEnabled = isEnabled;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Work{" +
                "id='" + id + '\'' +
                ", groupId='" + groupId + '\'' +
                ", workName='" + workName + '\'' +
                ", isEnabled=" + isEnabled +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
