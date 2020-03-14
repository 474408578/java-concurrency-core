package imooc.threadcoreknowledge.sixstates;

/**
 * 展示Blocked, Wait, TimedWaiting
 */
public class BlockedWaitingTimedWaiting implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        BlockedWaitingTimedWaiting r = new BlockedWaitingTimedWaiting();
//        BlockedWaitingTimedWaiting thread2 = new BlockedWaitingTimedWaiting();
        Thread thread1 = new Thread(r);
        Thread thread2 = new Thread(r);
        thread1.start();
        Thread.sleep(4);
        thread2.start();
        // 打印出TimeWait，因为正在执行Thread.sleep()
        System.out.println("线程1" + thread1.getState());

        // 拿不到锁
        System.out.println("线程2"  + "  " + thread2.getState());

        Thread.sleep(2000);
        System.out.println("线程" + thread1.getState());



    }


    @Override
    public void run() {
        try {
            syn();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void syn() throws InterruptedException {
        Thread.sleep(1000);
        wait();
    }
}
