package atguigu.juc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore信号量：常用于限制可以访问某些资源的线程数量
 * 信号量主要有两个作用：
 *      1、用于多个共享资源的互斥使用
 *      2、用于并发线程数的控制
 *
 * 在信号量上我们定义两种条件：
 *      acquire(获取)：
 *          当一个线程调用acquire操作时，它要么通过成功获取信号量（信号量减1）；
 *          要么一直等待下去，直到有线程释放信号量，或超时。
 *
 *      release（释放）：
 *          实际上会将信号量的值加1，然后唤醒等待的线程
 */
public class _13_SemaphoreDemo {
    public static void main(String[] args) {
        // 信号量，只允许3个线程同时访问
        Semaphore semaphore = new Semaphore(3); // 模拟资源类，有三个空车位
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                    // 获得许可
                    semaphore.acquire();
                    // 执行
                    System.out.println(Thread.currentThread().getName() + "\t抢占到了车位");
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "\t离开了车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // 释放
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }
    }

}
