import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生产者消费者
 */
class MyResource{

    private volatile boolean FLAG = true;
    private AtomicInteger atomicInteger = new AtomicInteger();
    BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);

    public void producer() throws InterruptedException {
        String s;
        while (FLAG){
            s = atomicInteger.incrementAndGet() + "";
            boolean offer = queue.offer(s);
            if (offer){
                System.out.println(Thread.currentThread().getName()+"\t 插入队列成功\t"+s);
            }else{
                System.out.println(Thread.currentThread().getName()+"\t 插入队列失败\t"+s);
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName()+"\t停止生产");
    }

    public void consumer() throws InterruptedException {
        String result;
        while (FLAG){
            result = queue.poll(2L,TimeUnit.SECONDS);
            if (result==null){
                FLAG=false;
                System.out.println(Thread.currentThread().getName()+"\t超时2s没有消费到 退出");
                return;
            }
            System.out.println(Thread.currentThread().getName()+"\t消费成功\t"+result);
//            TimeUnit.SECONDS.sleep(1);
        }
    }

    public void stop(){
        FLAG = false;
    }
}
public class ProdConsuBlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        MyResource myResource = new MyResource();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t生产线程启动");
            try {
                myResource.producer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"Prod线程").start();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t消费线程启动");
            try {
                myResource.consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"Consumer线程").start();

        TimeUnit.SECONDS.sleep(10);

        myResource.stop();
        System.out.println(Thread.currentThread().getName()+"\t叫停生产");
    }
}
