package com.xschen.feature.volatilecase;

/**
 * volatile 只能保证可见性，无法保证原子性
 * @author xschen
 */
public class VolatileInc {

    private static volatile int count = 0;

    public static void main(String[] args) throws InterruptedException {
        VolatileInc volatileInc = new VolatileInc();
        Thread t1 = new Thread(() -> volatileInc.add10k());
        Thread t2 = new Thread(() -> volatileInc.add10k());

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("最终的count=" + count);

    }

    public void add10k() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }

}
