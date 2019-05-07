package top.itning.server.shwwork.client.entity;

/**
 * 学生
 *
 * @author itning
 * @date 2019/4/30 13:08
 */
public class Student {
    /**
     * 学号
     */
    private String no;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 姓名
     */
    private String name;
    /**
     * 班级
     */
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
