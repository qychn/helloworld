import java.util.concurrent.CountDownLatch;

/**
 * @author qy
 * @version 1.0
 * @date 2021/1/30 23:06
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(7);

        for (int i = 1; i <=7 ; i++) {
            int finalI = i;
            new Thread(()->{
            },String.valueOf(i)).start();
        }
        countDownLatch.await();
        System.out.println("关门");
    }
}
