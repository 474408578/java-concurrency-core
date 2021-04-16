package com.xschen.juc.completablefuture;

import com.xschen.utils.ThreadUtil;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * @author xschen
 * 描述一种And汇聚关系: 合并两个异步任务
 * @see CompletableFuture#thenCombine(CompletionStage, BiFunction)
 * @see CompletableFuture#thenCombineAsync(CompletionStage, BiFunction) 
 * @see CompletableFuture#thenCombineAsync(CompletionStage, BiFunction, Executor)
 *
 * @see CompletableFuture#thenAcceptBoth(CompletionStage, BiConsumer)
 * @see CompletableFuture#thenAcceptBothAsync(CompletionStage, BiConsumer)
 * @see CompletableFuture#thenAcceptBothAsync(CompletionStage, BiConsumer, Executor)
 *
 * @see CompletableFuture#runAfterBoth(CompletionStage, Runnable) 
 * @see CompletableFuture#runAfterBothAsync(CompletionStage, Runnable) 
 * @see CompletableFuture#runAfterBothAsync(CompletionStage, Runnable, Executor)
 */
public class _03_thenCombine {

    public static void main(String[] args) {
        ThreadUtil.printTimeAndThread("小白进入餐厅");
        ThreadUtil.printTimeAndThread("小白点了 番茄炒蛋 + 一碗米饭");

        /**
         * 厨师做番茄炒蛋
         */
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            ThreadUtil.printTimeAndThread("厨师炒菜");
            ThreadUtil.sleepMillis(200);
            return "番茄炒蛋";
        });

        /**
         * 服务员蒸饭
         */
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            ThreadUtil.printTimeAndThread("服务员蒸饭");
            ThreadUtil.sleepMillis(300);
            return "米饭";
        });

        /**
         * 服务员打饭，并上菜
         * dish: cf1 的返回值
         * rice: cf2 的返回值
         */
        CompletableFuture cf3 = cf1.thenCombine(cf2, (dish, rice) -> {
            ThreadUtil.printTimeAndThread("服务员打饭");
            ThreadUtil.sleepMillis(100);
            return String.format("%s + %s 好了", dish, rice);
        });

        ThreadUtil.printTimeAndThread("小白在打王者");
        ThreadUtil.printTimeAndThread(String.format("%s， 小白开吃", cf3.join()));

        //applyAsync();
    }

    /**
     * 用 supplyAsync也可以实现
     */
    private static void applyAsync() {

        ThreadUtil.printTimeAndThread("小白进入餐厅");
        ThreadUtil.printTimeAndThread("小白点了 番茄炒蛋 + 一碗米饭");

        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
            ThreadUtil.printTimeAndThread("厨师炒菜");
            ThreadUtil.sleepMillis(200);
            return "番茄炒蛋";
        });

        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            ThreadUtil.printTimeAndThread("服务员蒸饭");
            ThreadUtil.sleepMillis(300);
            return "米饭";
        });

        ThreadUtil.printTimeAndThread("小白在打王者");
        String result = String.format("%s + %s 好了", cf1.join(), cf2.join());
        ThreadUtil.printTimeAndThread("服务员打饭");
        ThreadUtil.sleepMillis(100);
        ThreadUtil.printTimeAndThread(String.format("%s， 小白开吃", result));

    }
}
