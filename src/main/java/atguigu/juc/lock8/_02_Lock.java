package atguigu.juc.lock8;


import java.util.concurrent.TimeUnit;

/**
 * 2、1部手机，邮件方法暂停4秒，请问先打印邮件还是短信？ 短信???
 */
class Phone2 {
    public synchronized void sendEmail() throws InterruptedException {
        // 休眠4秒钟
        TimeUnit.SECONDS.sleep(4);
        System.out.println(Thread.currentThread().getName() + "-----sendEmail");
    }


    public synchronized void sendSMS() {
        System.out.println(Thread.currentThread().getName() + "-----sendSMS");
    }
}

public class _02_Lock {
    public static void main(String[] args) throws InterruptedException {
        Phone2 phone2 = new Phone2();

        new Thread(() -> {
            try {
                phone2.sendEmail();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();

        Thread.sleep(100);

        new Thread(() -> {
            phone2.sendSMS();
        }, "B").start();
    }
}
