package com.xschen.feature.singleton;

/**
 * 双重检查锁实现单例:
 *  1. 保证可见性
 *  2. 防止指令重排序
 * @author xschen
 */
public class Singleton {
    /**
     * volatile 保证可见性
     */
    private volatile static Singleton singleton;

    /**
     * 私有无参构造保证唯一入口
     */
    private Singleton() {
    }

    /**
     * new 操作理想情况下：
     *      1. 分配一块内存 M;
     *      2. 在内存 M 上初始化 Singleton 对象;
     *      3. 然后 M 的地址赋值给 instance 变量。
     *
     * 指令重排序后的 new 操作
     *      1. 分配一块内存M；
     *      2. 将M的地址赋给instance变量
     *      3. 在内存M上初始化Singleton对象
     * @return
     */
    public Singleton getSingleton() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    // 防止指令重排，导致 空指针问题.
                    singleton = new Singleton();
                }
            }
        }

        return singleton;
    }
}
