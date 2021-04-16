package com.xschen.juc.synchronizedcase;

import java.util.ArrayList;
import java.util.List;

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
        private Allocator allocator;
        private int balance;
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

}
