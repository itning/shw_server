package top.yunshu.shw_server.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 通知
 *
 * @author shulu
 */
@Entity
@Table(name = "notice", indexes = {
        @Index(name = "student_number_index", columnList = "student_number")
})
public class Notice implements Serializable {
    /**
     * 标识通知
     */
    @Id
    private String id;
    /**
     * 邀请人
     */
    @Column(nullable = false)
    private String invitePeopleName;
    /**
     * 邀请加入的群组ID
     */
    @Column(nullable = false)
    private String inviteGroupId;
    /**
     * 被邀请人学号
     */
    @Column(name = "student_number", nullable = false)
    private String studentNumber;
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

    public Notice() {
    }

    public Notice(String id, String invitePeopleName, String inviteGroupId, String studentNumber) {
        this.id = id;
        this.invitePeopleName = invitePeopleName;
        this.inviteGroupId = inviteGroupId;
        this.studentNumber = studentNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvitePeopleName() {
        return invitePeopleName;
    }

    public void setInvitePeopleName(String invitePeopleName) {
        this.invitePeopleName = invitePeopleName;
    }

    public String getInviteGroupId() {
        return inviteGroupId;
    }

    public void setInviteGroupId(String inviteGroupId) {
        this.inviteGroupId = inviteGroupId;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
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
        return "Notice{" +
                "id='" + id + '\'' +
                ", invitePeopleName='" + invitePeopleName + '\'' +
                ", inviteGroupId='" + inviteGroupId + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                '}';
    }
}
