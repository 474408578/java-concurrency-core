package imooc.threadcoreknowledge.background;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 第一种运行结果出错
 * 演示计数不准确，找出具体出错的位置
 */
public class MultiThreadError implements Runnable {
    int index;
    static MultiThreadError instance = new MultiThreadError();
    static AtomicInteger realIndex = new AtomicInteger();
    static AtomicInteger wrongCount = new AtomicInteger();
    static volatile CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
    static volatile CyclicBarrier cyclicBarrier1 = new CyclicBarrier(2);

    final boolean[] marked = new boolean[1000000];

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(instance);
        Thread thread2 = new Thread(instance);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("表面结果是" + instance.index);
        System.out.println("真正运行的次数" + realIndex.get());
        System.out.println("错误次数" + wrongCount.get());

    }
    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            try {
                cyclicBarrier1.reset();
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            index++;
            try {
                cyclicBarrier.reset();
                cyclicBarrier1.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            realIndex.incrementAndGet();
            synchronized (instance) {
                if (marked[index] && marked[index - 1]) {
                    System.out.println("发生了错误 " + index);
                    wrongCount.incrementAndGet();
                }
                marked[index] = true;
            }
        }
    }
}
