package imooc.threadcoreknowledge.createthreads.wrongways;

/**
 * 匿名内部类创建线程的方式
 */
public class AnonymousInnerClassDemo {
    public static void main(String[] args) {
        new Thread(){
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }.start();

        new Thread(new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }).start();
    }
}
