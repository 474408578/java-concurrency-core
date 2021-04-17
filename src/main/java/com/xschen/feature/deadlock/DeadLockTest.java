package com.xschen.feature.deadlock;

import com.xschen.juc.threadpool.ThreadPoolBuilder;
import com.xschen.utils.ThreadDumpHelper;
import com.xschen.utils.ThreadUtil;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 死锁演示
 * @author xschen
 */
public class DeadLockTest {

    private static final ThreadPoolExecutor threadPool =
            ThreadPoolBuilder.fixedPool().setPoolSize(2).setThreadNamePrefix("dead-lock-test").build();
    private static final ThreadDumpHelper dumpHelper = new ThreadDumpHelper();

    /**
     * 事实上 String 对象不建议当成锁的对象 （常量池的存在会导致锁重复）
     * 这里故意将 A、B 放入堆中以示问题。
     */
    private static final String A = new String("A");
    private static final String B = new String("B");

    public static void main(String[] args) {
        DeadLockTest deadLockTest = new DeadLockTest();
        deadLockTest.deadLock();
    }

    private void deadLock() {
        threadPool.execute(() -> {
            synchronized (A) {
                ThreadUtil.sleep(1, TimeUnit.SECONDS); // sleep 不会释放锁
                synchronized (B) {
                    System.out.println("A");
                }
            }
        });

        threadPool.execute(() -> {
            synchronized (B) {
                synchronized (A) {
                    System.out.println("B");
                }
            }
        });

        threadPool.shutdown();
        ThreadUtil.sleepMillis(2);
        dumpHelper.tryThreadDump();
    }
}
