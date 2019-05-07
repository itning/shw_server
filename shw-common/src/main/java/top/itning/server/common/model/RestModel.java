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
    /**
     * 状态码
     */
    private int code;
    /**
     * 消息
     */
    private String msg;
    /**
     * 负载
     */
    private T data;

    /**
     * 将{@link Mono<T>}转换为{@link Mono<RestModel>}
     * 使用默认状态码和消息
     *
     * @param data 负载数据
     * @param <T>  数据类型
     * @return {@link RestModel}
     */
    public static <T> Mono<RestModel> toMono(Mono<T> data) {
        return data.map(RestModel::new);
    }

    /**
     * 将{@link Mono<T>}转换为{@link Mono<RestModel>}
     *
     * @param status 状态码
     * @param msg    消息
     * @param data   数据
     * @param <T>    数据类型
     * @return {@link RestModel}
     */
    public static <T> Mono<RestModel> toMono(HttpStatus status, String msg, Mono<T> data) {
        return data.map(r -> new RestModel<>(status, msg, r));
    }

    /**
     * 将{@link Mono<T>}转换为{@link Mono<ServerResponse>}
     * 并返回200状态码且使用默认消息
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return {@link ServerResponse}
     */
    public static <T> Mono<ServerResponse> ok(Mono<T> data) {
        return ServerResponse.ok().body(toMono(data), RestModel.class);
    }

    /**
     * 将{@link Mono<T>}转换为{@link Mono<ServerResponse>}
     * 并返回201状态码且使用默认消息
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return {@link ServerResponse}
     */
    public static <T> Mono<ServerResponse> created(Mono<T> data) {
        return created(data, "创建成功");
    }

    /**
     * 将{@link Mono<T>}转换为{@link Mono<ServerResponse>}
     * 并返回201状态码
     *
     * @param data 数据
     * @param msg  消息
     * @param <T>  数据类型
     * @return {@link ServerResponse}
     */
    public static <T> Mono<ServerResponse> created(Mono<T> data, String msg) {
        return ServerResponse.status(HttpStatus.CREATED).body(toMono(HttpStatus.CREATED, msg, data), RestModel.class);
    }

    /**
     * 返回204状态码不携带任何数据消息
     *
     * @return {@link ServerResponse}
     */
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
