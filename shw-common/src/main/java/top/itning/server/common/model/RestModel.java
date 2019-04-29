package top.itning.server.common.model;


import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

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

    public static <T> Mono<RestModel> toMono(Mono<T> data) {
        return data.map(RestModel::new);
    }

    public static <T> Mono<RestModel> toMono(HttpStatus status, String msg, Mono<T> data) {
        return data.map(r -> new RestModel<>(status, msg, r));
    }

    public static <T> Mono<ServerResponse> ok(Mono<T> data) {
        return ServerResponse.ok().body(toMono(data), RestModel.class);
    }

    public static <T> Mono<ServerResponse> created(Mono<T> data) {
        return created("创建成功", data);
    }

    public static <T> Mono<ServerResponse> created(String msg, Mono<T> data) {
        return ServerResponse.status(HttpStatus.CREATED).body(toMono(HttpStatus.CREATED, msg, data), RestModel.class);
    }

    public static Mono<ServerResponse> noContent() {
        return ServerResponse.noContent().build();
    }

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
