package imooc.threadcoreknowledge.threadobjectclasscommonmethods;


/**
 * 展示线程sleep的时候不释放synchronized的monitor，等sleep时间到了以后，正常结束后才释放锁
 */
public class _08_SleepDontReleaseMonitor implements Runnable {
    public static void main(String[] args) {
        _08_SleepDontReleaseMonitor r = new _08_SleepDontReleaseMonitor();
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);

        t1.start();
        t2.start();
    }

    @Override
    public void run() {
        syn();
    }

    public synchronized void syn() {
        System.out.println(Thread.currentThread().getName() + "已经获取到了锁");
        try {
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getName() + "已经释放了锁");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
