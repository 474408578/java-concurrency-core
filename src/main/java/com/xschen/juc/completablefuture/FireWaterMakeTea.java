package com.xschen.juc.completablefuture;

import com.xschen.utils.SmallTool;

import java.util.concurrent.CompletableFuture;

/**
 * 烧水泡茶程序
 * @author xschen
 */


public class FireWaterMakeTea {

    public static void main(String[] args) {
        CompletableFuture<String> tea = CompletableFuture.supplyAsync(() -> {
            SmallTool.printTimeAndThread("洗茶壶");
            SmallTool.sleepMillis(100);
            SmallTool.printTimeAndThread("洗茶杯");
            SmallTool.sleepMillis(200);
            SmallTool.printTimeAndThread("拿茶叶");
            SmallTool.sleepMillis(100);
            return "龙井";
        });

        SmallTool.printTimeAndThread("洗水壶");
        SmallTool.sleepMillis(100);
        SmallTool.printTimeAndThread("烧开水");
        SmallTool.sleepMillis(1500);
        String teaName = tea.join();
        SmallTool.printTimeAndThread(String.format("拿到茶叶： %s", teaName));
        SmallTool.printTimeAndThread("上茶: " + teaName);

    }
}
