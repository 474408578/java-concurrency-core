package imooc.synchronize;

/**
 * 消失的请求数，线程不安全
 */
public class _01_DisappearRequest implements Runnable {
    static  _01_DisappearRequest instance = new _01_DisappearRequest();
    static int i = 0;

    @Override
    public void run() {
        for (int j = 0; j < 100000; j++) {
            i++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        /**
         * 两个线程操作同一个实例
         */
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(i);
    }
}
