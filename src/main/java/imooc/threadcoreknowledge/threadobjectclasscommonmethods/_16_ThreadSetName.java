package imooc.threadcoreknowledge.threadobjectclasscommonmethods;

/**
 * 修改线程的名字
 */
public class _16_ThreadSetName {
    public static void main(String[] args) {
        Thread thread = new Thread();
        thread.start();
        thread.setName("A");
        System.out.println(thread.getName());
    }
}

