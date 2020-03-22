package imooc.threadcoreknowledge.uncaughtexception;

/**
 * 手动在run方法中进行try/catch
 */
public class CantCatchDirectlySolutionFunction1 implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        new Thread(new CantCatchDirectlySolutionFunction1(), "Thread-1").start();
        Thread.sleep(300);
        new Thread(new CantCatchDirectlySolutionFunction1(), "Thread-2").start();
        Thread.sleep(300);
        new Thread(new CantCatchDirectlySolutionFunction1(), "Thread-3").start();
        Thread.sleep(300);
        new Thread(new CantCatchDirectlySolutionFunction1(), "Thread-4").start();
    }
    @Override
    public void run() {
        try {
            throw new RuntimeException();
        } catch (RuntimeException e) {
            System.out.println("Caught Exception");
        }
    }
}
