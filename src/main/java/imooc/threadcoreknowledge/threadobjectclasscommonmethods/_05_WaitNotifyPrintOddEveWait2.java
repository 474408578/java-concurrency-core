package imooc.threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * 交替打印0-100的奇偶数, 使用wait()/notify()进行实现
 * 效率更高
 */
public class _05_WaitNotifyPrintOddEveWait2 {

    private static int num;
    private static final Object object = new Object();

    public static void main(String[] args) {
        TurnRuner r = new TurnRuner();
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);

        t1.start();
        t2.start();

    }


    static class TurnRuner implements Runnable {
        @Override
        public void run() {
            while (num <= 100) {
                synchronized (object) {
                    System.out.println(Thread.currentThread().getName() + " : " + num++);
                    object.notify();
                    try {
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
