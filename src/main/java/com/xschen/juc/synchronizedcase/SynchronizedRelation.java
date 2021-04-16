package com.xschen.juc.synchronizedcase;

import com.xschen.juc.threadpool.ThreadPoolBuilder;
import com.xschen.utils.ThreadDumpHelper;
import com.xschen.utils.ThreadUtil;

import java.util.Scanner;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 保护有关联关系的资源： 银行转账问题
 * @author xschen
 */
public class SynchronizedRelation {

    private static ThreadPoolExecutor threadPoolExecutor = ThreadPoolBuilder
            .fixedPool()
            .setPoolSize(2)
            .setThreadNamePrefix("sync-test")
            .build();

    /**
     * 线程不安全
     */
    static class UnsafeAccount {

        private int balance;

        public UnsafeAccount(int balance) {
            this.balance = balance;
        }

        /**
         * 转账
         * @param target
         * @param amt
         */
        public void transfer(UnsafeAccount target, int amt) {
            if (this.balance > amt) {
                this.balance -= amt;
                target.balance += amt;
            }
        }
    }


    /**
     * 通过传入锁的方式 但是必须保持lock对象跟SafeAccount 一对一绑定
     * 并且真实场景中创建账户对象的地方可能是分布式，所以会很复杂极不推荐！
     */
    static class SafeAccount {
        private int balance;
        /**
         * 需要转账的对象都持有同一把锁
         */
        private Object lock;

        public SafeAccount() {
        }

        public SafeAccount(Object lock) {
            this.lock = lock;
        }

        public void transfer(SafeAccount target, int amt) {
            synchronized (lock) {
                if (this.balance > amt) {
                    this.balance -= amt;
                    target.balance += amt;
                }
            }
        }
    }

    /**
     * 通过锁类对象方式
     * 同样能解决并发问题 但是会导致操作串行 性能极差故不推荐
     */
    class Account {
        private int balance;

        public void transfer(Account target, int amt) {
            synchronized(Account.class) {
                if (this.balance > amt) {
                    this.balance -= amt;
                    target.balance += amt;
                }
            }
        }
    }

    /**
     * 分别加锁方式  支持并行 锁粒度变细，性能提升
     * 但是 会导致严重问题：死锁！！
     */
    static class DeadLockAccount {

        private static ThreadDumpHelper dumpHelper = new ThreadDumpHelper(3 * 1000, 16);
        private int balance;

        public void transfer(DeadLockAccount target, int amt) {
            synchronized (this) {
                /**
                 * sleep 20 毫秒，使他陷入死锁
                 */
                ThreadUtil.sleepMillis(20);
                synchronized (target) {
                    if (this.balance > amt) {
                        this.balance -= amt;
                        target.balance += amt;
                        System.out.println("success");
                    }
                }
            }
        }

        public static void main(String[] args) {
            DeadLockAccount a = new DeadLockAccount();
            DeadLockAccount b = new DeadLockAccount();
            threadPoolExecutor.execute(() -> a.transfer(b, 100));
            threadPoolExecutor.execute(() -> b.transfer(a, 100));

            threadPoolExecutor.shutdown();

            while (true) {
                dumpHelper.tryThreadDump();
                ThreadUtil.sleep(10, TimeUnit.SECONDS);
            }
        }
    }
}
