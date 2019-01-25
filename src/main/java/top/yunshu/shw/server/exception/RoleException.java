package top.yunshu.shw.server.exception;

import org.springframework.http.HttpStatus;

/**
 * 用户角色异常
 *
 * @author itning
 */
public class RoleException extends BaseException {
    public RoleException(String msg, HttpStatus code) {
        super(msg, code);
    }
}
