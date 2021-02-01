import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁
 */
public class SpinLockDemo {

    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void mylock() {
        Thread thread = Thread.currentThread();
        while (!atomicReference.compareAndSet(null,thread)){

        }
        System.out.println(Thread.currentThread().getName()+"获取锁");
    }
    public void myunlock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println(Thread.currentThread().getName()+"释放锁");
    }

    public static void main(String[] args) throws InterruptedException {
        //资源类
        SpinLockDemo spinLockDemo = new SpinLockDemo();

        //线程A获取锁
        new Thread(()->{
            try {
                spinLockDemo.mylock();
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                spinLockDemo.myunlock();
            }
        },"A").start();

        //为了让A先获取锁
        TimeUnit.SECONDS.sleep(1);

        //线程B
        new Thread(()->{
            spinLockDemo.mylock();
            spinLockDemo.myunlock();
        },"B").start();
    }

}
