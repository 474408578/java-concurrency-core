package atguigu.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * 获得多线程的方式：实现Callable接口
 * 面试题：
 *      Callable接口与Runnable接口的区别?
 *          1、是否有返回值
 *          2、是否抛异常
 *          3、落地方法不一样，一个是run, 一个是call
 *
 * 使用callable实现接口的方式创建线程注意点：
 *      1、get()方法一般请放在最后一行
 *      2、Future的生命周期不能后退: 一旦完成了任务，它就永久停在了”已完成"的状态，不能从头再来
 */
public class _10_CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask futureTask = new FutureTask(new MyThread2());
        new Thread(futureTask, "A").start();
        new Thread(futureTask, "B").start();
        System.out.println(Thread.currentThread().getName() + "开始计算");
        System.out.println(futureTask.get());

        System.out.println(Thread.currentThread().getName() + "计算完成");
    }
}

class MyThread implements Runnable {
    @Override
    public void run() {

    }
}

class MyThread2 implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println("come in here");
        TimeUnit.SECONDS.sleep(4);
        return 1024;
    }
}


