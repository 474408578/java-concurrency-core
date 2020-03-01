package imooc.threadcoreknowledge.stopthreads;

/**
 * 带有sleep的中断线程的方法, 线程处在sleep状态下，被中断
 */
public class RightWayStopThreadWithSleep {

    public static void main(String[] args) throws InterruptedException {
        /**
         * 在线程sleep期间，被中断
         */
        Runnable runnable = () -> {
            int num = 0;
            while(num<=300 && !Thread.currentThread().isInterrupted()){
                if (num % 100 == 0) {
                    System.out.println(num + " 是100的倍数");
                }
                num++;
            }
            try {
                // 被中断时，会抛出异常
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

        Thread.sleep(500);
        // 中断
        thread.interrupt();
    }
}
