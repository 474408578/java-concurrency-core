package imooc.juc.cas;

/**
 * 模拟CAS操作，等价代码
 */
public class TwoThreadsCompetition implements Runnable {
    private volatile int value;
    public synchronized int compareAndSwap(int expectValue, int newValue) {
        int oldValue = value;
        if (oldValue == expectValue) {
            value = newValue;
        }
        return value;
    }

    public static void main(String[] args) throws InterruptedException {
        TwoThreadsCompetition r = new TwoThreadsCompetition();
        r.value = 0;
        Thread t1 = new Thread(r, "thread-1");
        Thread t2 = new Thread(r, "thread-2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(r.value);
    }

    @Override
    public void run() {
        compareAndSwap(0, 1);
    }
}
