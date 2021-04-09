package com.xschen.utils;

import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

/**
 * 小工具
 * @author xschen
 */


public class SmallTool {

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

}
