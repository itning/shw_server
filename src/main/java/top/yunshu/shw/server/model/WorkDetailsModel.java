package top.yunshu.shw.server.model;

import top.yunshu.shw.server.entity.Student;
import top.yunshu.shw.server.entity.Upload;

import java.io.Serializable;

/**
 * 作业详情
 *
 * @author itning
 */
public class WorkDetailsModel implements Serializable {
    private String workName;
    private String groupName;
    private Student student;
    private Upload upload;
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
