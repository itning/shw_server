package top.yunshu.shw.server.exception;

/**
 * ID不存在
 *
 * @author itning
 */
public class NoSuchIdException extends RuntimeException {
    private String msg;

    public NoSuchIdException(String message) {
        super(message);
        this.msg = message;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
