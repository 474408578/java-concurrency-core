package imooc.synchronize;

/**
 * 对象锁示例1：代码块形式
 */
public class _02_SynchronizedObjectCodeBlock implements Runnable {

    static _02_SynchronizedObjectCodeBlock instance = new _02_SynchronizedObjectCodeBlock();
//    static _02_SynchronizeObjectCodeBlock instance2 = new _02_SynchronizeObjectCodeBlock();

    // 充当锁对象
    Object lock1 = new Object();
    Object lock2 = new Object();


    @Override
    public void run() {
        /**
         * 两个同步代码块，并且使用不同的锁
         */
        synchronized (lock1){
            System.out.println("我是lock1，我叫" + Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " lock1运行完成");
        }

        synchronized (lock2){
                    System.out.println("我是lock2，我叫" + Thread.currentThread().getName());
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "lock2运行完成");
                }

    }

    public static void main(String[] args) {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);

        t1.start();
        t2.start();
        /**
         * t1拿到lock1
         * t1释放lock1，
         * t1拿到lock2, t2拿到lock1
         * t1释放lock2, t2释放lock1
         * t2拿到lock2
         * t2释放lock2
         */
        while (t1.isAlive() || t2.isAlive()) { }
        System.out.println("finished");
    }
}
