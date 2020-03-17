package imooc.threadcoreknowledge.threadobjectclasscommonmethods;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * sleep方法可以让线程进入waiting状态，并且不占用CPU资源，但是不释放锁，
 * 直到规定时间后在执行，休眠期间如果被中断，会抛出异常并且清除中断状态。
 *
 * 演示Sleep不释放锁, synchronized 还是lock（Lock需要手动释放）
 */
public class _07_SleepDontReleaseLock implements Runnable {
    private final static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        _07_SleepDontReleaseLock r = new _07_SleepDontReleaseLock();
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);

        t1.start();
        t2.start();
    }

    @Override
    public void run() {
        lock.lock();
        System.out.println(Thread.currentThread().getName() + "获取到了锁");
        try {
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getName() + "已经苏醒");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
