package com.xschen.juc.completablefuture;

import com.xschen.utils.SmallTool;

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
            SmallTool.printTimeAndThread("洗水壶");
            SmallTool.sleepMillis(100);
            SmallTool.printTimeAndThread("烧开水");
            SmallTool.sleepMillis(1500);
        });

        /**
         * 任务2：洗茶壶 -> 洗茶杯 -> 拿茶叶
         */
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            SmallTool.printTimeAndThread("洗茶壶");
            SmallTool.sleepMillis(100);
            SmallTool.printTimeAndThread("洗茶杯");
            SmallTool.sleepMillis(200);
            SmallTool.printTimeAndThread("拿茶叶");
            SmallTool.sleepMillis(100);
            return "龙井";
        });


        /**
         * 任务3：泡茶
         */
        CompletableFuture<String> f3 = cf1.thenCombine(cf2, (__, tf) -> {
            SmallTool.printTimeAndThread(String.format("拿到茶叶： %s", tf));
            SmallTool.printTimeAndThread("泡茶 ");
            return "上茶：" + tf;

        });
        SmallTool.printTimeAndThread(f3.join());
    }
}
