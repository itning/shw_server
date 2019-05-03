package top.itning.server.shwwork.dto;

import top.itning.server.shwwork.client.entity.Student;
import top.itning.server.shwwork.client.entity.Upload;

import java.io.Serializable;

/**
 * 作业详情
 *
 * @author itning
 */
public class WorkDetailsDTO implements Serializable {
    /**
     * 作业名
     */
    private String workName;
    /**
     * 群组名
     */
    private String groupName;
    /**
     * 学生信息
     */
    private Student student;
    /**
     * 上传信息
     */
    private Upload upload;
    /**
     * 是否上传
     */
    private boolean up;

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Upload getUpload() {
        return upload;
    }

    public void setUpload(Upload upload) {
        this.upload = upload;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    @Override
    public String toString() {
        return "WorkDetailsModel{" +
                "workName='" + workName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", student=" + student +
                ", upload=" + upload +
                ", up=" + up +
                '}';
    }
}
