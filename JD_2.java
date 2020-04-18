package algorithm;

import java.util.Scanner;

/**
 * @author pray chow
 * 一堆区间代表上车时间和下车时间，找最少座位数，满足随意的购买顺序都有座位
 * 例如 A(1->2) B(2->3) C(1->3)
 * 按照 ABC 顺序需要 3 个座位，ACB 的话只需 2 个座位
 * IN :
 *      10
 *      84 302
 *      275 327
 *      364 538
 *      26 364
 *      29 386
 *      545 955
 *      715 965
 *      404 415
 *      903 942
 *      150 402
 * OUT :
 *      6
 * ps. 感觉这道题真的很迷。。。
 */
public class JD_2 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int[][] users = new int[n][2];
        for (int i = 0; i < n; ++i) {
            users[i][0] = scan.nextInt();
            users[i][1] = scan.nextInt();
        }
        scan.close();
        int max = 1;
        for (int i = 0; i < n; ++i) {
            int ans = 1;
            int start = users[i][0];
            int end = users[i][1];
            for (int j = 0; j < n; ++j) {
                if (j != i) {
                    if (!(end < users[j][0] || start > users[j][1])) {
                        ans++;
                    }
                }
            }
            max = Math.max(max, ans);
        }
        System.out.println(max);
    }
}
