package top.yunshu.shw.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 用于存储学生元数据的实体
 *
 * @author itning
 */
@Entity
public class Student implements Serializable {
    /**
     * 学号
     */
    @Id
    @Column(length = 50)
    private String no;
    /**
     * 登录名
     */
    @Column(length = 50)
    private String loginName;
    /**
     * 姓名
     */
    @Column(length = 50)
    private String name;
    /**
     * 班级
     */
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
