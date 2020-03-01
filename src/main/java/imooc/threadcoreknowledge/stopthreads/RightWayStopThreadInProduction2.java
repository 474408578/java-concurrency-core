package imooc.threadcoreknowledge.stopthreads;

/**
 * 最佳实践2：在catch子语句中调用Thread.currentThread().interrupt()来恢复设置中断状态，
 * 以便于在后续的执行中，依然能够回到刚才的RightWayStopThreadProduction1补上中断，让它跳出
 */
public class RightWayStopThreadInProduction2 implements Runnable{
    @Override
    public void run() {
        while (true) {
            System.out.println("go");
            if (Thread.currentThread().isInterrupted()){
                System.out.println("Interrupted, 程序运行结束");
                break;
            }
            reInterrupt();
        }
    }

    // 当不想或者无法传递中断时，恢复中断
    private void reInterrupt() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new RightWayStopThreadInProduction2());
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
    }
}
