package top.yunshu.shw.server.entity;

import java.io.Serializable;

/**
 * 登陆用户
 *
 * @author itning
 */
public class LoginUser implements Serializable {

    /**
     * id : ID
     * no : 学号或教师编号
     * loginName : 身份证号码或登录名
     * userType : 99(学生) 13(教师)
     * name : 姓名
     * phone :
     * email :
     * mobile :
     * loginIp : 登陆IP
     * remarks : uninitialized
     */

    private String id;
    private String no;
    private String loginName;
    private String userType;
    private String name;
    private String phone;
    private String email;
    private String mobile;
    private String loginIp;
    private String remarks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "id='" + id + '\'' +
                ", no='" + no + '\'' +
                ", loginName='" + loginName + '\'' +
                ", userType='" + userType + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", loginIp='" + loginIp + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
