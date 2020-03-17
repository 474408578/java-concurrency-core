package imooc.threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * 演示join期间被中断的效果（中断的是主线程）
 */
public class _11_JoinIterrupt {
    public static void main(String[] args) {
        Thread mainThread = Thread.currentThread();
        Thread thread = new Thread(() -> {
//           子线程对主线程进行中断
            mainThread.interrupt();
            try {
                Thread.sleep(5000);
                System.out.println("thread finished");
            } catch (InterruptedException e) {
                System.out.println("子线程被中断");
            }
        });

        thread.start();

        System.out.println("等待子线程运行完毕");
        try {
            thread.join();
        } catch (InterruptedException e) {
            // 主线程被中断，主线程抛出异常
            System.out.println(Thread.currentThread().getName() + "被中断了");
//            传递中断
//            thread.interrupt();
        }

        System.out.println("子线程已运行完毕");
    }
}
