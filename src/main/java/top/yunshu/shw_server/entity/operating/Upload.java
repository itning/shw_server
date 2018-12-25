package top.yunshu.shw_server.entity.operating;

import org.hibernate.annotations.CreationTimestamp;


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
    private String studentId;
    /**
     * 作业ID
     */
    @Id
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
     * 上传时间
     */
    @Column(nullable = false)
    @CreationTimestamp
    private Date createTime;

    public Upload() {
    }

    public Upload(String studentId, String workId, String mime, Double size, Date createTime) {
        this.studentId = studentId;
        this.workId = workId;
        this.mime = mime;
        this.size = size;
        this.createTime = createTime;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Upload{" +
                "studentId='" + studentId + '\'' +
                ", workId='" + workId + '\'' +
                ", mime='" + mime + '\'' +
                ", size=" + size +
                ", createTime=" + createTime +
                '}';
    }
}
