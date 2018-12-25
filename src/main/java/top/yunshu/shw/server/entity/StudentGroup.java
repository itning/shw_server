package top.yunshu.shw.server.entity;

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
@Entity
@IdClass(StudentGroupPrimaryKey.class)
public class StudentGroup implements Serializable {
    /**
     * StudentGroup ID
     */
    @Id
    private String studentNumber;
    /**
     * Group ID
     */
    @Id
    private String groupID;
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
