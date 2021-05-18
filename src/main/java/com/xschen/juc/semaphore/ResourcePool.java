package com.xschen.juc.semaphore;

import com.xschen.juc.threadpool.ThreadPoolBuilder;
import com.xschen.utils.ThreadUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

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
        semaphore.acquire();
        return resources.poll(timeOut, TimeUnit.SECONDS);

    }

    public void release(T resource) throws InterruptedException {
        if (resource != null) {
            resources.put(resource);
            semaphore.release();
        }
    }

    public static void main(String[] args) {
        ThreadPoolExecutor executor = ThreadPoolBuilder
                .cachedPool()
                .setThreadNamePrefix("resource-thread")
                .build();

        ResourcePool<Integer> pool = new ResourcePool<>(3,
                Arrays.asList(0, 1, 2));
        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            executor.execute(() -> {
                try {
                    Integer value = pool.get(60);
                    System.out.println(Thread.currentThread().getName() +  " Value taken: " + value);
                    ThreadUtil.sleepMillis(random.nextInt(5000));
                    pool.release(value);
                    System.out.println(Thread.currentThread().getName() + " Value released: " + value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();

    }
}
