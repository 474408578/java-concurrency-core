package imooc.threadcoreknowledge.uncaughtexception;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 利用UncaughtExceptionHandler进行捕获
 */
public class CantCatchDirectlySolutionFunction2 implements Runnable {

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler("捕获器1"));

        new Thread(new CantCatchDirectlySolutionFunction2(), "Thread-1").start();
        new Thread(new CantCatchDirectlySolutionFunction2(), "Thread-2").start();
        new Thread(new CantCatchDirectlySolutionFunction2(), "Thread-3").start();
        new Thread(new CantCatchDirectlySolutionFunction2(), "Thread-4").start();
    }
    @Override
    public void run() {
        throw new RuntimeException();
    }
}

/**
 * 定义自己的MyUncaughtExceptionHandler
 */
class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    private String name;

    public MyUncaughtExceptionHandler(String name) {
        this.name = name;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Logger logger = Logger.getAnonymousLogger();
        logger.log(Level.WARNING, "线程异常，终止啦" + t.getName(), e);
        System.out.println(name + "捕获了异常" + t.getName() + "异常" + e);
    }
}
