package top.yunshu.shw.server.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Date;

/**
 * 上传
 *
 * @author shulu
 * @author itning
 */
@ApiModel(description = "上传")
@Entity
@IdClass(UploadPrimaryKey.class)
public class Upload implements Serializable {
    /**
     * 学生ID
     */
    @ApiModelProperty(required = true, value = "学生ID")
    @Id
    @Column(length = 50)
    private String studentId;
    /**
     * 作业ID
     */
    @ApiModelProperty(required = true, value = "作业ID")
    @Id
    @Column(length = 50)
    private String workId;
    /**
     * 文件类型
     */
    @ApiModelProperty(required = true, value = "文件类型")
    @Column(nullable = false)
    private String mime;
    /**
     * 扩展名
     */
    @ApiModelProperty(required = true, value = "扩展名")
    @Column(nullable = false)
    private String extensionName;
    /**
     * 文件大小(bytes)
     */
    @ApiModelProperty(required = true, value = "文件大小(bytes)")
    @Column(nullable = false)
    private long size;
    /**
     * 创建时间
     */
    @ApiModelProperty(required = true, value = "创建时间")
    @Column(nullable = false)
    @CreationTimestamp
    private Date gmtCreate;
    /**
     * 更新时间
     */
    @ApiModelProperty(required = true, value = "更新时间")
    @Column(nullable = false)
    @UpdateTimestamp
    private Date gmtModified;

    public Upload() {
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

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getExtensionName() {
        return extensionName;
    }

    public void setExtensionName(String extensionName) {
        this.extensionName = extensionName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
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
        return "Upload{" +
                "studentId='" + studentId + '\'' +
                ", workId='" + workId + '\'' +
                ", mime='" + mime + '\'' +
                ", extensionName='" + extensionName + '\'' +
                ", size=" + size +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                '}';
    }
}
