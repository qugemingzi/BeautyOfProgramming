package algorithm;

import java.util.Scanner;

/**
 * @author pray chow
 * Fibonacci 数列，给定前两个数，求下标为 n 的数能否整除 3？
 * INPUT :
 *      t 测试样例      in [1, 100]
 *      a b n 第0、1项  in [1, 1e9]
 * OUTPUT :
 *      "YES" OR "NO"
 * IN :
 *      3
 *      1 1 3
 *      1 1 4
 *      7 11 2
 * OUT :
 *      YES
 *      NO
 *      YES
 * 100%。找规律：发现通过 a 和 b 模 3 的值，发现数组模 3 周期为 8。且判断是否整除 3 的周期为 4。
 * 下为从 a b 开始计算模 3 的值
 *  a b
 *  0 0 0 ...
 *  0 1 1 2 0 2 2 1     0 1 1 ...
 *  0 2 2 1 0 1 1 2     0 2 2 ...
 *  1 0 1 1 2 0 2 2     1 0 1 ...
 *  2 0 2 2 1 0 1 1     2 0 2 ...
 *  1 1 2 0 2 2 1 0     1 1 2 ...
 *  2 2 1 0 1 1 2 0     2 2 1 ...
 *  1 2 0 2 2 1 0 1     1 2 0 ...
 *  2 1 0 1 1 2 0 2     2 1 0 ...
 */
public class PDD_3 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int t = scan.nextInt();
        int[][] arr = new int[t][3];
        for (int i = 0; i < t; ++i) {
            arr[i][0] = scan.nextInt();
            arr[i][1] = scan.nextInt();
            arr[i][2] = scan.nextInt();
        }
        scan.close();
        for (int i = 0; i < t; ++i) {
            int a = arr[i][0];
            int b = arr[i][1];
            int n = arr[i][2];
            if (a % 3 == 0 && b % 3 == 0) {
                System.out.println("YES");
            } else if (a % 3 == 0) {
                System.out.println(n % 4 == 0 ? "YES" : "NO");
            } else if (b % 3 == 0) {
                System.out.println(n % 4 == 1 ? "YES" : "NO");
            } else if (a % 3 == b % 3) {
                System.out.println(n % 4 == 3 ? "YES" : "NO");
            } else {
                System.out.println(n % 4 == 2 ? "YES" : "NO");
            }
        }
    }
}
