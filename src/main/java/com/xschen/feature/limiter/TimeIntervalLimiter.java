package com.xschen.feature.limiter;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 时间间隔限制器
 * @author xschen
 */


public class TimeIntervalLimiter {

    private final AtomicLong lastTimeAtom = new AtomicLong(0);

    private long windowsSizeMillis;

    /**
     * @param interval 间隔时间
     * @param unit 间隔时间单位
     */
    public TimeIntervalLimiter(long interval, TimeUnit unit) {
        this.windowsSizeMillis = unit.toMillis(interval);
    }

    public boolean tryAcquire() {
        long currentTime = System.currentTimeMillis();
        long lastTime = lastTimeAtom.get();
        return currentTime - lastTime >= windowsSizeMillis && lastTimeAtom.compareAndSet(lastTime, currentTime);
    }
}
