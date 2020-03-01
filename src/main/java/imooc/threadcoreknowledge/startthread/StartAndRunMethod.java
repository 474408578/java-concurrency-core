package imooc.threadcoreknowledge.startthread;

/**
 * 对比start和run两种启动线程的方法
 *
 *
 * start方法
 *      含义：启动新线程，告诉JVM启动一个新线程，何时去启动线程却是随机的。
 */
public class StartAndRunMethod {

    public static void main(String[] args) {
        Runnable runnable = ()-> {
            System.out.println(Thread.currentThread().getName());
        };

        runnable.run();//主线程来执行的
        // main

        new Thread(runnable).start();
        // Thread-0
    }
}
