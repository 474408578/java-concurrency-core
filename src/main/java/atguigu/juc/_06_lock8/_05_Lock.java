package atguigu.juc._06_lock8;

import java.util.concurrent.TimeUnit;

/**
 * 5、两个静态同步方法，同一个手机，请问先打印邮件还是短信？
 * 先打印邮件， 此时使用的类锁，会对static修饰的同步方法加锁，针对这个资源类的所有对象
 */
class Phone5 {
    public static synchronized void sendEmail() throws InterruptedException {
        System.out.println("我是sendmail");
        // 休眠4秒钟
        TimeUnit.SECONDS.sleep(4);
        System.out.println(Thread.currentThread().getName() + "-----sendEmail");
    }


    public static synchronized void sendSMS() {
        System.out.println(Thread.currentThread().getName() + "-----sendSMS");
    }

}

public class _05_Lock {
    public static void main(String[] args) {
        Phone5 phone5 = new Phone5();
        new Thread(() -> {
            try {
                phone5.sendEmail();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();

        new Thread(() -> {
            phone5.sendSMS();
        }, "B").start();
    }
}
