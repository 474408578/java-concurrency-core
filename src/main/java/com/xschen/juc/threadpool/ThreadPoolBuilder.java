package com.xschen.juc.threadpool;

import org.apache.commons.lang3.Validate;

import java.util.concurrent.*;

/**
 * @author xschen
 */

public class ThreadPoolBuilder {

    /**
     * 默认的拒绝策略
     */
    private static RejectedExecutionHandler defaultRejectedHandler = new ThreadPoolExecutor.AbortPolicy();

    public static FixedThreadPoolBuilder fixedPool() {
        return new FixedThreadPoolBuilder();
    }

    public static CachedThreadPoolBuilder cachedPool() {
        return new CachedThreadPoolBuilder();
    }

    /**
     * Queue默认为无限长的LinkedBlockingQueue, 但建议设置queueSize换成有界的队列.
     */
    public static class FixedThreadPoolBuilder {
        private int poolSize = 1;
        private int queueSize = -1;
        private ThreadFactory threadFactory;
        private String threadNamePrefix;
        private Boolean daemon;
        private RejectedExecutionHandler rejectedExecutionHandler;

        /**
         * pool 大小，默认为 1， 即 singleThreadPool
         * @param poolSize
         * @return
         */
        public FixedThreadPoolBuilder setPoolSize(int poolSize) {
            Validate.isTrue(poolSize >= 1);
            this.poolSize = poolSize;
            return this;
        }

        /**
         * 不设置时默认为 -1，使用无限长的 LinkedBlockingQueue
         * 为正数时， 使用 ArrayBlockingQueue.
         * @param queueSize
         * @return
         */
        public FixedThreadPoolBuilder setQueueSize(int queueSize) {
            this.queueSize = queueSize;
            return this;
        }

        /**
         * 与 ThreadNamePrefix 互斥， 优先使用 ThreadFactory
         * @param threadFactory
         * @return
         */
        public FixedThreadPoolBuilder setThreadFactory(ThreadFactory threadFactory) {
            this.threadFactory = threadFactory;
            return this;
        }

        /**
         * 与 ThreadFactory 互斥，优先使用 threadFactory
         * @param threadNamePrefix
         * @return
         */
        public FixedThreadPoolBuilder setThreadNamePrefix(String threadNamePrefix) {
            this.threadNamePrefix = threadNamePrefix;
            return this;
        }

        /**
         * 与 ThreadFactory 互斥，优先使用 threadFactory
         * 是否守护线程，默认为Null， 不进行设置，即使用jdk的默认值.
         * @param daemon
         * @return
         */
        public FixedThreadPoolBuilder setDaemon(Boolean daemon) {
            this.daemon = daemon;
            return this;
        }

        public FixedThreadPoolBuilder RejectedExecutionHandler(RejectedExecutionHandler rejectedExecutionHandler) {
            this.rejectedExecutionHandler = rejectedExecutionHandler;
            return this;
        }

        public ThreadPoolExecutor build() {
            BlockingQueue<Runnable> queue = null;
            if (queueSize < 1) {
                queue = new LinkedBlockingDeque<>();
            } else {
                queue = new ArrayBlockingQueue<>(queueSize);
            }

            threadFactory = createThreadFactory(threadFactory, threadNamePrefix, daemon);
            if (rejectedExecutionHandler == null) {
                rejectedExecutionHandler = defaultRejectedHandler;
            }
            return new ThreadPoolExecutor(poolSize, poolSize,
                    0L, TimeUnit.MILLISECONDS,
                    queue, threadFactory, rejectedExecutionHandler);
        }
    }


    /**
     * @see Executors#newCachedThreadPool()
     * 可缓存线程池，线程池默认是无线增加的，
     * 队列为{@link SynchronousQueue}, 不存储任务，只负责中转和传递任务。
     */
    public static class CachedThreadPoolBuilder {
        private int minPoolSize = 0;
        private int maxPoolSize = Integer.MAX_VALUE;
        private int keepAliveSeconds = 10;
        private ThreadFactory threadFactory;
        private String threadNamePrefix;
        private Boolean daemon;
        private RejectedExecutionHandler rejectedExecutionHandler;

        public CachedThreadPoolBuilder setMinPoolSize(int minPoolSize) {
            this.minPoolSize = minPoolSize;
            return this;
        }

        public CachedThreadPoolBuilder setMaxPoolSize(int maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
            return this;
        }

        public CachedThreadPoolBuilder setKeepAliveSeconds(int keepAliveSeconds) {
            this.keepAliveSeconds = keepAliveSeconds;
            return this;
        }

        /**
         * 与 ThreadNamePrefix互斥，优先使用 threadFactory
         * @param threadFactory
         * @return
         */
        public CachedThreadPoolBuilder setThreadFactory(ThreadFactory threadFactory) {
            this.threadFactory = threadFactory;
            return this;
        }

        /**
         * 与 ThreadFactory 互斥，优先使用 threadFactory
         * @param threadNamePrefix
         * @return
         */
        public CachedThreadPoolBuilder setThreadNamePrefix(String threadNamePrefix) {
            this.threadNamePrefix = threadNamePrefix;
            return this;
        }

        /**
         * 与 ThreadFactory 互斥，优先使用 threadFactory
         * @param daemon
         * @return
         */
        public CachedThreadPoolBuilder setDaemon(Boolean daemon) {
            this.daemon = daemon;
            return this;
        }

        public CachedThreadPoolBuilder setRejectedExecutionHandler(RejectedExecutionHandler rejectedExecutionHandler) {
            this.rejectedExecutionHandler = rejectedExecutionHandler;
            return this;
        }

        public ThreadPoolExecutor build() {
            threadFactory = createThreadFactory(threadFactory, threadNamePrefix, daemon);
            if (rejectedExecutionHandler == null) {
                rejectedExecutionHandler = defaultRejectedHandler;
            }
            return new ThreadPoolExecutor(minPoolSize, maxPoolSize,
                    keepAliveSeconds, TimeUnit.SECONDS,
                    new SynchronousQueue<>(), threadFactory, rejectedExecutionHandler);
        }
    }

    private static ThreadFactory createThreadFactory(ThreadFactory threadFactory,
                                                     String threadNamePrefix,
                                                     Boolean daemon) {
        if (threadFactory != null) {
            return threadFactory;
        }

        if (threadNamePrefix != null) {
            if (daemon != null) {
                return ThreadPoolUtil.buildThreadFactory(threadNamePrefix, daemon);
            } else {
                return ThreadPoolUtil.buildThreadFactory(threadNamePrefix);
            }
        }
        return Executors.defaultThreadFactory();
    }
}
