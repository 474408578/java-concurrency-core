package com.xschen.juc.synchronizedcase;

import com.xschen.juc.threadpool.ThreadPoolBuilder;
import com.xschen.utils.ThreadDumpHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 解决死锁问题
 * @author xschen
 */

public class SynchronizedResolveDeadLock {

    /**
     * 破坏占有且等待条件
     * 加入分配类， 只有在 同时拿到两个锁的时候才可以操作
     */
    static class Allocator {

        private List<Object> als = new ArrayList<>();

        /**
         * 一次性申请所有资源
         * @param from
         * @param to
         * @return
         */
        synchronized boolean apply(Object from, Object to) {
            if (als.contains(from) || als.contains(to)) {
                return false;
            } else {
                als.add(from);
                als.add(to);
            }
            return true;
        }

        /**
         * 归还资源
         * @param from
         * @param to
         */
        synchronized void free(Object from, Object to) {
            als.remove(from);
            als.remove(to);
        }
    }

    class Account {
        /**
         * allocator 应该为单例
         */
        private Allocator allocator;
        private int balance;

        /**
         * 循环等待消耗CPU，不是最佳实现
         * @param target
         * @param amt
         */
        void transfer(Account target, int amt) {
            while (!allocator.apply(this, target)) {

            }

            try {
                synchronized (this) {
                    synchronized (target) {
                        if (this.balance >= amt) {
                            this.balance -= amt;
                            target.balance += amt;
                        }
                    }
                }
            } finally {
                allocator.free(this, target);
            }
        }
    }

    /**
     * 利用等待通知的方式实现
     */
    static class WaitAndNotifyAllocator {
        private List<Object> als = new ArrayList<>();

        private WaitAndNotifyAllocator() {}

        synchronized boolean apply(Object from, Object to) {
            while (als.contains(from) || als.contains(to)) { // 条件曾经满足过，需要循环判断
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            als.add(from);
            als.add(to);
            return true;
        }

        synchronized void free(Object from, Object to) {
            als.remove(from);
            als.remove(to);
            notifyAll();
        }

        static class WaitAndNotifyAllocatorHolder {
            private static WaitAndNotifyAllocator instance = new WaitAndNotifyAllocator();
        }

        /**
         * WaitAndNotifyAllocator 为单例
         * @return
         */
        public static WaitAndNotifyAllocator getInstance() {
            return WaitAndNotifyAllocatorHolder.instance;
        }
    }

    class WaitAndNotifyAccount {
        // allocator 应该为单例
        private WaitAndNotifyAllocator allocator;
        private int balance;
        void transfer(WaitAndNotifyAccount target, int amt) {
            WaitAndNotifyAllocator.getInstance().apply(this, target);
            this.balance -= amt;
            target.balance += amt;
            WaitAndNotifyAllocator.getInstance().free(this, target);
        }
    }

    /**
     * 破坏循环等待条件，
     */
    static class SortAccount {
        private static ThreadPoolExecutor threadPoolExecutor =
                ThreadPoolBuilder.fixedPool().setPoolSize(2).setThreadNamePrefix("sortAccount").build();
        private static ThreadDumpHelper dumpHelper = new ThreadDumpHelper();
        private int balance;
        private int id; // 作为排序字段

        public SortAccount(int balance, int id) {
            this.balance = balance;
            this.id = id;
        }

        synchronized void transfer(SortAccount target, int amt) {
            SortAccount left = this;
            SortAccount right = target;
            if (this.id > target.id) {
                left = target;
                right = this;
            }
            synchronized (left) { // 锁定账号小的账户

                synchronized (right) { // 锁定账号大的账户
                    if (this.balance > amt) {
                        this.balance -= amt;
                        target.balance += amt;
                    }
                }
            }
        }

        public static void main(String[] args) {
            SortAccount a = new SortAccount(200, 1);
            SortAccount b = new SortAccount(200, 1);
            threadPoolExecutor.execute(() -> a.transfer(b, 100));
            threadPoolExecutor.execute(() -> b.transfer(a, 100));
            threadPoolExecutor.shutdown();
            System.out.println(a.balance);
            System.out.println(b.balance);
            dumpHelper.tryThreadDump();
        }
    }

}
