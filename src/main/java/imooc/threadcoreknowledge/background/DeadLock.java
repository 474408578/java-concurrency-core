package imooc.threadcoreknowledge.background;

/**
 * 演示死锁
 *  两个线程
 *  两把锁
 *  线程A需要拿线程B持有的锁
 *  线程B需要拿线程A持有的锁
 */
public class DeadLock implements Runnable {
    int flag = 1;
    static Object object1 = new Object();
    static Object object2 = new Object();

    @Override
    public void run() {
        System.out.println("flag = " + flag);
        if (flag == 1){
            synchronized (object1) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (object2) {
                    System.out.println("1");
                }
            }
        }

        if (flag == 0) {
            synchronized (object2) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (object1) {
                    System.out.println("0");
                }
            }
        }
    }

    public static void main(String[] args) {
        DeadLock r1 = new DeadLock();
        DeadLock r2 = new DeadLock();

        r1.flag = 1;
        r2.flag = 0;

        new Thread(r1).start();
        new Thread(r2).start();
    }
}
