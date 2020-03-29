package algorithm;

import javax.print.DocFlavor;
import java.util.Scanner;

/**
 * @author pray chow
 * Baidu_2
 * n 个数，每次从 a[i] 中取出一个最大的减去 n，其他的 n-1 个数加 1，一直重复到最大的
 * a[i] < n，执行次数记为 k。最少多少次操作使得 n 个数都小于 n 呢？
 * INPUT :
 *      第一行 n in [2,50]
 *      第二行 n 个数表示 a[i] in [1,1e18]
 * OUTPUT :
 *      最少操作数 k
 * INPUT :
 *      3
 *      1 0 3
 * OUTPUT :
 *      1
 * idea :
 *      每次搜索至少需要的操作次数，即所有大于等于 n 的都需要操作，每个的操作次数 a[i]/n，
 *      一共操作次数 sum{a[i]/n}，a[i] 剩余 (a[i]%n + count - a[i]/n)，之后在查找是否
 *      满足条件，直至所有的都满足。
 */
public class Baidu_2 {

    /**
     * 计算最少操作次数
     * @param n 数组大小 int
     * @param arr 数组 long
     * @return 最少操作次数
     */
    public static long minDel(int n, long[] arr) {
        long res = 0, count = 0;
        for(int i = 0; i < n; ++i) {
            count += arr[i] / n;
        }
        while(count > 0) {
            long cur = 0;
            res += count;
            for(int i = 0; i < n; ++i) {
                arr[i] = (arr[i] % n) + count - (arr[i] / n);
                cur += arr[i] / n;
            }
            count = cur;
        }
        return res;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        long[] arr = new long[n];
        for(int i = 0; i < n; ++i) {
            arr[i] = scan.nextLong();
        }
        System.out.println(minDel(n, arr));
        scan.close();
    }
}
