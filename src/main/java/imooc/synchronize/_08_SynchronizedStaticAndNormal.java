package imooc.synchronize;

/**
 * 同时访问静态的synchronized和非静态的synchronized方法
 */
public class _08_SynchronizedStaticAndNormal implements Runnable {
    static _08_SynchronizedStaticAndNormal instance = new _08_SynchronizedStaticAndNormal();
    @Override
    public void run() {
        if (Thread.currentThread().getName().equals("Thread-0")) {
            method1();
        } else {
            method2();
        }
    }

    public static synchronized void method1() {
        System.out.println("我是加锁的静态方法，我叫" + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + "运行结束");
    }

    public synchronized void method2() {
        System.out.println("我是非静态的加锁的方法，我叫" + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "运行结束");
    }

    public static void main(String[] args) {
        // 加的是不同的两把锁，一个是类锁的method1(), 所有的对象访问这个方法会有锁，
        // 另一个是对象锁的method2(), 只针对一个对象访问会有锁。由于是不同的两把锁，
        // 互不影响
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);

        t1.start();
        t2.start();
        while (t1.isAlive() || t2.isAlive()) {}
        System.out.println("Finished");
    }
}
