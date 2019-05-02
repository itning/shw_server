package top.itning.server.shwupload.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author itning
 * @date 2019/5/2 16:48
 */
@Document(collection = "upload")
public class Upload {
    /**
     * studentId+|+workId
     */
    @Id
    private String id;
    /**
     * 学生ID
     */
    @Field("student_id")
    private String studentId;
    /**
     * 作业ID
     */
    @Field("work_id")
    private String workId;
    /**
     * 文件类型
     */
    @Field("mime")
    private String mime;
    /**
     * 扩展名
     */
    @Field("extension_name")
    private String extensionName;
    /**
     * 文件大小(bytes)
     */
    @Field("size")
    private long size;
    /**
     * 批阅信息
     */
    @Field("review")
    private String review;
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

    public Upload() {
    }

    public Upload(String studentId, String workId, String mime, String extensionName, long size) {
        this.id = studentId + "|" + workId;
        this.studentId = studentId;
        this.workId = workId;
        this.mime = mime;
        this.extensionName = extensionName;
        this.size = size;
        this.review = "";
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

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
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
                "id='" + id + '\'' +
                ", studentId='" + studentId + '\'' +
                ", workId='" + workId + '\'' +
                ", mime='" + mime + '\'' +
                ", extensionName='" + extensionName + '\'' +
                ", size=" + size +
                ", review='" + review + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                '}';
    }
}
