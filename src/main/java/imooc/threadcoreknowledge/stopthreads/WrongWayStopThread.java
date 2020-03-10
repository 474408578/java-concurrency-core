package imooc.threadcoreknowledge.stopthreads;

/**
 * 错误的停止线程的方法：
 *      停止线程被弃用的方法: stop()、suspend()、resume()
 *      使用volatile设置boolean标记位
 *
 * 描述：错误的停止方法：用stop来停止线程，会导致线程运行一半突然停止，
 * 没办法完成一个基本单位的操作（一个连队），会造成脏数据（有的连队多领取，有的少领取）
 */
public class WrongWayStopThread implements Runnable {
    @Override
    public void run() {
        // 模拟指挥军队：一共有5个连队，每个连队10人，以连队为单位，发放武器弹药，叫到号的士兵前去领取
        for (int i = 0; i < 5; i++) {
            System.out.println("连队" + i + "开始领取武器");
            for (int j = 0; j < 10; j++) {
                System.out.println(j);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("连队" + i + "已经领取完毕");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread =  new Thread(new WrongWayStopThread());
        thread.start();
        Thread.sleep(1000);
        thread.stop();
//        thread.interrupt();
    }
}
