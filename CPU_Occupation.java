import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CPU_Occupation {
    public static void main(String[] args) throws InterruptedException{
//        occup_1();// 解法1，有点问题
//        occup_2();// 解法2，单核可以，多核不可以
        Runtime r = Runtime.getRuntime();//获得当前系统的CPU数量，根据这个数值创建对应数量的线程
        ExecutorService pool = Executors.newFixedThreadPool(r.availableProcessors());
        for(int i = 0; i < r.availableProcessors(); i++){
//            pool.execute(new occup_3());// 解法3，多核可以
//            pool.execute(new occup_4());// 解法4，多核下，正弦曲线
        }
        pool.shutdown();
    }

    static void occup_1() throws InterruptedException{
        while(true){
            for(int i = 0; i < 14400000; i++)
                ;
            Thread.sleep(10);
        }
    }

    static void occup_2() throws InterruptedException{
        int busyTime = 50;
        int idleTime = busyTime;

        while(true){
            long startTime = System.currentTimeMillis();
            while((System.currentTimeMillis() - startTime) <= busyTime)
                ;
            Thread.sleep(idleTime);
        }
    }

}

class occup_3 implements Runnable{
    @Override
    public void run(){
        int busyTime = 100;
        int idleTime = busyTime;

        while(true){
            long startTime = System.currentTimeMillis();
            while((System.currentTimeMillis() - startTime) <= busyTime)
                ;
            try{
                Thread.sleep(idleTime);
            }catch(InterruptedException e){
                e.printStackTrace();
            }

        }
    }
}

class occup_4 implements Runnable{
    @Override
    public void run(){
        //角度的分割
        final double SPILT = 0.01;
        //2PI分割的次数，也就是2/0.01个，正好一周
        final int COUNT = (int) (2/SPILT);
        final double PI = Math.PI;
        //时间间隔
        final int INTERVAL = 200;
        long[] busySpan = new long[COUNT];
        long[] idleSpan = new long[COUNT];
        int half = INTERVAL / 2;
        double radian = 0.0;
        /*
        对busySpan和idleSpan数组赋值
        busySpan：100 102 104 ... 200 198 196 ... 100  98  96 ...   0   2   4 ... 100 循环
        idleSpan: 100  98  96 ...   0   2   4 ... 100 102 104 ... 200 198 196 ... 100 循环
        对于CPU，每次先工作busySpan[i]ms，再休眠idleSpan[i]ms。
        */
        for(int i = 0; i < COUNT; i++){
            busySpan[i] = (long) (half + (Math.sin(PI * radian) * half));
            idleSpan[i] = INTERVAL - busySpan[i];
            radian += SPILT;
        }

        long startTime = 0;
        int j = 0;
        while(true){
            j = j % COUNT;
            startTime = System.currentTimeMillis();
            while((System.currentTimeMillis() - startTime) <= busySpan[j])
                ;
            try{
                Thread.sleep(idleSpan[j]);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            j++;
        }
    }
}
