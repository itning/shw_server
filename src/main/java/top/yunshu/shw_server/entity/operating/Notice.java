package top.yunshu.shw_server.entity.operating;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * @author shulu
 */
@Entity
@Table(name = "notice", indexes = {
        @Index(name = "student_number_index", columnList = "student_number")
})
public class Notice {
    @Id
    private String id;
    /**
     * Inviter information
     */
    @Column(nullable = false)
    private String invitePeopleName;
    /**
     * Invitee information
     */
    @Column(nullable = false)
    private String inviteesName;
    /**
     * Invitee information ID
     */
    @Column(name = "student_number", nullable = false)
    private String studentNumber;
    /**
     * Creation time
     */
    @Column(nullable = false)
    @CreationTimestamp
    private Date createTime;

    public Notice() {
    }

    public Notice(String id, String invitePeopleName, String inviteesName, String studentNumber, Date createTime) {
        this.id = id;
        this.invitePeopleName = invitePeopleName;
        this.inviteesName = inviteesName;
        this.studentNumber = studentNumber;
        this.createTime = createTime;
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

    public String getInviteesName() {
        return inviteesName;
    }

    public void setInviteesName(String inviteesName) {
        this.inviteesName = inviteesName;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "id='" + id + '\'' +
                ", invitePeopleName='" + invitePeopleName + '\'' +
                ", inviteesName='" + inviteesName + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
