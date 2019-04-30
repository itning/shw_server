package top.itning.server.common.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import top.itning.server.common.exception.PermissionsException;
import top.itning.server.common.exception.RoleException;
import top.itning.server.common.model.LoginUser;

/**
 * 前置条件
 *
 * @author itning
 * @date 2019/4/30 10:36
 */
public class Preconditions {
    /**
     * 必须是学生身份
     *
     * @param request {@link ServerRequest}
     * @throws PermissionsException 如果角色为空
     * @throws RoleException        如果身份验证不通过
     */
    public static void mustStudentLogin(ServerRequest request) {
        String userType = request.queryParam("userType").orElseThrow(() -> new PermissionsException("角色为空"));
        if (!userType.equals(LoginUser.STUDENT_USER)) {
            throw new RoleException("FORBIDDEN", HttpStatus.FORBIDDEN);
        }
    }

    /**
     * 必须是教师身份
     *
     * @param request {@link ServerRequest}
     * @throws PermissionsException 如果角色为空
     * @throws RoleException        如果身份验证不通过
     */
    public static void mustTeacherLogin(ServerRequest request) {
        String userType = request.queryParam("userType").orElseThrow(() -> new PermissionsException("角色为空"));
        if (userType.equals(LoginUser.STUDENT_USER)) {
            throw new RoleException("FORBIDDEN", HttpStatus.FORBIDDEN);
        }
    }

    public static String getNo(ServerRequest request) {
        return request.queryParam("no").orElseThrow(() -> new PermissionsException("no is null"));
    }

    public static String getName(ServerRequest request) {
        return request.queryParam("name").orElseThrow(() -> new PermissionsException("name is null"));
    }
}
