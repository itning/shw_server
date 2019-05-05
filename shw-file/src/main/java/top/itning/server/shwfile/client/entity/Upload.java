package top.itning.server.shwfile.client.entity;

import java.util.Date;

/**
 * @author itning
 * @date 2019/5/2 16:48
 */
public class Upload implements Cloneable {
    /**
     * studentId+|+workId
     */
    private String id;
    /**
     * 学生ID
     */
    private String studentId;
    /**
     * 作业ID
     */
    private String workId;
    /**
     * 文件类型
     */
    private String mime;
    /**
     * 扩展名
     */
    private String extensionName;
    /**
     * 文件大小(bytes)
     */
    private long size;
    /**
     * 批阅信息
     */
    private String review;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 更新时间
     */
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

    public Upload init() {
        this.id = studentId + "|" + workId;
        this.review = "";
        Date date = new Date();
        this.gmtCreate = date;
        this.gmtModified = date;
        return this;
    }

    public Upload clones() {
        try {
            return (Upload) this.clone();
        } catch (CloneNotSupportedException e) {
            return new Upload();
        }
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
