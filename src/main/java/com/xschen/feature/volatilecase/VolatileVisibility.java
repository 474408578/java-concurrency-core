package com.xschen.feature.volatilecase;

import com.xschen.utils.SmallTool;

/**
 * volatile 保证可见性
 * @author xschen
 */
public class VolatileVisibility {

    private volatile static boolean flag = true;

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            while (flag) {

            }
            System.out.println(Thread.currentThread().getName() + "stopped");
        });

        SmallTool.sleepMillis(1000);
        flag = false;
    }
}
