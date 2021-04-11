package com.xschen.feature.singleton;

/**
 * 静态类部类单例
 * @author xschen
 */
public class StaticSingleton {

    private StaticSingleton() {

    }

    private static class StaticSingletonHolder {
        private static StaticSingleton singleton = new StaticSingleton();
    }

    /**
     * 调用 getInstance 时，才会加载StaticSingletonHolder类，然会创建instance.
     * instance的唯一性和线程安全是由 JVM 来保证的.
     * @return
     */
    public static StaticSingleton getInstance() {
        return StaticSingletonHolder.singleton;
    }
}
