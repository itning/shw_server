package top.yunshu.shw.server.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 用于存储学生元数据的实体
 *
 * @author itning
 */
@ApiModel(description = "用于存储学生元数据的实体")
@Entity
public class Student implements Serializable {
    /**
     * 学号
     */
    @ApiModelProperty(required = true, value = "学号")
    @Id
    @Column(length = 50)
    private String no;
    /**
     * 登录名
     */
    @ApiModelProperty("登录名")
    @Column(length = 50)
    private String loginName;
    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    @Column(length = 50)
    private String name;
    /**
     * 班级
     */
    @ApiModelProperty("班级")
    @Column(length = 50)
    private String clazzId;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClazzId() {
        return clazzId;
    }

    public void setClazzId(String clazzId) {
        this.clazzId = clazzId;
    }

    @Override
    public String toString() {
        return "Student{" +
                "no='" + no + '\'' +
                ", loginName='" + loginName + '\'' +
                ", name='" + name + '\'' +
                ", clazzId='" + clazzId + '\'' +
                '}';
    }
}
