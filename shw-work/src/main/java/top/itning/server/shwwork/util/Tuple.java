package top.itning.server.shwwork.util;

import reactor.util.annotation.NonNull;

import java.util.Objects;

/**
 * 包含两个对象的Tuple
 *
 * @author itning
 * @date 2019/4/30 21:47
 */
public class Tuple<T1, T2> {
    @NonNull
    final T1 t1;
    @NonNull
    final T2 t2;

    public Tuple(T1 t1, T2 t2) {
        this.t1 = Objects.requireNonNull(t1, "t1");
        this.t2 = Objects.requireNonNull(t2, "t2");
    }

    public T1 getT1() {
        return t1;
    }

    public T2 getT2() {
        return t2;
    }
}
