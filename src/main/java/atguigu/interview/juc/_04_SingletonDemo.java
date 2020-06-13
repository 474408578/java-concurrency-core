package atguigu.interview.juc;

public class _04_SingletonDemo {
    private static _04_SingletonDemo instance = null;
    private _04_SingletonDemo() {
        System.out.println(Thread.currentThread().getName() + "\t我是构造方法");
    }

    // 不加synchronized,多线程下不能保证单例
    public static _04_SingletonDemo getInstance() {
        if (instance == null) {
            instance = new _04_SingletonDemo();
        }
        return instance;
    }

    public static void main(String[] args) {
        // 单线程（main线程的操作动作）(构造方法只是被打印一次)
        /*
        System.out.println(_04_SingletonDemo.getInstance() == _04_SingletonDemo.getInstance());
        System.out.println(_04_SingletonDemo.getInstance() == _04_SingletonDemo.getInstance());
        System.out.println(_04_SingletonDemo.getInstance() == _04_SingletonDemo.getInstance());
        */

        //并发多线程后
        for (int i = 0; i < 10; i++) {
            new Thread(()-> {
                _04_SingletonDemo.getInstance();
            }, String.valueOf(i)).start();
        }
    }
}
