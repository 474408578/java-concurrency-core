package imooc.threadcoreknowledge.stopthreads;

/**
 * run在普通情况下如何停止线程
 * run方法内没有sleep或者wait方法时，停止线程
 */
public class RightWayStopThreadWithoutSleepAndWait implements Runnable {
    @Override
    public void run() {
        int num = 0;
        // 打印10000的倍数， 响应中断
        while(num < Integer.MAX_VALUE && !Thread.currentThread().isInterrupted()) {
            if (num % 10000 == 0){
                System.out.println(num + "是10000的倍数");
            }
            num++;
        }
        System.out.println("任务结束");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new RightWayStopThreadWithoutSleepAndWait());
        thread.start();

        Thread.sleep(1000);
        thread.interrupt();
    }
}
