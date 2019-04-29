package top.itning.server.common.model;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * Rest 返回消息
 *
 * @author itning
 */
public class RestModel<T> implements Serializable {
    private int code;
    private String msg;
    private T data;

    public RestModel() {
    }

    public RestModel(HttpStatus status, String msg, T data) {
        this(status.value(), msg, data);
    }

    public RestModel(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public RestModel(T data) {
        this(HttpStatus.OK, "查询成功", data);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
