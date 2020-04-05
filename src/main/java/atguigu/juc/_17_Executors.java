package atguigu.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 线程池：
 *      优势：线程池做的主要工作是控制运行的线程数量，处理过程中将任务放入队列，然后在线程创建后启动这些任务，
 *           如果线程数量超过了最大数量，超出数量的线程排队等候，等其他线程执行完毕，再从队列中取出任务来执行。
 *
 *      主要特点：线程复用；控制最大并发数；管理线程。
 *          1、降低资源消耗。通过重复利用已创建的线程降低线程创建和销毁造成的消耗。
 *          2、提高响应速度。当任务到达时，任务可以不需要等待线程创建就能立即执行。
 *          3、提高线程的可管理性。线程时稀缺资源，如果无限制的创建，不仅会消耗系统资源，还会降低系统的稳定性，
 *              使用线程池可以进行统一的分配，调优和监控。
 *
 * 父接口： Executor
 * 子接口：ExecutorService（我们主要使用的）
 * 线程池的工具类： Executors
 * 线程池的编码实现：
 *      Executors.newFixedThreadPool(int): 执行长期任务，性能好，创建一个线程池，一池有n个固定的线程，
 *      Executors.newSingleThreadExecutor(): 一个任务一个任务的执行，一池一线程
 *      Executors.newCachedThreadPool()：执行很多短期异步任务，线程池根据需要创建新的线程，
 *          但在先前构建的线程可用时将重用他们。可扩容，遇强则强。
 *
 * 线程池的几个重要参数：
 * ThreadPoolThread构造方法源码如下
 * ************************************************************************
 * public ThreadPoolExecutor(int corePoolSize,
 *                               int maximumPoolSize,
 *                               long keepAliveTime,
 *                               TimeUnit unit,
 *                               BlockingQueue<Runnable> workQueue,
 *                               ThreadFactory threadFactory,
 *                               RejectedExecutionHandler handler) {
 *         if (corePoolSize < 0 ||
 *             maximumPoolSize <= 0 ||
 *             maximumPoolSize < corePoolSize ||
 *             keepAliveTime < 0)
 *             throw new IllegalArgumentException();
 *         if (workQueue == null || threadFactory == null || handler == null)
 *             throw new NullPointerException();
 *         this.corePoolSize = corePoolSize;
 *         this.maximumPoolSize = maximumPoolSize;
 *         this.workQueue = workQueue;
 *         this.keepAliveTime = unit.toNanos(keepAliveTime);
 *         this.threadFactory = threadFactory;
 *         this.handler = handler;
 *     }
 * ****************************************************************************
 *      1、corePoolSize: 线程池中的常驻核心线程数。
 *      2、maximumPoolSize：线程池中能够容纳同时执行的最大线程数，此值必须大于等于1。
 *      3、keepAliveTime： 多余的空闲线程的存活时间，当前池中线程数量超过corePoolSize时，当空闲时间达到
 *                         keepAliveTime时，多余线程会被销毁，直到只剩下corePoolSize个线程为止。
 *      4、unit: keepAliveTime的单位。
 *      5、workQueue: 任务队列，被提交但尚未被执行的任务。
 *      6、threadFactory： 表示生成线程池中工作线程的线程工厂，用于创建线程，一般默认的即可。
 *      7、handler: 拒绝策略，表示当队列满了，并且工作线程大于等于线程池的最大线程数（maximumPoolSize）时，
 *                          如何来拒绝请求执行的Runnable的策略。
 *
 *
 * 线程池底层工作原理:
 *      1、在创建了线程池后，开始等待请求。
 *      2、当调用execute()方法添加一个请求任务时，线程池会做出如下判断：
 *          a、如果正在运行的线程数量小于corePoolSize, 那么马上创建线程运行这个任务；
 *          b、如果正在运行的线程数量等于或大于corePoolSize，那么将这个任务放入队列；
 *          c、如果这个时候队列满了且正在运行的线程数量还小于maximumPoolSize，那么还要创建非核心线程立刻运行这个任务；
 *          d、如果队列满了且正在运行的线程数量大于或等于maximumPoolSize时，那么线程池会启动饱和拒绝策略来执行。
 *     3、当一个线程完成任务时，它会从队列中取出下一个任务来执行。
 *     4、当一个线程无事可做，超过一定的时间（keepAliveTime）时，线程会判断：
 *          如果当前运行的线程数大于corePoolSize，那么这个线程会被停掉。
 *          所以线程池的所有任务完成后，它最终会收缩到corePoolSize的大小。
 *
 * 生产中如何设置合理参数：
 *      在工作中 单一的/固定数的/可变的 三种创建线程池的方法那个用的多？超级大坑
 *          答案：一个都不用，我们工作只能使用自定义的
 *          原因：
 *              阿里巴巴开发手册：
 *                  线程池不允许使用Executors去创建，而是通过ThreadPoolExecutor的方式去创建。
 *                  这样的处理方式让写的同学更加明确线程池的运行规则，规避了资源耗尽的风险。
 *                  Executors返回的线程池对象的弊端如下：
 *                      1、FixedThreadPool和SingleThreadPool：
 *                          允许的请求队列长度为Integer.MAX_VALUE, 可能会堆积大量的请求，导致oom
 *                      2、CacheThreadPool和ScheduledThreadPool：
 *                          允许创建的线程数量为Integer.MAX_VALUE, 可能会创建大量的线程，导致oom
 *
 */
public class _17_Executors {


    public static void main(String[] args) {
        /*
        // 一池5个工作线程，类似一个银行5个受理窗口
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        // 模拟10个顾客过来办理银行任务，目前池子里面有5个工作人员提供服务
        try {
            for (int i = 0; i < 10; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });
                TimeUnit.MILLISECONDS.sleep(400);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }*/

        /*
        // 一个工作线程，类似银1个银行只有一个受理窗口
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        try {
            for (int i = 0; i < 10; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }*/

        // 一池n线程，类似一个银行有n个受理窗口
        ExecutorService threadPool = Executors.newCachedThreadPool();
        try {
            for (int i = 0; i < 10; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });
                TimeUnit.MILLISECONDS.sleep(400);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }

    }
}