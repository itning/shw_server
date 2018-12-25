package top.yunshu.shw_server.entity.student;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Date;

/**
 * @author shulu
 */
@Entity
@IdClass(StudentPrimaryKey.class)
public class Student implements Serializable {
    /**
     * Student ID
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
    private Date createTime;

    public Student() {
    }

    public Student(String studentNumber, String groupID, Date createTime) {
        this.studentNumber = studentNumber;
        this.groupID = groupID;
        this.createTime = createTime;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentNumber='" + studentNumber + '\'' +
                ", groupID='" + groupID + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
