package com.xschen.juc.countdownlatch;

import com.xschen.juc.threadpool.ThreadPoolBuilder;
import com.xschen.utils.ThreadUtil;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * {@link CountDownLatch} 示例
 * @author xschen
 *
 * @see CountDownLatch 解决一个线程等待多个线程的场景
 */
public class CountDownLatchEx1 {

    private static ThreadPoolExecutor threadPoolExecutor = ThreadPoolBuilder
            .fixedPool()
            .setPoolSize(5)
            .setThreadNamePrefix("thread-player").build();

    private static CountDownLatch latch = new CountDownLatch(5);

    public static void main(String[] args) throws InterruptedException {
        ThreadUtil.printTimeAndThread("Begin racing: wait for the five player to finish running");
        for (int i = 0; i < 5; i++) {
            threadPoolExecutor.execute(new RaceDemo());
        }

        latch.await();
        ThreadUtil.printTimeAndThread("the race is finished, all players finished the race");
        threadPoolExecutor.shutdownNow();
    }

    static class RaceDemo implements Runnable {

        public static final Random random = new Random();

        @Override
        public void run() {
            ThreadUtil.printTimeAndThread("begin to run");
            int number = random.nextInt(900) + 100;
            ThreadUtil.sleepMillis(number);
            ThreadUtil.printTimeAndThread("finish the race takes " + number + " seconds");
            latch.countDown();
        }
    }
}