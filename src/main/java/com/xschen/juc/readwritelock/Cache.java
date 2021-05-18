package com.xschen.juc.readwritelock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 通过 {@link ReadWriteLock} 实现一个缓存工具类
 * @author xschen
 */


public class Cache<K, V> {

    final Map<K, V> map = new HashMap<>();

    final ReadWriteLock rwl = new ReentrantReadWriteLock();
    final Lock readLock = rwl.readLock();
    final Lock writeLock = rwl.writeLock();
    // 读缓存
    V get(K key) {
        readLock.lock();
        try {
            return map.get(key);
        } finally {
            readLock.unlock();
        }
    }

    // 写缓存
    V put(K key, V value) {
        writeLock.lock();
        try {
            return map.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * 按需加载
     * @param key
     * @return
     */
    V getOnDemand(K key) {
        V v = null;
        readLock.lock();
        try {
            v = map.get(key);
        } finally {
            readLock.unlock();
        }

        // 缓存中存在，返回
        if (v != null) {
            return v;
        }

        // 缓存中不存在，需要去查询数据库
        writeLock.lock();
        try {
            // 再次验证，并发情况下可能其他线程已经查询过数据库
            v = map.get(key);
            if (v == null) {
                // 省略若干查询数据库的代码，得到v
                map.put(key, v);
            }
        } finally {
            writeLock.unlock();
        }
        return v;
    }
}
