package top.yunshu.shw.server.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 作业模型
 *
 * @author itning
 */
public class WorkModel implements Serializable {
    /**
     * 作业ID，标识唯一作业
     */
    private String id;
    /**
     * 群组ID，该作业所属群
     */
    private String groupId;
    /**
     * 作业名
     */
    private String workName;
    /**
     * 作业启用状态
     */
    private boolean isEnabled = true;
    /**
     * 文件名规范
     */
    private String fileNameFormat;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 更新时间
     */
    private Date gmtModified;
    /**
     * 群组名
     */
    private String groupName;
    /**
     * 教师名
     */
    private String teacherName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getFileNameFormat() {
        return fileNameFormat;
    }

    public void setFileNameFormat(String fileNameFormat) {
        this.fileNameFormat = fileNameFormat;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    @Override
    public String toString() {
        return "WorkModel{" +
                "id='" + id + '\'' +
                ", groupId='" + groupId + '\'' +
                ", workName='" + workName + '\'' +
                ", isEnabled=" + isEnabled +
                ", fileNameFormat='" + fileNameFormat + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", groupName='" + groupName + '\'' +
                ", teacherName='" + teacherName + '\'' +
                '}';
    }
}
