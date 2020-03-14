package imooc.threadcoreknowledge.threadobjectclasscommonmethods;

import java.util.Date;
import java.util.LinkedList;

/**
 * 使用wait/notify实现生产者消费者模式
 */
public class _04_ProducerConsumerModel {

    public static void main(String[] args) {

        EventStorage storage = new EventStorage();
        Producer producer = new Producer(storage);
        Consumer consumer = new Consumer(storage);

        new Thread(producer).start();
        new Thread(consumer).start();


    }
    static class Producer implements Runnable{
        private EventStorage storage;

        public Producer(EventStorage storage) {
            this.storage = storage;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                storage.put();
            }
        }
    }

    static class Consumer implements Runnable {
        private EventStorage storage;

        public Consumer(EventStorage storage) {
            this.storage = storage;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                storage.take();
            }
        }
    }


    static class EventStorage {
        private int maxSize;
        private LinkedList<Date> storage;

        public EventStorage() {
            this.maxSize = 10;
            this.storage = new LinkedList<>();
        }

        // 如果满了则进入wait状态， 每次放入一个都唤醒
        synchronized void put() {
            while (storage.size() == maxSize) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            storage.add(new Date());
            System.out.println("仓库有了" + storage.size() + "个产品");
            this.notify();
        }

        synchronized void take() {
            while (storage.size() == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("拿到了" + storage.poll() + "，现在仓库还剩" + storage.size() + "个产品" );
            // 唤醒另外一个线程
            this.notify();
        }
    }
}
