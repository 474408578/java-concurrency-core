package atguigu.juc.lock8;

import java.util.concurrent.TimeUnit;

/**
 * 8、一个普通同步方法，1个静态同步方法，2部手机，请问先打印邮件还是短信？
 */

class Phone8 {
    public static synchronized void sendEmail() throws InterruptedException {
        // 休眠4秒钟
        TimeUnit.SECONDS.sleep(4);
        System.out.println(Thread.currentThread().getName() + "-----sendEmail");
    }

    public synchronized void sendSMS() {
        System.out.println(Thread.currentThread().getName() + "-----sendSMS");
    }

    public void hello() {
        System.out.println(Thread.currentThread().getName() + "----hello");
    }
}

public class _08_Lock {
    public static void main(String[] args) throws InterruptedException {
        Phone8 phone8_1 = new Phone8();
        Phone8 phone8_2 = new Phone8();

        new Thread(() -> {
            try {
                phone8_1.sendEmail();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();

        Thread.sleep(100);

        new Thread(() -> {
            phone8_2.sendSMS();
        }, "B").start();
    }
}
