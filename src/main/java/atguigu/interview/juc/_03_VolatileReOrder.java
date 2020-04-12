package atguigu.interview.juc;

/**
 * 指令重排：
 *      计算机在执行程序时，为了提高性能，编译器和处理器常常会对指令重排，一般分以下三种：
 *          1、编译器优化后的重排
 *          2、指令并行的重排
 *          3、内存系统的重排
 *      单线程环境里面确保程序最终执行结果和代码顺序执行的结果一致。
 *      处理器在进行指令重排时必须考虑指令之间的数据依赖性。
 *      多线程环境中线程交替执行，由于编译器优化重排的存在，两个线程中使用的变量能否保证一致性是无法确定的，结果无法预测。
 *
 * 并发场景下存在指令重排
 *
 */
public class _03_VolatileReOrder {
    private static int x = 0, y = 0;
    private static int a = 0, b = 0;
//    private static volatile int a = 0, b = 0;// 禁止指令重排

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        for(;;) {
            i++;
            x = 0;
            y = 0;

            a = 0;
            b = 0;

            Thread t1 = new Thread(() -> {
                a = 1;
                x = b;
            });

            Thread t2 = new Thread(() -> {
                b = 1;
                y = a;
            });

            t1.start();
            t2.start();
            t1.join();
            t2.join();
            /**
             *
             */

            String result = "第" + i + "次" + "(" + x + ", " + y + ")";
            if (x == 0 && y == 0) {
                System.out.println(result);
                break;
            } else{
                System.out.println(result);
            }
        }
    }
}
