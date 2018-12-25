package top.yunshu.shw_server.entity.group;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author shulu
 */
@Entity
@Table(name = "group_", indexes = {
        @Index(name = "teacher_number_index", columnList = "teacher_number"),
        @Index(name = "code_index", columnList = "code")
})
public class Group implements Serializable {
    /**
     * Group Id
     */
    @Id
    private String id;
    /**
     * Group Name
     */
    @Column(nullable = false)
    private String groupName;
    /**
     * Teacher Name
     */
    @Column(nullable = false)
    private String teacherName;
    /**
     * 教师序号
     */
    @Column(name = "teacher_number", nullable = false)
    private String teacherNumber;
    /**
     * 邀请码
     */
    @Column(name = "code", nullable = false)
    private String code;
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

    public Group() {
    }

    public Group(String id, String groupName, String teacherName, String teacherNumber, String code, Date createTime, Date updateTime) {
        this.id = id;
        this.groupName = groupName;
        this.teacherName = teacherName;
        this.teacherNumber = teacherNumber;
        this.code = code;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherNumber() {
        return teacherNumber;
    }

    public void setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
        return "Group{" +
                "id='" + id + '\'' +
                ", groupName='" + groupName + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", teacherNumber='" + teacherNumber + '\'' +
                ", code='" + code + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
