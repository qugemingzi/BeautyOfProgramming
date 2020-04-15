package algorithm;

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
 *      91% 还没搞懂哪里超时了。。。菜是原罪。。。
 */
public class Inspur_1 {
    public static void main(String[] args) {
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
}
