import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author qy
 * @version 1.0
 * @date 2021/1/31 10:56
 */
class MyCache{
    private volatile Map<String,Object> map = new HashMap<>();
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void put(String key,Object value) throws InterruptedException {
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\tput\t"+key);
            TimeUnit.MILLISECONDS.sleep(300);
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"\tput over");
        }finally {
            lock.writeLock().unlock();
        }
    }

    public void get(String key) throws InterruptedException {
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\tget");
            TimeUnit.MILLISECONDS.sleep(300);
            Object result = map.get(key);
            System.out.println(Thread.currentThread().getName()+"\tget over\t" +result);
        }finally {
            lock.readLock().unlock();
        }

    }
}
public class ReadWriteLockDemo {

    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(()->{
                try {
                    myCache.put(finalI+"",finalI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(()->{
                try {
                    myCache.get(finalI+"");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
    }
}
