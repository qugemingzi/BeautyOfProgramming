import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class Meituan_1 {
    /*
        扎银花
        每人一堆牌，每人挑选最大的三张牌比较和，输出最大的和
        INPUT :
            n 双方掌握的牌的数量, [1, 20000]
            两行，每行有 n 个数字，分别代表双方可选的 n 张牌, [0, 1e9]
        example :
        IN : 5
             1 2 3 4 5
             1 2 3 4 6
        OUT: 13
        idea :
            最大的 k 个数 (参考 BiggestK.java 《编程之美》)
            分别比较即可，注意结果类型，3 个 1e9 会超 int 范围。
     */

    // 采用最小堆实现
    static int[] KBig(int[] arr, int k){
        if(k <= 0){ return new int[0]; }
        if(arr.length <= k){ return arr; }
        Queue<Integer> queue = new PriorityQueue(k); // 默认最小堆
//        Queue<Integer> maxqueue = new PriorityQueue<>(new Comparator<Integer>() {
//            @Override
//            public int compare(Integer i1, Integer i2) {
//                return i2 - i1;
//            }
//        }); // 构造最大堆
        for(int i = 0; i < k; ++i)
            queue.offer(arr[i]);
        for(int i = k; i < arr.length; ++i){
            if(arr[i] > queue.peek()){
                queue.poll();
                queue.offer(arr[i]);
            }
        }
        int[] res = new int[k];
        for(int i = k-1; i >= 0; --i)
            res[i] = queue.poll();
        return res;
    }

    public static void main(String args[]){
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int[] a = new int[n];
        int[] b = new int[n];
        for(int i = 0; i < n; ++i){
            a[i] = scan.nextInt();
        }
        for(int i = 0; i < n; ++i){
            b[i] = scan.nextInt();
        }
        // 结果可能是 long 类型
        long a_res = 0, b_res = 0;
        for(int temp : KBig(a, 3)){
            a_res += temp;
        }
        for(int temp : KBig(b, 3)){
            b_res += temp;
        }
        if(a_res > b_res)
            System.out.println(a_res);
        else
            System.out.println(b_res);
    }
}
