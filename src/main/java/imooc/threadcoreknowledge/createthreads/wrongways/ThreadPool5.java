package imooc.threadcoreknowledge.createthreads.wrongways;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

/**
 * 线程池创建线程的方式, 本质是使用Runnable和Thread类来创建的
 */
public class ThreadPool5 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i=0; i<1000; i++) {
            executorService.submit(new Task(){});
        }
    }
}


class Task implements Runnable {
    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
    }
}