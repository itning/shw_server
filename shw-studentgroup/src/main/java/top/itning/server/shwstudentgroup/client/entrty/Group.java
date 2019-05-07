package top.itning.server.shwstudentgroup.client.entrty;

import java.util.Date;

/**
 * 群组
 *
 * @author itning
 * @date 2019/4/29 11:43
 */
public class Group {
    /**
     * 群组ID
     */
    private String id;
    /**
     * 群组名
     */
    private String groupName;
    /**
     * 教师名
     */
    private String teacherName;
    /**
     * 教师序号
     */
    private String teacherNumber;
    /**
     * 邀请码
     */
    private String code;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 更新时间
     */
    private Date gmtModified;

    public Group() {
    }

    public Group(String id, String groupName, String teacherName, String teacherNumber, String code, Date gmtCreate, Date gmtModified) {
        this.id = id;
        this.groupName = groupName;
        this.teacherName = teacherName;
        this.teacherNumber = teacherNumber;
        this.code = code;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTeacherNumber() {
        return teacherNumber;
    }

    public void setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
        return "Group{" +
                "id='" + id + '\'' +
                ", groupName='" + groupName + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", teacherNumber='" + teacherNumber + '\'' +
                ", code='" + code + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                '}';
    }
}
