package top.yunshu.shw.server.entity;

import java.io.Serializable;

/**
 * Rest 返回消息
 *
 * @author itning
 */
public class RestModel implements Serializable {
    private int code;
    private String msg;
    private String url;
    private Object data;

    public RestModel() {
    }

    public RestModel(int code, String msg, String url, Object data) {
        this.code = code;
        this.msg = msg;
        this.url = url;
        this.data = data;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
