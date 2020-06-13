package imooc.concurrency_tools_practice.future;

import java.util.Random;
import java.util.concurrent.*;

public class _03_OneFutureLambda {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return new Random().nextInt();
            }
        };
        Future<Integer> future = service.submit(callable);
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        service.shutdown();
    }
}
