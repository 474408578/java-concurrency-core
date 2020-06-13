package imooc.concurrency_tools_practice.future;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Callable和Future的关系：
 *      1、可以通过Future.get来获取Callable接口返回的执行结果
 *
 *      2、可以通过Future.isDone来判断任务是否已经执行完了
 *
 *      3、在call()未执行完成之前，调用get()的线程会被阻塞，直到获取到返回结果才会回到Runnable状态。
 *
 *      4、Future是一个存储器，它存储了call()这个任务的结果，
 *
 *  Future接口：
 *      1、get()方法：如果任务（call方法）执行过程中抛出异常，那么get()会抛出java.util.concurrent.ExecutionException；
 *                   如果任务被取消，get()会抛出java.util.concurrent.CancellationException。
 *                   如果任务超时（get有个重载的方法，可以传入超时时间），会抛出TimeoutException
 *
 *     2、cancel():
 *
 *     3、isDone():
 *
 *     4、isCanceled():
 *
 */
public class _02_OneFuture {


    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        Future<Integer> future = service.submit(new CallableTask());
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        service.shutdown();
    }

    static class CallableTask implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            Thread.sleep(3000);
            return new Random().nextInt();
        }
    }

}
