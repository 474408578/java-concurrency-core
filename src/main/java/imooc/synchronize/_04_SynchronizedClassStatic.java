package imooc.synchronize;

/**
 * 类锁示例1：synchronized 加在static上
 */
public class _04_SynchronizedClassStatic implements Runnable {
    static _04_SynchronizedClassStatic instance1 = new _04_SynchronizedClassStatic();
    static _04_SynchronizedClassStatic instance2 = new _04_SynchronizedClassStatic();
    @Override
    public void run() {
        method();
    }

    public static synchronized void method() {
        System.out.println("我是类锁的第一种形式：static形式。我叫" + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "运行结束");
    }

    // 类锁
    public static void main(String[] args) {
        Thread t1 = new Thread(instance1);
        Thread t2 = new Thread(instance2);

        t1.start();
        t2.start();

        while (t1.isAlive() || t2.isAlive()) {}

        System.out.println("Finished");
    }
}
