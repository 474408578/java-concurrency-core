package com.xschen.pattern.blockingqueue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xschen
 */
public class BlockingQueue<T extends Object> {

    private final T[] items;
    private int count;
    private final Lock lock = new ReentrantLock();
    /**
     * 条件变量，队列不满
     */
    private final Condition notFull;

    /**
     * 条件变量，队列不空
     */
    private final Condition notEmpty;

    public BlockingQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }
        this.items = (T[]) new Object[capacity];
        this.count = 0;
        this.notFull = lock.newCondition();
        notEmpty = lock.newCondition();
    }

    /**
     * 入队
     * @param t
     * @throws InterruptedException
     */
    public void enqueue(T t) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                notFull.await();// 等待 队列不满，才能执行入队操作
            }
            items[count++] = t; // 入队
            notEmpty.signalAll(); // 通知可出队
        } finally {
            lock.unlock();
        }

    }


    /**
     * 出队
     * @return
     * @throws InterruptedException
     */
    public T dequeue() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await(); // 等待队列不空，才能执行出队操作
            }
            T t = items[--count];
            notFull.signalAll();
            /**
             * 1. try中有return, 会先将值暂存，无论finally语句中对该值做什么处理，
             *     最终返回的都是try语句中的暂存值。
             * 2. 当try与finally语句中均有return语句，会忽略try中return。
             */
            return t;
        } finally {
            lock.unlock();
        }
    }
}
