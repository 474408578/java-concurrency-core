package imooc.juc.cas;

/**
 * 模拟CAS操作，等价代码
 */
public class SimulatedCAS {
    private volatile int value;
    public synchronized int compareAndSwap(int expectValue, int newValue) {
        int oldValue = value;
        if (oldValue == expectValue) {
            value = newValue;
        }
        return value;
    }
}
