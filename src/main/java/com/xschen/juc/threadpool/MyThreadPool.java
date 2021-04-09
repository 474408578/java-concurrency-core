package com.xschen.juc.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 简化的线程池，仅用来说明工作原理
 *
 *
 * @author xschen
 */


public class MyThreadPool {

    // 利用阻塞队列实现 生产者-消费者 模式
    BlockingQueue<Runnable> workQueue;

    // 保存内部工作线程
    List<WorkerThread> threads = new ArrayList<>();

    public MyThreadPool(int poolSize, BlockingQueue<Runnable> workQueue) {
        this.workQueue = workQueue;
        for (int i = 0; i < poolSize; i++) {
            WorkerThread workerThread = new WorkerThread();
            workerThread.start();
            threads.add(workerThread);
        }
    }


    /**
     * 提交任务
     * @param command
     */
    void execute(Runnable command) {
        try {
            workQueue.put(command);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 工作线程，负责消费任务，并执行任务
     */
    class WorkerThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Runnable task = workQueue.take();
                    task.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // 重置interrupte标志位
                }
            }
        }
    }

    public static void main(String[] args) {
        LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(2);

        MyThreadPool pool = new MyThreadPool(10, workQueue);
        pool.execute(() -> {
            System.out.println("hello");
        });
    }
}


