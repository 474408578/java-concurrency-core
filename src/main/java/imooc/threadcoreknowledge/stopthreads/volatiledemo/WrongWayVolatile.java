package imooc.threadcoreknowledge.stopthreads.volatiledemo;

/**
 * 掩饰volatile的局限性：part1  看似可行
 * volatile保证原子的可见性
 */
public class WrongWayVolatile implements Runnable {
    private volatile boolean canceled = false;

    @Override
    public void run() {
        int num = 0;
        try {
            while (!canceled && num <= 100000) {
                if (num % 100 == 0) System.out.println(num + "是100的倍数");
                num++;
                Thread.sleep(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        WrongWayVolatile r = new WrongWayVolatile();
        Thread thread = new Thread(r);
        thread.start();
        Thread.sleep(5000);
        r.canceled = true;
    }
}
