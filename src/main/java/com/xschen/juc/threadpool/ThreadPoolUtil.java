package com.xschen.juc.threadpool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ThreadFactory;

/**
 * ThreadPool 工具类
 * @author xschen
 */
public class ThreadPoolUtil {



    public static ThreadFactory buildThreadFactory(String threadNamePrefix, Boolean daemon) {
        return new ThreadFactoryBuilder()
                .setNameFormat(threadNamePrefix + "-%d")
                .setDaemon(daemon).build();
    }

    public static ThreadFactory buildThreadFactory(String threadNamePrefix) {
        return new ThreadFactoryBuilder()
                .setNameFormat(threadNamePrefix + "-%d").build();
    }
}
