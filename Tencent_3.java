package algorithm;

import java.util.Scanner;

/**
 * @author pray chow
 * n 张卡牌，第 i 张正面为 ai, 反面为 bi, 排成一行，初始正面朝上，执行若干次操作
 * 每次操作可选择相邻的两张牌，先交换顺序再翻转它们，求最少操作次数似的牌非降序
 * INPUT :
 *      n in [1, 18]
 *      n 个数字表示 ai
 *      n 个数字表示 bi
 * OUTPUT :
 *      输出答案，无解输出 -1
 * IN :
 *      3
 *      1 3 2
 *      3 2 1
 * OUT :
 *      1
 * 状态压缩 dp，设最后的方案为 At1, At2,...,Atn。容易得知第 i 张卡片正面朝上
 * 当且仅当 2|i-ti，即原来的下标与最后结果的下边差为偶数，反之则为反面。
 * dp[i][k] 表示 i 这个集合中的卡片已经选好了位置，目前最后一个数下标为 k，
 * 枚举当前填的为 p，可知 p 卡片朝上的数字为 j。
 *      dp[i][j] = min(dp[i - {p}][k] + num)    k <= j
 * num 为 p 放下后产生的逆序对个数。
 * 初始 dp 都为 Integer.MAX_VALUE，只有一个元素的 dp[1<<i][i] = 0。
 * 所求即为 min{dp[1<<n - 1][x], x in [0, n-1]}
 */
public class Tencent_3 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int[] a = new int[n];
        int[] b = new int[n];
        int[][] dp = new int[1<<n][n];
        for (int i = 0; i < n; ++i) {
            a[i] = scan.nextInt();
        }
        for (int i = 0; i < n; ++i) {
            b[i] = scan.nextInt();
        }
        scan.close();
        // 将偶数下标交换 a[i] 与 b[i]，方便判断
        for (int i = 1; i < n; i += 2) {
            int tmp = a[i];
            a[i] = b[i];
            b[i] = tmp;
        }
        for (int i = 0; i < (1<<n); ++i) {
            for (int j = 0; j < n; ++j) {
                dp[i][j] = Integer.MAX_VALUE;
            }
        }
        for (int i = 0; i < n; ++i) {
            dp[1<<i][i] = 0;
        }
        for (int i = 0; i < (1<<n); ++i) {
            for (int j = 0; j < n; ++j) {
                if (dp[i][j] == Integer.MAX_VALUE) {
                    continue;
                }
                for (int k = 0; k < n; ++k) {
                    // k 已经存在
                    if (((i>>k) & 1) == 1) {
                        continue;
                    }
                    // 求 i 二进制表示中有多少 1
                    int tmp = i, cnt = 0;
                    while (tmp != 0) {
                        tmp &= (tmp - 1);
                        cnt++;
                    }
                    int x = cnt & 1;
                    // 奇数偶数不同判断，剪枝
                    if (x == 1) {
                        if (b[k] < a[j]) {
                            continue;
                        }
                    } else {
                        if (a[k] < b[j]) {
                            continue;
                        }
                    }
                    // 插入 k 后造成的多的逆序对，就是操作次数
                    int ans = 0;
                    for (int l = k + 1; l < n; ++l) {
                        if (((i>>l) & 1) == 1) {
                            ans++;
                        }
                    }
                    dp[i|1<<k][k] = Math.min(dp[i|1<<k][k], dp[i][j] + ans);
                }
            }
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < n; ++i) {
            ans = Math.min(ans, dp[(1<<n) - 1][i]);
        }
        if (ans == Integer.MAX_VALUE) {
            System.out.println(-1);
        } else {
            System.out.println(ans);
        }
    }
}
