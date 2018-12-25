package top.yunshu.shw_server.exception;

/**
 * 不存在Code异常
 * @author shulu
 */
public class NoCodeException extends Exception {
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public NoCodeException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
