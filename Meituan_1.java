import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class Meituan_1 {

    static int[] KBig(int[] arr, int k){
        if(k <= 0){ return new int[0]; }
        if(arr.length <= k){ return arr; }
        Queue<Integer> queue = new PriorityQueue(k);
        for(int i = 0; i < k; ++i){
            queue.offer(arr[i]);
        }
        for(int i = k; i < arr.length; ++i){
            if(arr[i] > queue.peek()){
                queue.poll();
                queue.offer(arr[i]);
            }
        }
        int[] res = new int[k];
        for(int i = k-1; i >= 0; --i){
            res[i] = queue.poll();
        }
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
