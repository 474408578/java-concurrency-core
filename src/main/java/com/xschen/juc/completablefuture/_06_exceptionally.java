package com.xschen.juc.completablefuture;

import com.xschen.utils.SmallTool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author xschen
 * 处理异常
 * @see CompletableFuture#exceptionally(Function)
 *
 * @see CompletableFuture#whenComplete(BiConsumer)
 * @see CompletableFuture#whenCompleteAsync(BiConsumer)
 * @see CompletableFuture#whenCompleteAsync(BiConsumer, Executor)
 *
 * @see CompletableFuture#handle(BiFunction)
 * @see CompletableFuture#handleAsync(BiFunction)
 * @see CompletableFuture#handleAsync(BiFunction, Executor)
 */
public class _06_exceptionally {

    public static void main(String[] args) {

        SmallTool.printTimeAndThread("小白走出餐厅，来到公交站");
        SmallTool.printTimeAndThread("等待 700路 或者 800路 公交到来");

        CompletableFuture<String> bus700 = CompletableFuture.supplyAsync(() -> {
            SmallTool.printTimeAndThread("700路公交正在赶来");
            SmallTool.sleepMillis(100);
            throw new RuntimeException("撞树上了");
            //return "700路到了";
        });

        CompletableFuture<String> bus800 = CompletableFuture.supplyAsync(() -> {
            SmallTool.printTimeAndThread("800路公交正在赶来");
            SmallTool.sleepMillis(200);
            return "800路到了";
        });

        CompletableFuture<String> cf = bus700.applyToEither(bus800, firstComeBus -> {
            if (firstComeBus.startsWith("700")) {
                //throw new RuntimeException("撞树上了");
            }
            return firstComeBus;
        }).exceptionally(e -> {
            SmallTool.printTimeAndThread(e.getMessage());
            SmallTool.printTimeAndThread("小白叫出租车");
            return "出租车 叫到了";
        });

        SmallTool.printTimeAndThread(String.format("%s,小白坐车回家", cf.join()));

    }
}
