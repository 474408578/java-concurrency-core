package atguigu.juc;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 题目：请举例说明集合类时不安全的。
 *      ArrayList是线程不安全的，不具有Synchronized关键字
 * 1、故障现象
 *      java.util.ConcurrentModificationException
 *
 * 2、导致原因
 *      集合类ArrayList的不安全性
 * 3、解决方案
 *      a、使用Vector 性能慢，
 *      b、使用Collections.synchronizedList(new List)
 *      c、CopyOnWriteArrayList      写时复制技术（性能最好）
 *
 *
 * 4、优化建议（同样的错误，不出现第二次）
 *
 * 知识点：
 *      1、UUID
 *      2、System.()
 */
/*
写时复制
CopyOnWrite容器，即写时复制容器，在一个容器添加元素的时候，不直接往当前容器Object[]添加，
而是现将当前容器的Object[]进行copy，复制出一个新的容器Object[] newElements,
然后在新的容器Object[] newElements里添加元素，添加完元素之后，再将原容器的引用指向新容器---setArray(newElements)。
这样做的好处是可以对CopyOnWrite容器进行并发的读，而不需要加锁，因为当前容器不会添加任何元素，
所以CopyOnWrite容器也是一种读写分离的思想，读和写不同的容器。


public boolean add(E e) {
        final ReentrantLock lock = this.lock;
        // 加锁
        lock.lock();
        try {
            Object[] elements = getArray();
            int len = elements.length;
            // 复制
            Object[] newElements = Arrays.copyOf(elements, len + 1);
            newElements[len] = e;
            setArray(newElements);
            return true;
        } finally {
            lock.unlock();
        }
    }
 */
public class _07_ThreadNoSafe {
    public static void main(String[] args) {
//        List<String> list = new ArrayList<>();
//        List<String> list = new Vector<>();
//        List<String> list = Collections.synchronizedList(new ArrayList<>());
        List<String> list = new CopyOnWriteArrayList<>();

        /**
         * 多个线程，边写边读
         */
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}
