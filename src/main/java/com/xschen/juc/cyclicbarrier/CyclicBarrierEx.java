package com.xschen.juc.cyclicbarrier;

import com.xschen.juc.threadpool.ThreadPoolBuilder;
import com.xschen.utils.ThreadUtil;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * {@link CyclicBarrier} 示例
 * @author xschen
 * @see CyclicBarrier 一组线程之间相互等待
 */


public class CyclicBarrierEx {
    private static ThreadPoolExecutor threadPoolExecutor = ThreadPoolBuilder
            .fixedPool()
            .setPoolSize(3)
            .setThreadNamePrefix("student").build();

    public static void main(String[] args) {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

        for (int i = 0; i < 3; i++) {
            threadPoolExecutor.execute(new Cycling(cyclicBarrier));
        }

        ThreadUtil.sleep(2, TimeUnit.SECONDS);
        threadPoolExecutor.shutdown();
    }

    static class Cycling implements Runnable {

        private static final Random random = new Random();

        private CyclicBarrier cyclicBarrier;

        public Cycling(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            ThreadUtil.printTimeAndThread("go from main gate to the bike station");
            int number = random.nextInt(900) + 100;
            ThreadUtil.sleepMillis(number);
            ThreadUtil.printTimeAndThread("arrive at bike station take " + number +
                    " seconds and start waiting for his classmates");
            try {
                cyclicBarrier.await();
                ThreadUtil.printTimeAndThread("start cycling...");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
