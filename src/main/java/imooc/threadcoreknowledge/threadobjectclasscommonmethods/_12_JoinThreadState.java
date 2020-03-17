package imooc.threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * 在一个线程中执行另一个线程的join方法，此时这个线程的状态是waiting
 */
public class _12_JoinThreadState {
    public static void main(String[] args) throws InterruptedException {
        Thread mainThread = Thread.currentThread();
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(3000);
                System.out.println(mainThread.getState());
                System.out.println(Thread.currentThread().getName() + "运行结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread.start();
        System.out.println("等待子线程运行完毕");
        thread.join();
        System.out.println("子线程运行完毕");
    }
}
