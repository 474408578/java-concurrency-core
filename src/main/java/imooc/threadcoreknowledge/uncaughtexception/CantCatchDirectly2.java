package imooc.threadcoreknowledge.uncaughtexception;

/**
 * 加了try/catch，期望捕获到第一个线程的异常，线程234不应该运行，希望看到打印出Caught Exception
 *
 * try/catch只能捕获对应线程内的异常
 * 子线程发生的异常，无法在主线程中进行捕获
 */
public class CantCatchDirectly2 implements Runnable {
    public static void main(String[] args) {
        try {
            new Thread(new CantCatchDirectly2(), "Thread-1").start();
            Thread.sleep(300);
            new Thread(new CantCatchDirectly2(), "Thread-2").start();
            Thread.sleep(300);
            new Thread(new CantCatchDirectly2(), "Thread-3").start();
            Thread.sleep(300);
            new Thread(new CantCatchDirectly2(), "Thread-4").start();
        } catch (InterruptedException e) {
            System.out.println("Caught Exception");
        }
    }

    @Override
    public void run() {
        throw new RuntimeException();
    }
}
