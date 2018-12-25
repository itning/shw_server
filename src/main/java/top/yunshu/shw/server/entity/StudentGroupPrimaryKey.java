package top.yunshu.shw.server.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * StudentGroup Complex Key
 *
 * @author shulu
 */
public class StudentGroupPrimaryKey implements Serializable {
    /**
     * StudentGroup ID
     */
    private String studentNumber;
    /**
     * Group ID
     */
    private String groupID;

    public StudentGroupPrimaryKey() {
    }

    public StudentGroupPrimaryKey(String studentNumber, String groupID) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudentGroupPrimaryKey)) {
            return false;
        }
        StudentGroupPrimaryKey that = (StudentGroupPrimaryKey) o;
        return getStudentNumber().equals(that.getStudentNumber()) &&
                getGroupID().equals(that.getGroupID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStudentNumber(), getGroupID());
    }
}
