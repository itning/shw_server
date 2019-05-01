package top.itning.server.shwwork.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.UUID;

/**
 * @author itning
 * @date 2019/5/1 9:36
 */
@Document(collection = "work")
public class Work implements Cloneable {
    /**
     * 作业ID，标识唯一作业
     */
    @Id
    private String id;
    /**
     * 群组ID，该作业所属群
     */
    @Indexed
    @Field("group_id")
    private String groupId;
    /**
     * 作业名
     */
    @Field("work_name")
    private String workName;
    /**
     * 作业启用状态
     */
    @Indexed
    @Field("is_enabled")
    private boolean enabled = true;
    /**
     * 文件名规范
     */
    @Field("file_name_format")
    private String fileNameFormat;
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

    public Work() {
    }

    public Work(String groupId, String workName, boolean enabled, String fileNameFormat) {
        this.id = UUID.randomUUID().toString().replace("-", "");
        this.groupId = groupId;
        this.workName = workName;
        this.enabled = enabled;
        this.fileNameFormat = fileNameFormat;
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
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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

    @Override
    public String toString() {
        return "Work{" +
                "id='" + id + '\'' +
                ", groupId='" + groupId + '\'' +
                ", workName='" + workName + '\'' +
                ", enabled=" + enabled +
                ", fileNameFormat='" + fileNameFormat + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                '}';
    }

    public Work clones() {
        try {
            return (Work) this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return new Work();
        }
    }
}
