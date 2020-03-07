package imooc.synchronize;

/**
 * 同时访问同一个对象的不同的普通同步方法
 */
public class _07_SynchronizedDifferentMethod implements Runnable {
    static _07_SynchronizedDifferentMethod instance = new _07_SynchronizedDifferentMethod();
    @Override
    public void run() {
        if (Thread.currentThread().getName().equals("Thread-0")) {
            method1();
        } else {
            method2();
        }
    }

    public synchronized void method1() {
        System.out.println("我是加锁的方法，我叫" + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + "运行结束");
    }

    public synchronized void method2() {
        System.out.println("我是加锁的方法，我叫" + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "运行结束");
    }

    public static void main(String[] args) {
        // 加的同一把锁 this
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);

        t1.start();
        t2.start();
        while (t1.isAlive() || t2.isAlive()) {}
        System.out.println("Finished");
    }


}
