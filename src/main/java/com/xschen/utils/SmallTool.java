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


public class SmallTool {

    private static Logger logger = LoggerFactory.getLogger(SmallTool.class);

    public static void sleepMillis(long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

}
