package com.xschen.juc.threadlocal;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 安全的 SimpleDateFormat
 * @author xschen
 */

public class SafeDateFormat {

    private static final ThreadLocal<SimpleDateFormat> safeSimpleDataFormat =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    /**
     * 不同的线程执行 get 方法，返回的 SimpleDateFormat 对象是不同的
     * @return
     */
    public DateFormat get() {
        return safeSimpleDataFormat.get();
    }

    public static void main(String[] args) {
        SafeDateFormat safeDateFormat = new SafeDateFormat();

        for (int i = 0; i < 5; i++) {
            new Thread(() ->
                    System.out.println(String.format("thread Id: %d, dateFormat: %s",
                            Thread.currentThread().getId(),
                            safeDateFormat.get()))).start();
        }
    }

}
