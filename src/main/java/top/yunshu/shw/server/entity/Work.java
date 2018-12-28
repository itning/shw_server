package top.yunshu.shw.server.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 作业
 *
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
    @Column(name = "is_enabled", nullable = false)
    private boolean enabled = true;
    /**
     * 文件名规范
     */
    @Column(nullable = false)
    private String fileNameFormat;
    /**
     * 创建时间
     */
    @Column(nullable = false)
    @CreationTimestamp
    private Date gmtCreate;
    /**
     * 更新时间
     */
    @Column(nullable = false)
    @UpdateTimestamp
    private Date gmtModified;

    public Work() {
    }

    public Work(String id, String groupId, String workName, boolean enabled, String fileNameFormat) {
        this.id = id;
        this.groupId = groupId;
        this.workName = workName;
        this.enabled = enabled;
        this.fileNameFormat = fileNameFormat;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getFileNameFormat() {
        return fileNameFormat;
    }

    public void setFileNameFormat(String fileNameFormat) {
        this.fileNameFormat = fileNameFormat;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    public String toString() {
        return "Work{" +
                "id='" + id + '\'' +
                ", groupId='" + groupId + '\'' +
                ", workName='" + workName + '\'' +
                ", enabled=" + enabled +
                ", fileNameFormat='" + fileNameFormat + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                '}';
    }
}
