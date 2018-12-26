package top.yunshu.shw.server.exception;

import org.springframework.http.HttpStatus;

/**
 * 空属性异常
 *
 * @author itning
 */
public class NullFiledException extends BaseException {
    public NullFiledException(String msg, HttpStatus code) {
        super(msg, code);
    }
}
