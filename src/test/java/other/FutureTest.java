package other;
 
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang.time.StopWatch;
 
/**
 * 试验 Java 的 Future 用法
 */
public class FutureTest {
 
    //Callable和Future，一个产生结果，一个拿到结果
    public static class Task implements Callable<Long> {
        @Override
        public Long call() throws Exception {
            long tid = Thread.currentThread().getId();
//            int sleepInt = new Random().nextInt(5);
            int sleepInt = 2;
            TimeUnit.SECONDS.sleep(sleepInt);
            System.out.printf("sleepInt[%d] Thread#%s : in call\n", sleepInt, tid);
            return tid;
        }
    }
 
    
    /*
     * Executors工厂类:
     *  1. newCachedThreadPool 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
        2. newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
        3. newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
        4. newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
        
        1.CompletionService.take 会获取并清除已经完成Task的结果，如果当前没有已经完成Task时，会阻塞。
        2.“先创建一个装Future类型的集合，用Executor提交的任务返回值添加到集合中，最后遍历集合取出数据” -- 这种方法通常是按照Future加入的顺序。
                两个方法最大的差别在于遍历 Future 的顺序，相对来说， CompletionService 的性能更高。
                考虑如下场景：多线程下载，结果用Future返回。第一个文件特别大，后面的文件很小。用方法1，能很快知道已经下载完文件的结果(不是第一个)；
                                                                                                                                                                    而用方法2，必须等第一个文件下载结束后，才会获得其他文件的下载结果。
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        StopWatch sw = new StopWatch();
        sw.start();
        
        List<Future<Long>> results = new ArrayList<Future<Long>>();
//        ExecutorService es = Executors.newCachedThreadPool();
//        ThreadPoolExecutor es = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        ExecutorService es = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        for(int i=0; i<10;i++){
            results.add(es.submit(new Task()));
        }
 
//        System.out.println("before res.get(): "+sw.getTime());
//        for(Future<Long> res : results){
//        TimeUnit.SECONDS.sleep(2);
//            System.out.println(res.get());/** future.get()会产生阻塞*/
//        }
        
        es.submit(new Runnable() {
            @Override
            public void run()
            {
                System.out.println("es.submit(new Runnable() time: "+sw.getTime());
                for(Future<Long> res : results){
                    try
                    {
                        TimeUnit.SECONDS.sleep(2);
//                        System.out.println("es.submit(new Runnable() time: "+sw.getTime());
                        System.out.println(res.get());/** future.get()会产生阻塞*/
                    }
                    catch (InterruptedException | ExecutionException e)
                    {
                        e.printStackTrace();
                    }
                }                
            }
        });
        
        //shutdown()方法之后，ExecutorService不会立即关闭，但是它不再接收新的任务，直到当前所有线程执行完成才会关闭
        es.shutdown();
        
        System.out.println("es.shutdown, total time: "+sw.getTime());
        
        TimeUnit.SECONDS.sleep(10);
        System.out.println("System.exit(0) time: "+sw.getTime());
        System.exit(0);
        
        sw.stop();
    }
 
}