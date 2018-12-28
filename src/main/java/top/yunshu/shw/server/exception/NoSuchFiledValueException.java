package top.yunshu.shw.server.exception;

import org.springframework.http.HttpStatus;

/**
 * 属性值不存在
 *
 * @author itning
 */
public class NoSuchFiledValueException extends BaseException {
    public NoSuchFiledValueException(String msg, HttpStatus code) {
        super(msg, code);
    }
}
