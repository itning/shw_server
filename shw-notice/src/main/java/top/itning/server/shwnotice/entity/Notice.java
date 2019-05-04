package top.itning.server.shwnotice.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * 通知
 *
 * @author itning
 */
@Document(collection = "notice")
public class Notice {
    /**
     * 标识通知
     */
    @Id
    private String id;
    /**
     * 发送者昵称
     */
    @Field("send_name")
    private String sendName;
    /**
     * 发送者ID
     */
    @Field("send_id")
    private String sendId;
    /**
     * 接收者ID
     */
    @Indexed
    @Field("receive_id")
    private String receiveId;
    /**
     * 接收者昵称
     */
    @Field("receive_name")
    private String receiveName;
    /**
     * 通知标题
     */
    @Field("title")
    private String title;
    /**
     * 通知内容
     */
    @Field("conten")
    private String content;
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

    public static Notice createReviewInstance(String workId,
                                              String studentId,
                                              String sendName,
                                              String sendId,
                                              String receiveId,
                                              String receiveName,
                                              String workName) {
        Notice notice = new Notice();
        notice.setId(studentId + workId);
        notice.setSendName(sendName);
        notice.setSendId(sendId);
        notice.setReceiveId(receiveId);
        notice.setReceiveName(receiveName);
        notice.setTitle("作业批阅通知");
        notice.setContent(workName + "已经被批改");
        return notice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public String getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        return "Notice{" +
                "id='" + id + '\'' +
                ", sendName='" + sendName + '\'' +
                ", sendId='" + sendId + '\'' +
                ", receiveId='" + receiveId + '\'' +
                ", receiveName='" + receiveName + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                '}';
    }
}
