package atguigu.juc._06_lock8;

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
 *
 *
 *
 * 解答：
 * new this     具体的一部手机
 * 静态 class 唯一的手机模板
 *
 * 所有的 非静态同步方法用的都是同一把锁---实例对象本身
 *
 * synchronized实现同步的基础，Java中的每一个对象都可以作为锁
 *
 * 具体表现为以下三种形式：
 *      对于普通同步方法，锁的是当前对象
 *      对于静态同步方法，锁的是当前类的class对象
 *      对于同步方法代码块，锁的是synchronized括号里配置的对象。
 *
 * 当一个线程试图访问同步代码块时，它首先必须先获得锁，退出或抛出异常时必须先释放锁
 *
 * 也就是说如果一个实例对象的非静态同步方法获取锁后，该实例对象的其他非静态同步方法必须等待获取锁的方法释放锁之后才能获取锁
 *
 * 可是别的实例对象的非静态同步方法因为跟该实例对象的非静态同步方法用的是不同的锁，
 *
 * 所以毋需等待该实例对象已获取的非静态同步方法释放锁就可以获取他们自己的锁
 *
 *
 * 所有的静态同步方法用的也是一把锁---类对象本身
 *
 * 这两把锁是不同的两个对象，所以静态同步方法和非静态同步方法之间是不会有竞态条件的。
 * 但是一旦静态同步方法获取锁之后，其他的静态同步方法必须等待该方法释放锁，
 * 而不管是同一个实例对象的静态同步方法之间，还是不同实例对象的静态同步方法之间。
 *
 *
 *
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



