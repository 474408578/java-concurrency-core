package atguigu.juc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞队列：
 *      1、当队列是空的，从队列中获取元素的操作会被阻塞
 *      2、当队列是满的，从队列中添加元素的操作会被阻塞
 *
 * ArrayBlockingQueue: 数组的有界阻塞队列的实现
 *      抛出异常：
 *          add(): 当队列满时，再往队列里add插入元素会抛出IllegalStateException
 *          remove()：当队列为空时，再取元素会抛出NoSuchElementException。
 *          element()
 *
 *      特殊值：
 *          offer(): 插入成功返回true，失败返回false
 *          poll(): 成功返回出队列的元素，队列没有就返回null
 *          peek()
 *
 *      阻塞：
 *          put(): 队列满时再添加会一直阻塞，直到put数据或者响应中断退出
 *          take()：队列空时再取,会一直阻塞，直到队列可用
 *
 *      超时退出：
 *          offer(Object e, long timeout, TimeUnit unit)：当队列满了，再添加元素时，超过设定的时间会超时、
 *          poll(long timeout, TimeUnit unit)：当队列空时，再取元素，超过设定的时间会超时
 */
public class _15_BlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue blockingQueue = new ArrayBlockingQueue(3);

        /*
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
//        System.out.println(blockingQueue.add("d"));
        blockingQueue.remove();
        blockingQueue.remove();
        blockingQueue.remove();
        blockingQueue.remove();
        */

        /*
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
//        System.out.println(blockingQueue.offer("d"));
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        */

        /*
        blockingQueue.put("a");
        blockingQueue.put("a");
        blockingQueue.put("a");
//        blockingQueue.put("a");
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        */

        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("a"));
        // 队列满后，3秒钟返回false
        System.out.println(blockingQueue.offer("a", 3L, TimeUnit.SECONDS));


    }



}
