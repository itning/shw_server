package top.itning.server.shwstudentgroup.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author itning
 * @date 2019/4/30 17:47
 */
@Document(collection = "student_group")
public class StudentGroup {
    /**
     * studentNumber|groupID
     */
    @Id
    private String id;
    /**
     * StudentGroup ID
     */
    @Indexed
    @Field("student_number")
    private String studentNumber;
    /**
     * Group ID
     */
    @Indexed
    @Field("group_id")
    private String groupID;
    /**
     * 创建时间
     */
    @Field("gmt_create")
    private Date gmtCreate;
    /**
     * 更新时间
     */
    @Field("gmt_modified")
    private Date gmtModified;

    public StudentGroup() {
    }

    public StudentGroup(String studentNumber, String groupID) {
        this.id = studentNumber + "|" + groupID;
        this.studentNumber = studentNumber;
        this.groupID = groupID;
        Date date = new Date();
        this.gmtCreate = date;
        this.gmtModified = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
                "id='" + id + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                ", groupID='" + groupID + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                '}';
    }
}
