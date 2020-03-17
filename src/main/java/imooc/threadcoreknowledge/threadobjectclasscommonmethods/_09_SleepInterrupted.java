package imooc.threadcoreknowledge.threadobjectclasscommonmethods;

import java.sql.Time;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 每隔1秒钟输出当前时间，被中断，观察
 *
 * 两种写法 ：
 *      Thread.sleep()
 *      TimeUnit.SECONDS.sleep()
 */
public class _09_SleepInterrupted implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        _09_SleepInterrupted sleepInterrupted = new _09_SleepInterrupted();
        Thread t1 = new Thread(sleepInterrupted);
        t1.start();
        Thread.sleep(5000);
        t1.interrupt();
    }
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(new Date());
            try {
                /*
                TimeUnit.HOURS.sleep(3);
                TimeUnit.MINUTES.sleep(3);
                */
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("我被中断了！");
            }
        }
    }
}
