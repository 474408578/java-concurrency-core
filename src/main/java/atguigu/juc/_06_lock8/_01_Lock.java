package atguigu.juc._06_lock8;


/**
 * 线程操作资源类
 *
 * 1、1部手机，标准同步访问，请问先打印邮件还是短信？ 邮件
 *
 *
 * synchronized锁的不是一个方法，锁的是这个方法所在的整个资源类，也就是当前这个对象，
 * 即同一时间段只有一个线程能够进入这个资源类。
 */
class Phone1 {
    public synchronized void sendEmail() {

        System.out.println(Thread.currentThread().getName() + "-----sendEmail");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("sendEmail" + Thread.currentThread().getName() + "运行完毕");
    }

    public synchronized void sendSMS() {
        System.out.println(Thread.currentThread().getName() + "-----sendSMS");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("sendSMS" + Thread.currentThread().getName() + "运行完毕");
    }
}

public class _01_Lock {
    public static void main(String[] args) throws InterruptedException {
        Phone1 phone1 = new Phone1();
        new Thread(() -> {
            phone1.sendEmail();
        }, "A").start();

        Thread.sleep(1000);

        new Thread(() -> {
            phone1.sendSMS();
        }, "B").start();

    }
}
