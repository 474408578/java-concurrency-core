package atguigu.juc.lock8;

import java.util.concurrent.TimeUnit;

/**
 * 题目：多线程8锁
 * 1、标准访问，请问先打印邮件还是短信？ 邮件
 * 2、邮件方法暂停4秒，请问先打印邮件还是短信？ 短信
 * 3、新增一个普通方法hello()，请问先打印邮件还是hello();
 * 4、两部手机，请问先打印邮件还是短信？
 * 5、两个静态同步方法，同一个手机，请问先打印邮件还是短信？
 * 6、两个静态同步方法, 2个手机，请问先打印邮件还是短信？
 * 7、一个普通同步方法，1个静态同步方法，1部手机，请问先打印邮件还是短信？
 * 8、一个普通同步方法，1个静态同步方法，2部手机，请问先打印邮件还是短信？
 */


class Phone {
    public static synchronized void sendEmail() throws InterruptedException {
        // 休眠4秒钟
        TimeUnit.SECONDS.sleep(4);
        System.out.println(Thread.currentThread().getName() + "-----sendEmail");
    }


    public  synchronized void sendSMS() {
        System.out.println(Thread.currentThread().getName() + "-----sendSMS");
    }

    public void hello(){
        System.out.println(Thread.currentThread().getName() + "----hello");
    }
}

public class LockPhenomenon {
    public static void main(String[] args) throws InterruptedException {
        Phone phone = new Phone();
        Phone phone1 = new Phone();
        new Thread(() -> {
            try {
                phone.sendEmail();
//                phone.sendSMS();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();

        Thread.sleep(200);

        new Thread(() -> {
           phone.sendSMS();
        }, "B").start();

        new Thread(() -> {
            phone.hello();
        }, "C").start();

        // phone1
        new Thread(() -> {
            phone1.sendSMS();
        }, "D").start();

    }
}



