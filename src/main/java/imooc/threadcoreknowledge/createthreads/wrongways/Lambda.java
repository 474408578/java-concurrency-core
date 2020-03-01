package imooc.threadcoreknowledge.createthreads.wrongways;

/**
 * Lambda创建线程的方式
 */
public class Lambda {
    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
        }).start();
    }
}
