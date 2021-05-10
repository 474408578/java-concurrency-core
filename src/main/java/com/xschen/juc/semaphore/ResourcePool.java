package com.xschen.juc.semaphore;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 对象资源池
 * @author xschen
 */

public class ResourcePool<T> {
    private Semaphore semaphore;
    private BlockingQueue<T> resources;

    public ResourcePool(int poolSize, List<T> initializedResources) {
        this.semaphore = new Semaphore(poolSize, true); // true 代表 FIFO
        this.resources = new LinkedBlockingQueue<>(poolSize);
        this.resources.addAll(initializedResources);
    }

    public T get() throws InterruptedException {
        return get(Integer.MAX_VALUE);
    }

    /**
     * 获取资源
     * @param timeOut
     * @return
     */
    public T get(long timeOut) throws InterruptedException {
        try {
            semaphore.acquire();
            return resources.poll(timeOut, TimeUnit.SECONDS);
        } finally {
            semaphore.release();
        }
    }


}
