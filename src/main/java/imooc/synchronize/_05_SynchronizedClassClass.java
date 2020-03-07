package imooc.synchronize;

public class _05_SynchronizedClassClass implements Runnable {
    static _05_SynchronizedClassClass instance1 = new _05_SynchronizedClassClass();
    static _05_SynchronizedClassClass instance2 = new _05_SynchronizedClassClass();

    @Override
    public void run() {
        method();
    }

    private void method() {
        //className.class
        synchronized (_05_SynchronizedClassClass.class) {
            System.out.println("我是类锁的第二种形式，我叫" + Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "执行完毕，运行结束");
        }
    }


    public static void main(String[] args) {
        // 两个对象哪的是同一把锁
        Thread t1 = new Thread(instance1);
        Thread t2 = new Thread(instance2);

        t1.start();
        t2.start();

        while (t1.isAlive() || t2.isAlive());
        System.out.println("Finished");
    }
}
