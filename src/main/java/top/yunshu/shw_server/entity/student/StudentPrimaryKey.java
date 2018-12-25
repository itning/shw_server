package top.yunshu.shw_server.entity.student;

import java.io.Serializable;

/**
 * Student Complex Key
 *
 * @author shulu
 */
public class StudentPrimaryKey implements Serializable {
    /**
     * Student ID
     */
    private String studentNumber;
    /**
     * Group ID
     */
    private String groupID;

    public StudentPrimaryKey() {
    }

    public StudentPrimaryKey(String studentNumber, String groupID) {
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
}
