package imooc.threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * 两个线程交替打印0-100的奇数、偶数
 * A 1
 * B 2
 * A 3
 * ……
 *
 * 用wait和notify实现
 */
public class _05_WaitNotifyPrintOddEveWait1 {
    public int num;

    public static void main(String[] args) throws InterruptedException {
        _05_WaitNotifyPrintOddEveWait1 r = new _05_WaitNotifyPrintOddEveWait1();
        Thread threadA = new Thread(() -> {
            r.printOdd();
        });

        Thread threadB = new Thread(() -> {
            r.printEve();
        });


        threadA.start();
        Thread.sleep(10);
        threadB.start();
    }

    public synchronized void printOdd() {
        for (int i = 0; i < 50; i++) {
            while (num % 2 != 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " : " + (++num));
            notify();
        }
    }

    public synchronized void printEve() {
        for (int i = 0; i < 50; i++) {
            while (num % 2 == 0){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " : " + (++num));
            notify();
        }
    }

}
