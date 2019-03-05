package top.yunshu.shw.server.util;

import org.springframework.http.HttpStatus;
import top.yunshu.shw.server.entity.LoginUser;
import top.yunshu.shw.server.exception.RoleException;

/**
 * 角色工具类
 * 99(学生) 13(教师)
 *
 * @author itning
 */
public class RoleUtils {
    private static final String USER_TYPE_STUDENT = "99";

    private RoleUtils() {
    }

    /**
     * 检查登陆用户是否是学生
     *
     * @param loginUser {@link LoginUser}
     * @throws RoleException 如果不是学生
     */
    public static void checkRoleIsStudent(LoginUser loginUser) {
        if (!loginUser.getUserType().equals(USER_TYPE_STUDENT)) {
            throw new RoleException("FORBIDDEN", HttpStatus.FORBIDDEN);
        }
    }

}
