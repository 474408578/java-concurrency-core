package com.xschen.juc.semaphore;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

/**
 * 通过 {@link Semaphore} 信号量模型示例： 实现一个线程安全的累加器
 * @author xschen
 */


public class SemaphoreAccumulator {

    // 只允许一个线程进入临界区
    private Semaphore semaphore = new Semaphore(1);
    private int count;

    public SemaphoreAccumulator(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void addOne() {
        try {
            semaphore.acquire();
            count++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 用于主线程等待两个线程执行完成
        CountDownLatch countDownLatch = new CountDownLatch(2);
        SemaphoreAccumulator semaphoreAccumulator = SemaphoreAccumulator.getInstance();
        new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                semaphoreAccumulator.addOne();
            }
            countDownLatch.countDown();
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                semaphoreAccumulator.addOne();
            }
            countDownLatch.countDown();
        }).start();

        countDownLatch.await();
        System.out.println(semaphoreAccumulator.getCount());
    }

    /**
     * 静态类实现单例， JVM 来保证的
     */
    static class SemaphoreAccumulatorHolder {
        private static SemaphoreAccumulator semaphoreAccumulator = new SemaphoreAccumulator(0);
    }

    public static SemaphoreAccumulator getInstance() {
        return SemaphoreAccumulatorHolder.semaphoreAccumulator;
    }
}
