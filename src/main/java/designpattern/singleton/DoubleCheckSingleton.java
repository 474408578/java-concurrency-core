package designpattern.singleton;

/**
 * @author xschen
 *
 * 双重锁检查单例模式
 */


public class DoubleCheckSingleton {
    /**
     * 为什么要使用volatile关键字？
     * singleton = new Singleton并非是一个原子操作，它主要做三件事：
     *      1、给singleton分配空间。
     *      2、调用Singleton的构造函数等来初始化singleton
     *      3、将singleton对象指向分配的内存空间（执行完这一步singleton就不是null了）
     *
     * 1-2-3的顺序可以存在指令重排序的优化，也就是2和3的顺序是不能保证的，可能是1-2-3，或者1-3-2
     * 如果是 1-3-2，那么在第 3 步执行完以后，singleton 就不是 null 了，可是这时第 2 步并没有执行，
     * singleton 对象未完成初始化，它的属性的值可能不是我们所预期的值。
     *
     * 假设此时线程 2 进入 getInstance 方法，由于 singleton 已经不是 null 了，所以会通过第一重检查并直接返回，
     * 但其实这时的 singleton 并没有完成初始化，所以使用这个实例的时候会报错
     *
     *
     * 注意时机，第一个线程执行了前两步后，并没有退出synchronized，就在此时，第二个线程进来了，直接在第一个if判断就返回了，
     * 而不是第一个线程已经退出了synchronized代码块
     */
    private static volatile DoubleCheckSingleton singleton;

    private DoubleCheckSingleton() {

    }

    public static DoubleCheckSingleton getInstance() {
        if (singleton == null) {
            /**
             * 假设有两个线程同时到达synchronized语句块，实例化代码只允许抢到锁的线程执行一次，
             * 而后抢到锁的线程会在第二个if判断中发现singleton不为null,所以跳过创建实例的语句。
             * 再后来其他的线程再来调用getInstance方法时，只需判断第一次的if(singleton == null),然后会跳过整个if块，
             * 直接return实例化后的对象。
             *
             * 如果去掉第一个if判断的话，所有线程都会串行执行，效率低下。
             * 如果去掉第二个if判断的话，会导致创建不止一个单例。
             */
            synchronized (DoubleCheckSingleton.class) {
                if (singleton == null) {
                    singleton = new DoubleCheckSingleton();
                }
            }
        }
        return singleton;
    }
}
