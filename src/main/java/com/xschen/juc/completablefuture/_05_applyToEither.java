package com.xschen.juc.completablefuture;

import com.xschen.utils.ThreadUtil;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author xschen
 * 描述一种 OR 汇聚关系：最先完成的任务
 * @see CompletableFuture#applyToEither(CompletionStage, Function)
 * @see CompletableFuture#applyToEitherAsync(CompletionStage, Function)
 * @see CompletableFuture#applyToEitherAsync(CompletionStage, Function, Executor)
 *
 * @see CompletableFuture#acceptEither(CompletionStage, Consumer)
 * @see CompletableFuture#acceptEitherAsync(CompletionStage, Consumer)
 * @see CompletableFuture#acceptEitherAsync(CompletionStage, Consumer, Executor)
 *
 * @see CompletableFuture#runAfterEither(CompletionStage, Runnable)
 * @see CompletableFuture#runAfterEitherAsync(CompletionStage, Runnable)
 * @see CompletableFuture#runAfterEitherAsync(CompletionStage, Runnable, Executor)
 */
public class _05_applyToEither {

    public static void main(String[] args) {
        ThreadUtil.printTimeAndThread("小白走出餐厅，来到公交站");
        ThreadUtil.printTimeAndThread("等待 700路 或者 800路 公交到来");

        CompletableFuture<String> bus700 = CompletableFuture.supplyAsync(() -> {
            ThreadUtil.printTimeAndThread("700路公交正在赶来");
            ThreadUtil.sleepMillis(100);
            return "700路到了";
        });

        CompletableFuture<String> bus800 = CompletableFuture.supplyAsync(() -> {
            ThreadUtil.printTimeAndThread("800路公交正在赶来");
            ThreadUtil.sleepMillis(200);
            return "800路到了";
        });

        CompletableFuture<String> cf = bus700.applyToEither(bus800, firstComeBus -> firstComeBus);

        ThreadUtil.printTimeAndThread(String.format("%s,小白坐车回家", cf.join()));
    }
}
