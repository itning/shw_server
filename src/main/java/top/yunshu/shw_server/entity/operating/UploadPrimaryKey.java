package top.yunshu.shw_server.entity.operating;

import java.io.Serializable;

/**
 * @author shulu
 */
public class UploadPrimaryKey implements Serializable {
    /**
     * 学生ID
     */
    private String studentId;
    /**
     * 作业ID
     */
    private String workId;

    public UploadPrimaryKey() {
    }

    public UploadPrimaryKey(String studentId, String workId) {
        this.studentId = studentId;
        this.workId = workId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    @Override
    public String toString() {
        return "UploadPrimaryKey{" +
                "studentId='" + studentId + '\'' +
                ", workId='" + workId + '\'' +
                '}';
    }
}
