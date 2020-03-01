package imooc.threadcoreknowledge.createthreads;

/**
 * 同时使用Runnable和Thread方式实现线程的方式
 */
public class BothRunnableThread {
    public static void main(String[] args) {
        /**
         * 传入target；
         */
        new Thread(new Runnable(){
            public void run() {
                System.out.println("我来自Runnable");
            }
        })
                /**
                 * 匿名内部类的方式，run函数被改写, 经典的三行代码不存在了
                 */
        {
            @Override
            public void run() {
                System.out.println("我来自Thread类");
            }
        }.start();
    }
}

/**
    @Override
    public void run() {
        if (target != null) {
            target.run();
        }
    }
    private Runnable target;
    //What will be run.
    private Runnable target;
 */