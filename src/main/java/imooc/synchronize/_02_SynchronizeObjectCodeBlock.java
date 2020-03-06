package imooc.synchronize;

/**
 * 对象锁示例1：代码块形式
 */
public class _02_SynchronizeObjectCodeBlock implements Runnable {

    static _02_SynchronizeObjectCodeBlock instance = new _02_SynchronizeObjectCodeBlock();
//    static _02_SynchronizeObjectCodeBlock instance2 = new _02_SynchronizeObjectCodeBlock();

    // 
    Object lock = new Object();


    @Override
    public void run() {

        synchronized (lock){
            System.out.println("我是对象锁的代码块形式，我叫" + Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "运行完成");
        }

    }

    public static void main(String[] args) {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);

        t1.start();
        t2.start();

        while (t1.isAlive() || t2.isAlive()) { }
        System.out.println("finished");
    }
}
