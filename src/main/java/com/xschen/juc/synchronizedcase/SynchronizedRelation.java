package com.xschen.juc.synchronizedcase;

import com.xschen.juc.threadpool.ThreadPoolBuilder;

import java.util.concurrent.ThreadPoolExecutor;

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
     * 直接对转账方法做同步方式，
     * 锁对象为this， 而target对象资源不受保护
     */
    static class SynchronizedTransferAccount {
        /**
         * 用于测试并发
         */
        private static volatile boolean flag = true;

        private void breakLoop() {
            this.flag = false;
        }

        private int balance;

        public SynchronizedTransferAccount(int balance) {
            this.balance = balance;
        }

        synchronized void transfer(SynchronizedTransferAccount target, int amount) {
            if (this.balance > amount) {
                while (flag) {}

                System.out.println(Thread.currentThread().getName() + "this amount: " +
                        this.balance + " target amount: " + target + amount);

                this.balance -= amount;
                target.balance += amount;

                System.out.println(Thread.currentThread().getName() + "this amount: " +
                        this.balance + " target amount: " + target + amount);
            }
        }

        public static void main(String[] args) {
            SynchronizedTransferAccount a = new SynchronizedTransferAccount(200);
            SynchronizedTransferAccount b = new SynchronizedTransferAccount(200);
            SynchronizedTransferAccount c = new SynchronizedTransferAccount(200);


        }
    }

}
