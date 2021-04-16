package com.xschen.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.StringJoiner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 小工具
 * @author xschen
 */


public class ThreadUtil {

    private static Logger logger = LoggerFactory.getLogger(ThreadUtil.class);

    /**
     * sleep 等待，单位为 毫秒，已捕捉异常
     * @param durationMillis
     */
    public static void sleepMillis(long durationMillis) {
        try {
            TimeUnit.MILLISECONDS.sleep(durationMillis);
        } catch (InterruptedException e) {
            /**
             *  将线程中断标志位置为true
             */
            Thread.currentThread().interrupt();
        }
    }

    /**
     * sleep 等待，单位自己设置，已捕捉异常
     * @param duration
     * @param unit
     */
    public static void sleep(long duration, TimeUnit unit) {
        long millis = unit.toMillis(duration);
        sleepMillis(millis);
    }

    public static void printTimeAndThread(String tag) {
        String result = new StringJoiner("\t|\t")
                .add(String.valueOf(System.currentTimeMillis()))
                .add(String.valueOf(Thread.currentThread().getId()))
                .add(Thread.currentThread().getName())
                .add(tag)
                .toString();

        System.out.println(result);
    }

    public static void printThreadPoolStat(ThreadPoolExecutor threadPool) {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            logger.info("PoolSize: {}", threadPool.getPoolSize());
            logger.info("Active Threads: {}", threadPool.getActiveCount());
            logger.info("Number of Tasks completed: {}", threadPool.getCompletedTaskCount());
            logger.info("Number of Tasks in Queue: {}", threadPool.getQueue().size());
            logger.info("============================================");
        }, 0, 1, TimeUnit.SECONDS);
    }

    /**
     * 纯粹为了提醒下处理InterruptedException的正确方式，除非你是在写不可中断的任务.
     */
    public static void handleInterruptedException() {
        Thread.currentThread().interrupt();
    }

}
