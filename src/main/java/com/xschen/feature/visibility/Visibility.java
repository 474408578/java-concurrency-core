package com.xschen.feature.visibility;


import com.xschen.juc.threadpool.ThreadPoolBuilder;
import com.xschen.utils.ThreadUtil;

import java.util.concurrent.ThreadPoolExecutor;


/**
 * 可见性问题
 * @author xschen
 */
public class Visibility {

    private static Long count = 0L;
    private static Boolean flag = true;
    private static ThreadPoolExecutor threadPoolExecutor = ThreadPoolBuilder.fixedPool().setPoolSize(2).build();

    public static void main(String[] args) throws InterruptedException {
        //System.out.println(calc());

        /**
         * 通过 Boolean 更加直观
         */
        new Thread(() -> {
            while (flag) {

            }
            System.out.println("stop");
        }).start();


        ThreadUtil.sleepMillis(1000L);
        flag = false;
    }

    public static long calc() throws InterruptedException {
        final Visibility visibility = new Visibility();
        Thread t1 = new Thread(() -> visibility.add10k());
        Thread t2 = new Thread(() -> visibility.add10k());
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        return count;
    }

    public void add10k() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }
}
