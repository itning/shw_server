package top.yunshu.shw.server.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.yunshu.shw.server.entity.Student;
import top.yunshu.shw.server.entity.Upload;

import java.io.Serializable;

/**
 * 作业详情
 *
 * @author itning
 */
@ApiModel(description = "作业详情")
public class WorkDetailsModel implements Serializable {
    @ApiModelProperty(required = true, value = "作业名")
    private String workName;

    @ApiModelProperty(required = true, value = "群组名")
    private String groupName;

    @ApiModelProperty(required = true, value = "学生信息")
    private Student student;

    @ApiModelProperty(required = true, value = "上传信息")
    private Upload upload;

    @ApiModelProperty(required = true, value = "是否上传")
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
