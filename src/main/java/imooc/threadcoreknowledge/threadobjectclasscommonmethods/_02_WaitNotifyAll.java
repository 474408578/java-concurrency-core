package imooc.threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * 三个线程
 * 线程1和线程2首先被阻塞，线程3唤醒他们。
 * notify，notifyAll，start先执行并不代表线程先启动
 */
public class _02_WaitNotifyAll implements Runnable {
    private static final Object resource = new Object();


    public static void main(String[] args) throws InterruptedException {
        _02_WaitNotifyAll r = new _02_WaitNotifyAll();
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);

        t1.start();
        t2.start();

        Thread.sleep(200);

        new Thread(() -> {
            synchronized (resource) {
                resource.notifyAll();
//                resource.notify();
                System.out.println(Thread.currentThread().getName() + " notified");
            }
        }).start();
    }

    @Override
    public void run() {
        synchronized (resource) {
            System.out.println(Thread.currentThread().getName() + " got resource lock");
            try {
                System.out.println(Thread.currentThread().getName() + " waits to start");
                resource.wait();
                System.out.println(Thread.currentThread().getName() + " waits to end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
