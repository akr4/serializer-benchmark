package net.physalis.accesslog;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import java.util.Arrays;

public enum Method {
    GET(0), POST(1), HEAD(2), PUT(3), DELETE(4);

    private int code;

    Method(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static Method findByCode(final int code) {
        return Iterables.find(Arrays.asList(Method.values()), new Predicate<Method>() {
            @Override public boolean apply(Method m) {
                return m.code == code;
            }
        });
    }
}
