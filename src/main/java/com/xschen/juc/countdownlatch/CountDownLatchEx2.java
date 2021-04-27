package com.xschen.juc.countdownlatch;

import com.xschen.juc.threadpool.ThreadPoolBuilder;
import com.xschen.utils.ThreadUtil;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * {@link CountDownLatch} 示例
 * @author xschen
 *
 * @see CountDownLatch 解决多个线程等待一个线程的场景
 */
public class CountDownLatchEx2 {

    private static ThreadPoolExecutor threadPoolExecutor = ThreadPoolBuilder
            .fixedPool()
            .setPoolSize(5)
            .setThreadNamePrefix("thread-player").build();

    private static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
        ThreadUtil.printTimeAndThread("every player has one seconds to prepare...");
        ThreadUtil.sleep(1, TimeUnit.SECONDS);
        ThreadUtil.printTimeAndThread("the five players waiting for the referee's starting gun");
        latch.countDown();
        for (int i = 0; i < 5; i++) {
            threadPoolExecutor.execute(new RaceDemo());
        }

        threadPoolExecutor.shutdown();
    }

    static class RaceDemo implements Runnable {

        public static final Random random = new Random();
        @Override
        public void run() {
            try {
                latch.await();
                ThreadUtil.printTimeAndThread("begin to run");
                int number = random.nextInt(900) + 100;
                ThreadUtil.sleepMillis(number);
                ThreadUtil.printTimeAndThread("finish the race takes " + number + " seconds");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
