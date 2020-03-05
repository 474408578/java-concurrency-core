package atguigu.juc.lock8;

import java.util.concurrent.TimeUnit;

/**
 * 5、两个静态同步方法，同一个手机，请问先打印邮件还是短信？
 */
class Phone5 {
    public static synchronized void sendEmail() throws InterruptedException {
        // 休眠4秒钟
        TimeUnit.SECONDS.sleep(4);
        System.out.println(Thread.currentThread().getName() + "-----sendEmail");
    }


    public static synchronized void sendSMS() {
        System.out.println(Thread.currentThread().getName() + "-----sendSMS");
    }

    public void hello(){
        System.out.println(Thread.currentThread().getName() + "----hello");
    }
}

public class _05_Lock {
    public static void main(String[] args) throws InterruptedException {
        Phone5 phone5 = new Phone5();
        new Thread(() -> {
            try {
                phone5.sendEmail();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();

        Thread.sleep(100);

        new Thread(() -> {
            phone5.sendSMS();
        }, "B").start();
    }
}
