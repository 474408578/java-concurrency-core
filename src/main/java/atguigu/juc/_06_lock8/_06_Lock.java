package atguigu.juc._06_lock8;

import java.util.concurrent.TimeUnit;

/**
 * 6、两个静态同步方法, 2个手机，请问先打印邮件还是短信？
 * 先打印邮件，static锁的整个类的Class对象 Phone.class对象
 */
class Phone6 {
    public static synchronized void sendEmail() throws InterruptedException {
        System.out.println("sendEmail");
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

public class _06_Lock {
    public static void main(String[] args) throws InterruptedException {
        Phone6 phone6_1 = new Phone6();
        Phone6 phone6_2 = new Phone6();

        new Thread(() -> {
            try {
                phone6_1.sendEmail();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();

        new Thread(() -> {
            phone6_2.sendSMS();
        }, "B").start();
    }

}
