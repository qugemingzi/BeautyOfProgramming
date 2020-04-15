package algorithm;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author pray chow
 * n 个数，1-n，每次可选择一个将其置为最左边或最右边，问最少操作次数
 * INPUT :
 *      n       in [1, 1000000]
 *      n 个数  in [1, n]
 * OUTPUT :
 *      最少操作次数
 * IN :
 *      5
 *      4 1 2 5 3
 * OUT :
 *      2       4 -> 最右边，5 -> 最右边
 * idea :
 *      找最长加一上升序列长，最少操作数即为 length - 序列长
 *      上述算法 91% 超时（菜是原罪），时间复杂度为 O(n^2)
 *      所以，这么想，把原数组索引值和数值互换位置，则将原问题的求最长加一上升子序列转化为
 *      在新的数组中求最长上升子串，Orz。。。
 */
public class Inspur_1 {

    /**
     * 最长加一上升子序列，笨方法 O(n^2)，91%。。。
     */
    public static void method_1() {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; ++i) {
            arr[i] = scan.nextInt();
        }
        scan.close();
        boolean[] flag = new boolean[n];
        int maxlen = 1, count = 0;
        for (int i = 0; i < n; ++i) {
            if (!flag[i]) {
                int temp = arr[i];
                for (int j = i; j < n; ++j) {
                    if (arr[j] == temp) {
                        temp++;
                        count++;
                        flag[j] = true;
                    }
                }
                maxlen = count > maxlen ? count : maxlen;
                count = 0;
            }
        }
        System.out.println(n - maxlen);
    }

    /**
     * 改进为求最长上升子串，O(n)
     */
    public static  void method_2() {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int[] arr = new int[n];
        for (int i = 1; i <= n; ++i) {
            int temp = scan.nextInt();
            // 索引值与数组位置互换，等价于求最长递增子串
            arr[temp - 1] = i;
        }
        scan.close();
        int maxlen = 1;
        for (int i = 0; i < n; ) {
            int j, count = 0, temp = arr[i];
            for (j = i; j < n; ++j) {
                if (arr[j] >= temp) {
                    count++;
                    temp = arr[j];
                } else {
                    break;
                }
            }
            i = j;
            maxlen = Math.max(maxlen, count);
        }
        System.out.println(n - maxlen);
    }

    public static void main(String[] args) {
        method_1();
        method_2();
    }
}
