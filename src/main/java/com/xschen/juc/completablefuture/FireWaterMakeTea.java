package com.xschen.juc.completablefuture;

import com.xschen.utils.ThreadUtil;

import java.util.concurrent.CompletableFuture;

/**
 * 烧水泡茶程序
 * @author xschen
 */


public class FireWaterMakeTea {

    public static void main(String[] args) {

        /**
         * 任务1：洗水壶 -> 烧开水
         */
        CompletableFuture<Void> cf1 = CompletableFuture.runAsync(() -> {
            ThreadUtil.printTimeAndThread("洗水壶");
            ThreadUtil.sleepMillis(100);
            ThreadUtil.printTimeAndThread("烧开水");
            ThreadUtil.sleepMillis(1500);
        });

        /**
         * 任务2：洗茶壶 -> 洗茶杯 -> 拿茶叶
         */
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            ThreadUtil.printTimeAndThread("洗茶壶");
            ThreadUtil.sleepMillis(100);
            ThreadUtil.printTimeAndThread("洗茶杯");
            ThreadUtil.sleepMillis(200);
            ThreadUtil.printTimeAndThread("拿茶叶");
            ThreadUtil.sleepMillis(100);
            return "龙井";
        });


        /**
         * 任务3：泡茶
         */
        CompletableFuture<String> f3 = cf1.thenCombine(cf2, (__, tf) -> {
            ThreadUtil.printTimeAndThread(String.format("拿到茶叶： %s", tf));
            ThreadUtil.printTimeAndThread("泡茶 ");
            return "上茶：" + tf;

        });
        ThreadUtil.printTimeAndThread(f3.join());
    }
}
