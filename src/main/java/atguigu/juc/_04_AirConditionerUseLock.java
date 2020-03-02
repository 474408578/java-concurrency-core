package atguigu.juc;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * synchronize对应object中的wait(), notifyAll()
 * lock对应locks.Condition中的await(), signAll()
 */

class AirConditioner2 {
    private int num = 0;
    private Lock lock = new ReentrantLock();
    // 锁的钥匙
    private Condition condition = lock.newCondition();

    public void increment()  {
        /**
         * 判断
         * 为了防止虚假唤醒，使用while，而不是if
         * 因为使用if时，当线程被唤醒时，不会再去检查条件是否成立
         */
        lock.lock();
        try {
            while (num!= 0) {
                condition.await();
            }

            num++;
            System.out.println(Thread.currentThread().getName() + "\t" + num);
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decrement() {
        /**
         * 判断
         * 为了防止虚假唤醒，使用while，而不是if
         * 因为使用if时，当线程被唤醒时，不会再去检查条件是否成立
         */
        lock.lock();
        try {
            while (num==0) {
                condition.await();
            }

            num--;
            System.out.println(Thread.currentThread().getName() + "\t" + num);
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


}
public class _04_AirConditionerUseLock {
    public static void main(String[] args) {
        AirConditioner2 airConditioner = new AirConditioner2();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                    airConditioner.increment();

            }
        }, "A").start();


        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                    airConditioner.decrement();
            }
        }, "B").start();

    }
}

