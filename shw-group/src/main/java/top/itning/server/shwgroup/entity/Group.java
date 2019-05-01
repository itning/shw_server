package top.itning.server.shwgroup.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.UUID;

/**
 * @author itning
 * @date 2019/4/29 11:43
 */
@Document(collection = "group")
public class Group {
    /**
     * 群组ID
     */
    @Id
    private String id;
    /**
     * 群组名
     */
    @Field("group_name")
    private String groupName;
    /**
     * 教师名
     */
    @Field("teacher_name")
    private String teacherName;
    /**
     * 教师序号
     */
    @Indexed
    @Field("teacher_number")
    private String teacherNumber;
    /**
     * 邀请码
     */
    @Indexed
    @Field("code")
    private String code;
    /**
     * 创建时间
     */
    @Field("gmt_create")
    private Date gmtCreate;
    /**
     * 更新时间
     */
    @Field("gmt_modified")
    private Date gmtModified;

    public Group() {
    }

    public Group(String groupName, String teacherName, String teacherNumber) {
        String id = UUID.randomUUID().toString().replace("-", "");
        this.id = id;
        this.groupName = groupName;
        this.teacherName = teacherName;
        this.teacherNumber = teacherNumber;
        this.code = id;
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
