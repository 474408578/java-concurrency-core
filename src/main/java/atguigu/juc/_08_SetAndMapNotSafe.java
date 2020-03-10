package atguigu.juc;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * set是不安全的
 * HashSet是不安全的
 *
 * HashSet的底层数据结构就是HashMap
 *
 * HashMap的结构
 */
public class _08_SetAndMapNotSafe {
    public static void main(String[] args) {
        // HashSet不安全
//        Set<String> set = new HashSet<>();
        //线程安全
//        Set<String> set = Collections.synchronizedSet(new HashSet<>());
        //线程安全的
//        Set<String> set = new CopyOnWriteArraySet<>();

        /*
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(set);
            }, String.valueOf(i)).start();0
        }

         */

//        Map<String, String> map = new HashMap<>();

//        Map<String, String> map = Collections.synchronizedMap(new HashMap());

        Map<String, String> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 8));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }
}
