package com.xschen.juc.future;

import com.xschen.utils.ThreadUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 烧水泡茶 程序
 * @author xschen
 */
public class FireWaterMakeTea {

    public static void main(String[] args) throws InterruptedException {
        FutureTask<String> ft2 = new FutureTask<>(new Task2());
        FutureTask<String> ft1 = new FutureTask<>(new Task1(ft2));
        Thread t1 = new Thread(ft1);
        Thread t2 = new Thread(ft2);
        t2.start();
        t1.start();

        t1.join();
    }

    /**
     * 洗水壶、烧开水、泡茶
     */
    static class Task1 implements Callable<String> {
        // 需要拿 Task2 的结果
        private FutureTask<String> futureTask;

        public Task1(FutureTask futureTask) {
            this.futureTask = futureTask;
        }

        @Override
        public String call() throws Exception {
            ThreadUtil.printTimeAndThread("洗水壶");
            ThreadUtil.sleepMillis(100);
            ThreadUtil.printTimeAndThread("烧开水");
            ThreadUtil.sleepMillis(1500);
            String teaName = futureTask.get();
            ThreadUtil.printTimeAndThread(String.format("拿到茶叶： %s", teaName));
            ThreadUtil.printTimeAndThread("上茶: " + teaName);
            ThreadUtil.sleepMillis(100);
            return "上茶: " + teaName;
        }
    }

    /**
     * 洗茶壶、洗茶杯、拿茶叶
     */
    static class Task2 implements Callable<String> {
        @Override
        public String call() throws Exception {
            ThreadUtil.printTimeAndThread("洗茶壶");
            ThreadUtil.sleepMillis(100);
            ThreadUtil.printTimeAndThread("洗茶杯");
            ThreadUtil.sleepMillis(200);
            ThreadUtil.printTimeAndThread("拿茶叶");
            ThreadUtil.sleepMillis(100);
            return "龙井";
        }
    }
}
