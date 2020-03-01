package imooc.threadcoreknowledge.stopthreads;


/**
 * 如果在执行过程中，每次循环都会调用sleep或者wait等方法，那么不需要在每次迭代都检查中断，
 * 这是因为sleep过程中，会帮我们响应中断
 */
public class RightWayStopThreadSleepEveryLoop {

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            int num = 0;
            // 如果while里面放try/catch, 会导致中断失效
            // 这是因为sleep设计，一旦响应了中断之后，便会把interrupt这个中断标记位清除
            /*
            while(num<Integer.MAX_VALUE){
                if (num % 100 == 0) {
                    System.out.println(num + " 是100的倍数");
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                num++;
            }*/

            try {
                while (num < Integer.MAX_VALUE) {
                    if (num % 100 == 0) {
                        System.out.println(num + " 是100的倍数");
                    }
                    Thread.sleep(10);
                    num++;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        };
        Thread thread = new Thread(runnable);
        thread.start();

        Thread.sleep(5000);
        // 中断
        thread.interrupt();
    }
}
