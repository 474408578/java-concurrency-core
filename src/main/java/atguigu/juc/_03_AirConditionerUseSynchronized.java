package atguigu.juc;

/**
 * 线程间通信
 * <p>
 * 题目：现在两个线程，可以操作初始值为零的一个变量，
 * 实现一个线程对该变量加1，一个线程对该变量减1，
 * 实现交替，共10轮，变量初始值为0
 * <p>
 * <p>
 * <p>
 * 1、高内聚低耦合前提下，线程操作资源类
 * 2、判断/干活/通知
 * 3、多线程交互中，必须要防止多线程的虚假唤醒，也即（判断只用while、不能用if）
 * <p>
 * 知识点
 * wait()、notifyAll()是object类的方法
 * wait(): 使执行线程被暂停
 * notifyAll()：唤醒线程
 */

class AirConditioner {
    private int num = 0;

    public synchronized void increment() throws InterruptedException {
        /**
         * 判断
         * 为了防止虚假唤醒，使用while，而不是if
         * 因为使用if时，当线程被唤醒时，不会再去检查条件是否成立
         */
        while (num != 0) {
            this.wait();
        }

        num++;
        System.out.println(Thread.currentThread().getName() + "\t" + num);
        this.notifyAll();
    }


    public synchronized void decrement() throws InterruptedException {
        while (num == 0) {
            this.wait();
        }

        num--;
        System.out.println(Thread.currentThread().getName() + "\t" + num);
        this.notifyAll();
    }
}

public class _03_AirConditionerUseSynchronized {
    public static void main(String[] args) {
        AirConditioner airConditioner = new AirConditioner();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    airConditioner.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();


        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    airConditioner.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    airConditioner.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    airConditioner.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "D").start();
    }
}


