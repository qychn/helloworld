import java.util.concurrent.*;

public class CountDownLatchDemo2 {

    public static void main(String[] args) throws InterruptedException {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                3,
                7,
                1L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
        CountDownLatch countDownLatch = new CountDownLatch(7);

        for (int i = 1; i <= 7; i++) {
            int finalI = i;
            threadPoolExecutor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "第" + finalI + "个人离开");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        System.out.println("关门");
        threadPoolExecutor.shutdown();
    }
}
