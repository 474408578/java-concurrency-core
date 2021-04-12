package com.xschen.juc.threadlocal;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 为每个线程分配唯一的线程 Id
 * @author xschen
 */
public class ThreadId {

    private static final AtomicLong nextId = new AtomicLong(0);

    private static final ThreadLocal<Long> threadId =
            ThreadLocal.withInitial(() -> nextId.getAndIncrement());

    /**
     * 不同的线程执行此方法，返回的值是不同的
     * @return
     */
    public Long get() {
        return threadId.get();
    }

    public static void main(String[] args) throws InterruptedException {
        // 同一个线程，调用多次都是一样的.
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(threadId.get());
            }
        });
        thread.start();
        thread.join();

        System.out.println("------------------");

        // 不同线程，每次调用得到的值不一样.
        for (int i = 0; i < 10; i++) {
            new Thread(() -> System.out.println(threadId.get())).start();
        }
    }
}
