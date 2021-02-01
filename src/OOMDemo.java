import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author qy
 * @version 1.0
 * @date 2021/1/31 17:26
 */
public class OOMDemo {
    public void testHeap(){
        for(;;){
            ArrayList list = new ArrayList (2000);
        }
    }
    int num=1;
    public void testStack(){
        num++;
        this.testStack();
    }

    public static void main(String[] args){
        OOMDemo  t  = new OOMDemo ();
        t.testHeap();
        t.testStack();
        
    }

}
