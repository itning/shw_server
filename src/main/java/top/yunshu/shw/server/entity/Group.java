package top.yunshu.shw.server.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 群组实体<br>
 * 教师可以创建群组，删除群组，修改群组<br>
 * 学生可以选择加入群组<br>
 *
 * @author shulu
 * @author itning
 */
@ApiModel(description = "群组")
@Entity
@Table(name = "group_", indexes = {
        @Index(name = "teacher_number_index", columnList = "teacher_number"),
        @Index(name = "code_index", columnList = "code", unique = true)
})
public class Group implements Serializable {
    /**
     * 群组ID
     */
    @ApiModelProperty(required = true, value = "群组ID")
    @Id
    @Column(length = 50)
    private String id;
    /**
     * 群组名
     */
    @ApiModelProperty(required = true, value = "群组名")
    @Column(nullable = false)
    private String groupName;
    /**
     * 教师名
     */
    @ApiModelProperty(required = true, value = "教师名")
    @Column(nullable = false)
    private String teacherName;
    /**
     * 教师序号
     */
    @ApiModelProperty(required = true, value = "教师序号")
    @Column(name = "teacher_number", nullable = false, length = 50)
    private String teacherNumber;
    /**
     * 邀请码
     */
    @ApiModelProperty(required = true, value = "邀请码")
    @Column(name = "code", nullable = false, unique = true, length = 50)
    private String code;
    /**
     * 创建时间
     */
    @ApiModelProperty(required = true, value = "创建时间")
    @Column(nullable = false)
    @CreationTimestamp
    private Date gmtCreate;
    /**
     * 更新时间
     */
    @ApiModelProperty(required = true, value = "更新时间")
    @Column(nullable = false)
    @UpdateTimestamp
    private Date gmtModified;

    public Group() {
    }

    public Group(String id, String groupName, String teacherName, String teacherNumber, String code) {
        this.id = id;
        this.groupName = groupName;
        this.teacherName = teacherName;
        this.teacherNumber = teacherNumber;
        this.code = code;
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
        return "Group{" +
                "id='" + id + '\'' +
                ", groupName='" + groupName + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", teacherNumber='" + teacherNumber + '\'' +
                ", code='" + code + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                '}';
    }
}
