package atguigu.interview.juc;

/**
 * 演示Volatile不能保证原子性：
 *      线程A在执行任务的时候，是不能被打扰的，要么同时成功，要么同时失败
 * 为什么？
 *
 */
public class _02_VolatileCantPromiseAtomicity {

    public static void main(String[] args) {
        MyData1 myData1 = new MyData1();
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    myData1.addOne();
                }
            }, String.valueOf(i)).start();
        }

        // 需要等待20个线程都计算完成后，main线程取得最终的结果
        while (Thread.activeCount() > 2){ // main gc
            Thread.yield();
        }
        System.out.println("num = " + myData1.num);
    }
}


class MyData1 {
    volatile int num = 0;
    // 加synchronized可以保证
    public void addOne() {
        num++;
    }
}