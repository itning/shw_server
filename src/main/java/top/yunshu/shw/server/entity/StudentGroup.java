package top.yunshu.shw.server.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Date;

/**
 * 学生群组
 *
 * @author shulu
 */
@ApiModel(description = "学生群组")
@Entity
@IdClass(StudentGroupPrimaryKey.class)
public class StudentGroup implements Serializable {
    /**
     * StudentGroup ID
     */
    @ApiModelProperty(required = true, value = "StudentGroup ID")
    @Id
    @Column(length = 50)
    private String studentNumber;
    /**
     * Group ID
     */
    @ApiModelProperty(required = true, value = "Group ID")
    @Id
    @Column(length = 50)
    private String groupID;
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

    public StudentGroup() {
    }

    public StudentGroup(String studentNumber, String groupID) {
        this.studentNumber = studentNumber;
        this.groupID = groupID;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
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
        return "StudentGroup{" +
                "studentNumber='" + studentNumber + '\'' +
                ", groupID='" + groupID + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                '}';
    }
}
