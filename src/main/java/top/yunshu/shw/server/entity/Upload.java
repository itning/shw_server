package top.yunshu.shw.server.entity;

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
 * @author shulu
 */
@Entity
@IdClass(UploadPrimaryKey.class)
public class Upload implements Serializable {
    /**
     * 学生ID
     */
    @Id
    @Column(length = 50)
    private String studentId;
    /**
     * 作业ID
     */
    @Id
    @Column(length = 50)
    private String workId;
    /**
     * 文件类型
     */
    @Column(nullable = false)
    private String mime;
    /**
     * 文件大小
     */
    @Column(nullable = false)
    private Double size;
    /**
     * 创建时间
     */
    @Column(nullable = false)
    @CreationTimestamp
    private Date gmtCreate;
    /**
     * 更新时间
     */
    @Column(nullable = false)
    @UpdateTimestamp
    private Date gmtModified;

    public Upload() {
    }

    public Upload(String studentId, String workId, String mime, Double size) {
        this.studentId = studentId;
        this.workId = workId;
        this.mime = mime;
        this.size = size;
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

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
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
                ", size=" + size +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                '}';
    }
}
