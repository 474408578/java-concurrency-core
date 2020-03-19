package imooc.threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * 线程ID从1开始，JVM运行起来之后，我们创建的线程早已不是2
 */
public class _15_ThreadId {
    public static void main(String[] args) {
        Thread thread = new Thread();
        System.out.println("主线程的ID为" + Thread.currentThread().getId());
        System.out.println("线程的ID为" + thread.getId());
    }
}
