package atguigu.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 题目： 三个售票员 卖出 30张票
 *
 * 多线程编程的企业级套路 + 模板
 *
 * 1、在高内聚低耦合的前提下， 线程    操作(对外暴露的调用方法)    资源类
 */

/**
 * 知识点
 * 1、匿名内部类
 * 2、lambda表达式
 * 3、synchronized关键字
 */


// 资源类
class Ticket {
    private int number = 30;
    private Lock lock = new ReentrantLock();



    // synchronized能够保证同一时刻最多只有一个线程执行该段代码，以达到保证并发安全的效果，内部锁
//    public synchronized void saleTicket() {
//        if (number > 0){
//            System.out.println(Thread.currentThread().getName() + "\t卖出第：" + (number--) + "\t还剩下：" + number);
//        }
//
//    }


    // 使用lock，显式锁
    public void saleTicket() {
        // 加锁
        lock.lock();
        try {
            if (number > 0){
                System.out.println(Thread.currentThread().getName() + "\t卖出第：" + (number--) + "\t还剩下：" + number);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            // 释放锁
            lock.unlock();
        }
    }
}


public class _01_SaleTicket {

    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(() -> {for (int i=0; i<=40; i++){ ticket.saleTicket();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }},"A").start();
        new Thread(() -> {for (int i=0; i<=40; i++) ticket.saleTicket();},"B").start();
        new Thread(() -> {for (int i=0; i<=40; i++) ticket.saleTicket();},"C").start();
        // public Thread(Runnable target, String name)

        /*
        // 匿名内部类
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 400 ; i++) {
                    ticket.saleTicket();
                }
            }
        }, "A").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 400 ; i++) {
                    ticket.saleTicket();
                }
            }
        }, "B").start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 400 ; i++) {
                    ticket.saleTicket();
                }
            }
        }, "C").start();
         */
    }
}
