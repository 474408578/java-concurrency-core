package com.xschen.juc.threadlocal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author xschen
 */
public class MyThreadLocal<T> {

    private final Map<Thread, T> locals = new ConcurrentHashMap();

    T get() {
        return locals.get(Thread.currentThread());
    }

    void set(T t) {
        locals.put(Thread.currentThread(), t);
    }
}
