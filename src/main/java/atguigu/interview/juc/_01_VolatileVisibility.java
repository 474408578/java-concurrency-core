package atguigu.interview.juc;

import java.util.concurrent.TimeUnit;

/**
 * volatile：给Java虚拟机提供的轻量级的同步机制
 *      1、保证可见性
 *      2、不保证原子性
 *      3、禁止指令重排
 *
 *  验证Volatile的可见性：
 *
 */
public class _01_VolatileVisibility {

    public static void main(String[] args) {
        MyData myData = new MyData();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " come in...");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.addTo60();
            System.out.println(Thread.currentThread().getName() + " updated number value to: " + myData.number);
        }, "AAA").start();
        while (myData.number == 0) {
            // 如果等于0，main线程就会一直子这个循环里面。
        }

        System.out.println(Thread.currentThread().getName() + " is over");
    }


}


class MyData {
//    int number = 0;
    volatile int number = 0;
    public void addTo60() {
        this.number = 60;
    }
}