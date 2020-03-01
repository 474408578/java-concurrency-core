package imooc.threadcoreknowledge.createthreads.wrongways;

import java.util.Timer;

/**
 * 使用定时器创建线程的方式
 */
public class TimerTask {
    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new java.util.TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }, 1000, 1000);
    }
}
