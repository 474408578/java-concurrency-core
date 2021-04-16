package com.xschen.juc.completablefuture;

import com.xschen.utils.ThreadUtil;

import java.util.concurrent.CompletableFuture;

/**
 * 任务后置处理
 * @author xschen
 */
public class _04_thenApply {

    public static void main(String[] args) {
        ThreadUtil.printTimeAndThread("小白吃好了");
        ThreadUtil.printTimeAndThread("小白 结账、要求开发票");

        CompletableFuture<String> invoice = CompletableFuture.supplyAsync(() -> {
            ThreadUtil.printTimeAndThread("服务员收款 500元");
            ThreadUtil.sleepMillis(100);
            return "500";
        }).thenApplyAsync(money -> {
            ThreadUtil.printTimeAndThread(String.format("服务员开发票 面额 %s元", money));
            ThreadUtil.sleepMillis(200);
            return String.format("%s元发票", money);
        });

        ThreadUtil.printTimeAndThread("小白 接到朋友的电话，想一起打游戏");
        ThreadUtil.printTimeAndThread(String.format("小白拿到%s，准备回家", invoice.join()));
    }
}
