package top.yunshu.shw.server.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * 上传 双主键
 *
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UploadPrimaryKey)) {
            return false;
        }
        UploadPrimaryKey that = (UploadPrimaryKey) o;
        return getStudentId().equals(that.getStudentId()) &&
                getWorkId().equals(that.getWorkId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStudentId(), getWorkId());
    }
}
