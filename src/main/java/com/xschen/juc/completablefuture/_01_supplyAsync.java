package com.xschen.juc.completablefuture;

import com.xschen.utils.SmallTool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

/**
 * 演示 supplyAsync 用法
 * @author xschen
 * 创建一个CompletableFuture 对象: 开启一个异步任务
 * @see CompletableFuture#supplyAsync(Supplier)
 * @see CompletableFuture#supplyAsync(Supplier, Executor)
 *
 * @see CompletableFuture#runAsync(Runnable)
 * @see CompletableFuture#runAsync(Runnable, Executor)
 */
public class _01_supplyAsync {

    public static void main(String[] args) {
        SmallTool.printTimeAndThread("小白进入餐厅");
        SmallTool.printTimeAndThread("小白点了 番茄炒蛋 + 一碗米饭");

        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            SmallTool.printTimeAndThread("厨师炒菜");
            SmallTool.sleepMillis(200);
            SmallTool.printTimeAndThread("厨师打饭");
            SmallTool.sleepMillis(100);
            return "番茄炒蛋 + 米饭 做好了";
        });

        SmallTool.printTimeAndThread("小白在打王者");
        SmallTool.printTimeAndThread(String.format("%s， 小白开吃", cf.join()));

    }
}
