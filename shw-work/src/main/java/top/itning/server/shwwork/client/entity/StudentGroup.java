package top.itning.server.shwwork.client.entity;

import java.util.Date;

/**
 * 学生群组
 *
 * @author itning
 * @date 2019/4/30 17:47
 */
public class StudentGroup {
    /**
     * studentNumber|groupID
     */
    private String id;
    /**
     * StudentGroup ID
     */
    private String studentNumber;
    /**
     * Group ID
     */
    private String groupID;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 更新时间
     */
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
