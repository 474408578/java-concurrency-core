package imooc.threadcoreknowledge.stopthreads;

/**
 * 停止线程的相关重要函数解析
 * interrupt()方法: 将调用该方法的对象所表示的线程标记一个停止标记，并不是真的停止该线程。(停止标志，并不会唤醒)
 *
 * 判断是否已被中断的相关方法
 *      static boolean interrupted()：获取当前线程的中断状态，并且会清除线程的中断状态标记。
 *          官网解释：
 *              Tests whether the current thread has been interrupted.
 *              The interrupted status of the thread is cleared by this method.
 *      boolean isInterrupted()：获取调用该方法的对象所表示的线程，不会清除线程的状态标记。
 *      Thread.interrupted的目的对象
 *
 *  描述：注意interrupted操作的目标对象是"当前线程"，而不管本方法来自于哪个对象
 */

public class InterruptedDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread threadOne = new Thread(() -> {
            for (;;);
        });

        threadOne.start();
        // 设置中断标志
        threadOne.interrupt();
        // 获取threadOne中断标志 true
        System.out.println("isInterrupted: " + threadOne.isInterrupted());
        // 获取当前线程的中断标志并重置
        System.out.println("isInterrupted: " + threadOne.interrupted());
        // 获取中断标志并重置
        System.out.println("isInterrupted: " + Thread.interrupted());
        // 获取中断标志
        System.out.println("isInterrupted: " + threadOne.isInterrupted());
        System.out.println("isInterrupted: " + Thread.interrupted());
        System.out.println("isInterrupted: " + Thread.interrupted());
        threadOne.join();
        System.out.println("Main thread is over");
    }
}
/**
 * true
 * false
 * false
 * true
 * false
 */