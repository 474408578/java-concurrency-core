package atguigu.juc.lock8;


import java.util.concurrent.TimeUnit;

/**
 * 3、1部手机，新增一个普通方法hello()，请问先打印邮件还是hello()?
 */

class Phone3 {
    public synchronized void sendEmail() throws InterruptedException {
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
public class _03_Lock {

    public static void main(String[] args) throws InterruptedException {
        Phone3 phone3 = new Phone3();
        new Thread(() -> {
            try {
                phone3.sendEmail();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();

        Thread.sleep(100);

        new Thread(() -> {
            phone3.hello();
        }, "B").start();
    }
}
