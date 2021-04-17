package com.xschen.feature.threadstate;

import com.xschen.utils.ThreadDumpHelper;
import com.xschen.utils.ThreadUtil;

import java.util.concurrent.TimeUnit;

/**
 * 线程状态 一览
 * @author xschen
 */
public class ThreadState {

    private static ThreadDumpHelper dumpHelper = new ThreadDumpHelper();

    public static void main(String[] args) {
        new Thread(new Waiting(), "waiting").start(); // WAITING
        new Thread(new TimeWaiting(), "timeWaiting").start(); // TIMED_WAITING
        new Thread(new Blocked(), "block1").start(); // TIMED_WAITING
        new Thread(new Blocked(), "block2").start(); // BLOCKED

        dumpHelper.tryThreadDump();

    }

    static class Waiting implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (Waiting.class) {
                    try {
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }

    static class TimeWaiting implements Runnable {
        @Override
        public void run() {
            while (true) {
                ThreadUtil.sleep(2, TimeUnit.MINUTES);
            }
        }
    }

    static class Blocked implements Runnable {
        @Override
        public void run() {
            while (true){
                synchronized (Blocked.class){
                    ThreadUtil.sleep(2, TimeUnit.MINUTES);
                }
            }
        }
    }
}
