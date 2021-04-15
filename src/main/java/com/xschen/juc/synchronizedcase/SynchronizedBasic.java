package com.xschen.juc.synchronizedcase;

import java.util.ArrayList;
import java.util.List;

/**
 * Synchronized 可见性  原子性  有序性
 * @author xschen
 */
public class SynchronizedBasic {

    static class X {
        /**
         * 修饰非静态方法，锁对象为当前类的实例 this
         */
        synchronized void get() {

        }

        /**
         * 修饰静态方法，锁对象为当前类的 Class 对象
         */
        synchronized static void set() {

        }

        /**
         * 修饰代码块，锁对象为 obj
         */
        Object obj = new Object();
        void put() {
            synchronized (obj) {

            }
        }
    }


    /**
     * 利用 synchronized 实现原子操作
     */
    static class SafeCalc {
        long value = 0;
        synchronized long getValue() {
            return value;
        }

        synchronized void addOne() {
            value += 1;
        }
    }

    public static void main(String[] args) {
        SafeCalc safeCalc = new SafeCalc();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    safeCalc.addOne();
                }
            });
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(safeCalc.getValue());
    }
}
