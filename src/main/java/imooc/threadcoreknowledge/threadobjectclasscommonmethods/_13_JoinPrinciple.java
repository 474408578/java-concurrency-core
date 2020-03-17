package imooc.threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * Join的原理：
 *      唤醒操作：每一个Thread类在run方法执行之后，会自动执行notify的唤醒操作。
 *
 * CountDownLatch
 * CyclicBarrier
 *
 */
public class _13_JoinPrinciple {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "执行完毕");
        });

        thread.start();
        System.out.println("开始等待子线程执行完毕");
//        thread.join();
        /**
         * 主线程进入到synchronized同步代码块，拿到thread这把锁，调用thread.wait后进入休眠状态
         * thread类运行完成后，会自动执行notify的唤醒操作，主线程跳出同步代码块，并且释放锁
         */
        synchronized (thread) {
            thread.wait();
        }
        System.out.println("所有线程执行完毕");

    }
}
