package javacode.jichu;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author sunxu93@163.com
 * @date 19/9/1/001 17:24
 */
public class ExecutorShutdownnowTest {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("Thread run");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdownNow();
        System.out.println("Main run");
    }
}
