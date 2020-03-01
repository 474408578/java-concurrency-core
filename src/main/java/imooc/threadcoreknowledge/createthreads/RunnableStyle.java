package imooc.threadcoreknowledge.createthreads;

/**
 * 用Runnable方式实现线程
 */
public class RunnableStyle implements Runnable {
    public void run() {
        System.out.println("用Runnable接口实现线程");
    }

    public static void main(String[] args) {
        new Thread(new RunnableStyle()).start();
    }
}
