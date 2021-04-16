package com.xschen.utils;

import com.xschen.feature.limiter.TimeIntervalLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 由程序触发的 ThreadDump，打印到日志中
 * 因为ThreadDump本身会造成JVM停顿，所以加上了开关和最少间隔时间的选项(默认不限制)
 * 因为ThreadInfo的toString()最多只会打印8层的StackTrace，所以加上了最大打印层数的选项(默认为8)
 * @author xschen
 */

public class ThreadDumpHelper {

    private static final Logger logger = LoggerFactory.getLogger(ThreadDumpHelper.class);

    private static final int DEFAULT_MAX_STACK_LEVEL = 8;

    /**
     * 10 分钟
     */
    private static final int DEFAULT_MIN_IN = 1000 * 60 * 10;

    private int maxStackLevel;

    private TimeIntervalLimiter timeIntervalLimiter;

    public ThreadDumpHelper() {
        this(DEFAULT_MIN_IN, DEFAULT_MAX_STACK_LEVEL);
    }

    public ThreadDumpHelper(long leastIntervalMills, int maxStackLevel) {
        this.maxStackLevel = maxStackLevel;
        timeIntervalLimiter = new TimeIntervalLimiter(leastIntervalMills, TimeUnit.MILLISECONDS);
    }

    /**
     * 设置 ThreadDump 的最小时间间隔，单位自行指定
     * @param leastInterval 最小间隔时间
     * @param unit 时间单位
     */
    public void setTimeIntervalLimiter(int leastInterval, TimeUnit unit) {
        this.timeIntervalLimiter = new TimeIntervalLimiter(leastInterval, unit);
    }

    /**
     * 打印 stackTrace 的最大深度，默认为 8
     * @param maxStackLevel
     */
    public void setMaxStackLevel(int maxStackLevel) {
        this.maxStackLevel = maxStackLevel;
    }

    public void tryThreadDump() {
        this.tryThreadDump(null);
    }

    /**
     * 符合条件，打印线程栈
     * @param reasonMsg
     */
    public void tryThreadDump(String reasonMsg) {
        if (timeIntervalLimiter.tryAcquire()) {
            threadDump(reasonMsg);
        }
    }

    /**
     * 强行打印ThreadDump，使用最轻量的采集方式，不打印锁信息
     * @param reasonMsg
     */
    private void threadDump(String reasonMsg) {
        String msg = (reasonMsg != null ? ("for " + reasonMsg): "");
        logger.info("Thread dump by ThreadDumpHelper " + msg);
        Map<Thread, StackTraceElement[]> threads = Thread.getAllStackTraces();
        // 两条日志的时间间隔，是 VM 被 threadDump 阻塞的时间
        logger.info("Finish the threads snapshot");

        StringBuilder sb = new StringBuilder(8092 * 20).append('\n');
        for (Map.Entry<Thread, StackTraceElement[]> entry : threads.entrySet()) {
            dumpThreadInfo(entry.getKey(), entry.getValue(), sb);
        }
        logger.info(sb.toString());
    }

    /**
     *
     * @param thread
     * @param stackTrace
     * @param sb
     */
    private void dumpThreadInfo(Thread thread, StackTraceElement[] stackTrace, StringBuilder sb) {
        sb.append('\"').append(thread.getName()).append("\" Id=").append(thread.getId()).append(' ').append(thread.getState()).append('\n');
        int i = 0;
        for ( ; i < Math.min(maxStackLevel, stackTrace.length); i++) {
            StackTraceElement ste = stackTrace[i];
            sb.append("\tat ").append(ste).append('\n');
        }

        if (i < stackTrace.length) {
            sb.append("\t...").append('\n');
        }

        sb.append('\n');
    }

}
