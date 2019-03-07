package top.yunshu.shw.server.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 通知
 *
 * @author shulu
 * @author itning
 */
@ApiModel(description = "通知")
@Entity
@Table(name = "notice", indexes = {
        @Index(name = "receive_id_index", columnList = "receive_id")
})
public class Notice implements Serializable {
    /**
     * 标识通知
     */
    @ApiModelProperty(required = true, value = "标识通知")
    @Id
    @Column(length = 50)
    private String id;
    /**
     * 发送者昵称
     */
    @ApiModelProperty(required = true, value = "发送者昵称")
    @Column(name = "send_name", nullable = false, length = 50)
    private String sendName;
    /**
     * 发送者ID
     */
    @ApiModelProperty(required = true, value = "发送者ID")
    @Column(name = "send_id", nullable = false, length = 50)
    private String sendId;
    /**
     * 接收者ID
     */
    @ApiModelProperty(required = true, value = "接收者ID")
    @Column(name = "receive_id", nullable = false, length = 50)
    private String receiveId;
    /**
     * 接收者昵称
     */
    @ApiModelProperty(required = true, value = "接收者昵称")
    @Column(name = "receive_name", nullable = false, length = 50)
    private String receiveName;
    /**
     * 通知标题
     */
    @ApiModelProperty(required = true, value = "通知标题")
    @Column(name = "title", nullable = false, length = 50)
    private String title;
    /**
     * 通知内容
     */
    @ApiModelProperty(required = true, value = "通知内容")
    @Column(name = "content", nullable = false)
    private String content;
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
