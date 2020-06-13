package imooc.concurrency_tools_practice.future;

import java.util.concurrent.Callable;

/**
 * Runnable的缺陷：
 *      1、没有返回值
 *      2、不能抛出checked Exception
 *
 * Callable接口：
 *      1、类似于Runnable，被其他线程执行的任务
 *      2、实现call方法
 *      3、有返回值
 *      4、看源码
 */

public class _01_RunnableCantThrowsException {
    public void ddd() throws Exception {
        new Callable<Object>(){
            @Override
            public Object call() throws Exception {
                return null;
            }
        };

    }
    public static void main(String[] args) {
        new Runnable(){
            @Override
            public void run() {
                try {
                    throw new Exception();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

}
