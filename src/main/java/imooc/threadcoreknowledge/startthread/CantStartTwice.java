package imooc.threadcoreknowledge.startthread;

/**
 * 演示不能两次调用start方法，否则会报错
 * start()
 *  1、检查线程状态
 *  2、将线程加入线程组
 *  3、调用start0()
 *
 *
 */
public class CantStartTwice {
    public static void main(String[] args) {
        Thread thread = new Thread(){
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        };

        thread.start();


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        thread.start();
    }
}
