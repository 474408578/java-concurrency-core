package atguigu.juc._06_lock8;


import java.util.concurrent.TimeUnit;

/**
 * 4、两部手机，请问先打印邮件还是短信？
 * 先打印短信，因为是两个对象，此时的synchronize是对象锁，也就是两个锁
 */
class Phone4 {
    public synchronized void sendEmail() throws InterruptedException {
        // 休眠4秒钟
        TimeUnit.SECONDS.sleep(4);
        System.out.println(Thread.currentThread().getName() + "-----sendEmail");
    }


    public synchronized void sendSMS() {
        System.out.println(Thread.currentThread().getName() + "-----sendSMS");
    }
}

public class _04_Lock {
    public static void main(String[] args) throws InterruptedException {
        Phone4 phone4_1 = new Phone4();
        Phone4 phone4_2 = new Phone4();

        new Thread(() -> {
            try {
                phone4_1.sendEmail();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();

        Thread.sleep(100);

        new Thread(() -> {
            phone4_2.sendSMS();
        }, "B").start();
    }
}
