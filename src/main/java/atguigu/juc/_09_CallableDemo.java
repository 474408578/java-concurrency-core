package atguigu.juc;

import java.util.concurrent.Callable;

/**
 * 使用线程池来实现多线程
 *
 * 面试题：Callable接口与Runnable接口的区别？
 *      1、是否有返回值
 *      2、是否抛异常
 *      3、落地方法不一样，一个run，一个是call
 */
public class _09_CallableDemo {
    public static void main(String[] args) {

    }

    class MyThread implements Runnable {
        @Override
        public void run() {

        }
    }

    class MyThread02 implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            System.out.println("come in here");
            return 1024;
        }
    }
}
