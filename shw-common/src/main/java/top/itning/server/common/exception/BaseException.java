package top.itning.server.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 异常基类
 *
 * @author itning
 */
public abstract class BaseException extends RuntimeException {
    private String msg;
    private HttpStatus code;

    public BaseException(String msg, HttpStatus code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }
}
