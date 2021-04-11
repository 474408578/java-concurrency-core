package com.xschen.juc.completablefuture;

import com.xschen.utils.SmallTool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author xschen
 * 描述一种串行关系：连接 两个异步任务
 * @see CompletableFuture#thenApply(Function)
 * @see CompletableFuture#thenApplyAsync(Function)
 * @see CompletableFuture#thenApplyAsync(Function, Executor) 
 * 
 * @see CompletableFuture#thenAccept(Consumer) 
 * @see CompletableFuture#thenAcceptAsync(Consumer) 
 * @see CompletableFuture#thenAcceptAsync(Consumer, Executor) 
 * 
 * @see CompletableFuture#thenRun(Runnable) 
 * @see CompletableFuture#thenRunAsync(Runnable) 
 * @see CompletableFuture#thenRunAsync(Runnable, Executor) 
 * 
 * @see CompletableFuture#thenCompose(Function)
 * @see CompletableFuture#thenComposeAsync(Function) (Function) 
 * @see CompletableFuture#thenComposeAsync(Function, Executor) 
 *
 */
public class _02_thenCompose {

    public static void main(String[] args) {
        SmallTool.printTimeAndThread("小白进入餐厅");
        SmallTool.printTimeAndThread("小白点了 番茄炒蛋 + 一碗米饭");

        /**
         * 1. 厨师炒菜
         * 2. 服务员打饭
         */
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            SmallTool.printTimeAndThread("厨师炒菜");
            SmallTool.sleepMillis(200);
            return "番茄炒蛋";
        }).thenCompose(dish -> CompletableFuture.supplyAsync(() -> {
                    SmallTool.printTimeAndThread("服务员打饭");
                    SmallTool.sleepMillis(100);
                    return dish + " + 米饭";
                }));

        SmallTool.printTimeAndThread("小白在打王者");
        SmallTool.printTimeAndThread(String.format("%s， 小白开吃", cf.join()));

//        applyAsync();
    }

    /**
     * 使用 applyAsync实现
     * @see CompletableFuture#supplyAsync(Supplier)
     */
    public static void applyAsync() {
        SmallTool.printTimeAndThread("小白进入餐厅");
        SmallTool.printTimeAndThread("小白点了 番茄炒蛋 + 一碗米饭");

        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            SmallTool.printTimeAndThread("厨师炒菜");
            SmallTool.sleepMillis(200);

            CompletableFuture<String> race = CompletableFuture.supplyAsync(() -> {
                SmallTool.printTimeAndThread("服务员打饭");
                SmallTool.sleepMillis(100);
                return "米饭";
            });
            return "番茄炒蛋" + " + " + race.join();
        });

        SmallTool.printTimeAndThread("小白在打王者");
        SmallTool.printTimeAndThread(String.format("%s， 小白开吃", cf.join()));
    }
}