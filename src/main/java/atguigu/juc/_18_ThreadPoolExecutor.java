package atguigu.juc;

import java.util.concurrent.*;

/**
 * 线程池的拒绝策略：等待队列已经排满了，再也塞不下新的任务了；同时线程池的maximumPoolSize也满了，无法为新任务服务。
 *      JDK内置的拒绝策略：
 *          1、AbortPolicy()[默认策略]: 直接抛出RejectedExecutionException，阻止系统正常运行。
 *          2、CallerRunsPolicy(): “调用者运行”调节机制， 该策略既不会抛弃任务，也不会抛出异常，
 *              而是将某些任务回退到调用者，从而降低新任务的流量。
 *          3、DiscardOldestPolicy(): 抛弃队列中等待最久的任务，然后把当前任务加入队列中，尝试再次提交当前任务
 *          4、DiscardPolicy()：该策略默默地丢弃无法处理的任务，不予任何处理也不抛出异常，如果允许任务丢失，这是最好的一种策略。
 *
 *
 * 创建多少线程合适（maximumPoolSize）
 *      CPU密集型: 线程池中maximumPoolSize比核数多1至2
 *      IO密集型: 1+(IO耗时)/(CPU耗时)
 */
public class _18_ThreadPoolExecutor {
    public static void main(String[] args) {

        System.out.println("处理器核数" + Runtime.getRuntime().availableProcessors());
        ExecutorService threadPool = new ThreadPoolExecutor(
                2,
                5,
                2L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy());
        // 模拟10个顾客过来办理银行任务，目前池子里面有5个工作人员提供服务
        try {
            // 最多能够承载8个任务： maximumPoolSize + queueCapacity
            for (int i = 0; i < 20; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
