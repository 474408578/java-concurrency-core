package atguigu.juc.lock8;

import java.util.concurrent.TimeUnit;

/**
 * 7、一个普通同步方法，1个静态同步方法，1部手机，请问先打印邮件还是短信？
 */
class Phone7 {
    public static synchronized void sendEmail() throws InterruptedException {
        // 休眠4秒钟
        TimeUnit.SECONDS.sleep(4);
        System.out.println(Thread.currentThread().getName() + "-----sendEmail");
    }


    public synchronized void sendSMS() {
        System.out.println(Thread.currentThread().getName() + "-----sendSMS");
    }

    public void hello(){
        System.out.println(Thread.currentThread().getName() + "----hello");
    }
}

public class _07_Lock {
    public static void main(String[] args) throws InterruptedException {
        Phone7 phone7 = new Phone7();
        new Thread(() -> {
            try {
                phone7.sendEmail();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();

        Thread.sleep(100);

        new Thread(() -> {
            phone7.sendSMS();
        }, "B").start();
    }
}
