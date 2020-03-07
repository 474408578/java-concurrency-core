package imooc.synchronize;

/**
 * 消失的请求数，线程不安全
 */
public class _01_DisappearRequest implements Runnable {
    static  _01_DisappearRequest instance = new _01_DisappearRequest();
    static int i = 0;

    @Override
    public void run() {
        for (int j = 0; j < 100000; j++) {
            i++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        /**
         * 两个线程操作同一个实例
         */
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(i);// 结果小于200000 可见性
    }
}
/*
解决方法
1、在run方法中加synchronized关键字
public synchronized void run() {
    for (int j = 0; j < 100000; j++) {
        i++;
    }
}


2、使用对象锁的代码块形式
 public void run() {
     synchronized (this) {
         for (int j = 0; j < 100000; j++) {
            i++;
         }
     }
 }

3、类锁的*.class形式
public void run() {
        synchronized (_01_DisappearRequest.class) {
            for (int j = 0; j < 100000; j++) {
                i++;
            }
        }
    }

4、类锁的static形式
对于run方法不可以使用static，如果是我们实现的普通方法，则可以使用static，封装一下就可以了。
    public void run() {
        add();
    }

    public static synchronized void add() {
        for (int j = 0; j < 100000; j++) {
            i++;
        }
    }

*/