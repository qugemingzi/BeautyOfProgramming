package algorithm;

import java.util.Scanner;

/**
 * @author pray chow
 * 数组中所有连续区间中，gcd 与区间大小乘积最大值？
 * INPUT :
 *      n 数组大小      in [1, 1e5]
 *      Ai 数组         in [1, 1e9]
 * OUTPUT :
 *      最大值
 * IN :
 *      5               5
 *      3 6 2 2 2       6 3 2 2 2
 * OUT :
 *      8   [6,2,2,2] 2*4=8
 *      6   一种方案 [6,3] 3*2=6
 * 暴力，50%
 */
public class PDD_4 {

    static int gcd(int x, int y) {
        return (y == 0) ? x : gcd(y, x % y);
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; ++i) {
            arr[i] = scan.nextInt();
        }
        scan.close();
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; ++i) {
            dp[i][i] = arr[i];
        }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j + i + 1 < n; ++j) {
                dp[j][j + i + 1] = gcd(dp[j][j + i], dp[j + 1][j + i + 1]);
            }
        }
        long res = 0;
        for (int i = 0; i < n; ++i) {
            int max = 0;
            for (int j = 0; j + i < n; ++j) {
                max = Math.max(max, dp[j][j + i]);
            }
            long tmp = (long) max * (i + 1);
            res = Math.max(res, tmp);
        }
        System.out.println(res);
    }
}
