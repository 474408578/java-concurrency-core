package imooc.threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * 交替打印0-100，使用synchronize的方式实现（效率比较低）
 */
public class _06_WaitNotifyPrintOddEvenSyn {
    private static int num;
    private static final Object lock = new Object();

    // 新建两个线程
    // 1个处理偶数，一个处理奇数（使用位运算）
    // 两个线程使用synchronized来进行通信
    public static void main(String[] args) {
        new Thread(() -> {
            while (num < 100) {
                synchronized (lock) {
                    if ((num & 1) == 1) {
                        System.out.println(Thread.currentThread().getName() + " : " + num++);
                    }
                }
            }
        },"奇数").start();

        new Thread(() -> {
            while (num < 100) {
                synchronized (lock) {
                    if ((num & 1) == 0) System.out.println(Thread.currentThread().getName() + " : " + num++);
                }
            }
        },"偶数").start();
    }

}