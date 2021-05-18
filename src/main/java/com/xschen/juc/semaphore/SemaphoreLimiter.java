package com.xschen.juc.semaphore;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

/**
 * 使用 {@link Semaphore} 实现一个限流器
 * @author xschen
 */


public class SemaphoreLimiter<T, R> {

    final List<T> pool;

    final Semaphore semaphore;

    public SemaphoreLimiter(int size, T t) {
        pool = new Vector<T>() {};
        for (int i = 0; i < size; i++) {
            pool.add(t);
        }
        this.semaphore = new Semaphore(size);
    }

    /**
     * @param function 传入的函数式方法
     * @return
     * @throws InterruptedException
     */
    R exec(Function<T, R> function) throws InterruptedException {
        T t = null;
        try {
            semaphore.acquire();
            t = pool.remove(0);
            return function.apply(t);
        } finally {
            pool.add(t);
            semaphore.release();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SemaphoreLimiter<Object, String> semaphoreLimiter = new SemaphoreLimiter(10, "worker");
        // 通过对象池获取 t 之后执行.
        semaphoreLimiter.exec(t -> {
            System.out.println(t);
            return t.toString();
        });
    }
}
