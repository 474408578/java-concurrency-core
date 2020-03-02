package atguigu.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程之间按顺序调用，A->B->C
 * 三个线程启动，要求如下：
 *      AA打印5次
 *      BB打印10次
 *      CC打印15次
 *      接着
 *      AA打印5次
 *      BB打印10次
 *      CC打印15次
 *      ...
 *      共十轮
 *
 * 1、高内聚低耦合前提下，线程操作资源类
 * 2、判断/干活/通知
 * 3、多线程交互中，必须要防止多线程的虚假唤醒，也即（判断只用while、不能用if）
 * 4、注意标志位的修改和定位
 */

class ShareResource {
    //标志位 A：1, B:2, C:3
    private int flag = 1;
    private Lock lock = new ReentrantLock();
    // 钥匙
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();

    public void print5() {
        lock.lock();
        try {
            // 判断
            while (flag!=1){
                condition1.await();
            }
            // 干活
            for (int i=0; i<5; i++){
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            // 修改标志位、通知B
            flag = 2;
            condition2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print10() {
        lock.lock();
        try {
            // 判断
            while (flag!=2){
                condition2.await();
            }
            // 干活
            for (int i=0; i<10; i++){
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            // 修改标志位、通知C
            flag = 3;
            condition3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void print15() {
        lock.lock();
        try {
            // 判断
            while (flag!=3){
                condition3.await();
            }
            // 干活
            for (int i=0; i<15; i++){
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            // 修改标志位、通知
            flag = 1;
            condition1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}


public class _05_ThreadOrderAccess {

    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();

        new Thread(() -> {
            for (int i=0; i<10; i++){
                shareResource.print5();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i=0; i<10; i++){
                shareResource.print10();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i=0; i<10; i++){
                shareResource.print15();
            }
        }, "C").start();


    }
}
