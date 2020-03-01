package imooc.threadcoreknowledge.stopthreads;

/**
 * 最佳实践：catch了InterruptedException之后的优先选择：
 * 在方法签名中抛出异常（传递中断），那么在run()就会强制try/catch
 * 不应该屏蔽中断
 */
public class RightWayStopThreadInProduction1 implements Runnable {

    // run无法抛出checked Exception，只能用try/catch
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("go");
            try {
                throwInMethod();
            } catch (InterruptedException e) {
//                将interrupted标记位
                Thread.currentThread().interrupt();

                System.out.println("保存日志");
                e.printStackTrace();
            }
        }
    }

    // 在编写方法时，优先选择在方法签名中抛出InterruptedException，而不是在方法中try/catch
    private void throwInMethod() throws InterruptedException {
        Thread.sleep(2000);
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new RightWayStopThreadInProduction1());
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();

    }
}
