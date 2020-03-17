package imooc.threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * 若线程A调用线程B的join方法，那么线程A的运行将会被暂停，直到线程B运行结束
 *
 * 在此例中，main线程分别调用thread， thread1的join方法，main线程被暂停，直到thread、thread2运行结束
 */
public class _10_Join {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "执行完毕");
        });


        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "执行完毕");
        });

        thread.start();
        thread2.start();

        System.out.println("开始等待子线程执行完毕");
        thread.join();
        thread2.join();
        System.out.println("所有子线程执行完毕");

    }
}
