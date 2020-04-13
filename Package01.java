package algorithm;

/**
 * @author pray chow
 * 0-1背包
 * 给定一个可装重量 W 的背包和 N 个物品，每个物品有重量和价值两个属性。第 i 个
 * 物品的重量为 wt[i]，价值为 val[i]，用该背包装物品，最多能装的价值为？
 * N = 3, W = 4
 * wt = [2, 1, 2]
 * val = [4, 2, 1]
 * 算法返回 6
 * idea :
 *      dp[i][w]表示前 i 个物品，重量为 w 情况下最大价值
 *      dp[i][w] = max { dp[i - 1][w],
 *                       dp[i - 1][w - wt[i - 1]] + val[i - 1] }
 *      dp[0][...] = dp[...][0] = 0
 */
public class Package01 {

    public int knapsack(int W, int N, int[] wt, int[] val) {
        int[][] dp = new int[N + 1][W + 1];
        for (int i = 1; i <= N; ++i) {
            for (int j = 1; j <= W; ++j) {
                dp[i][j] = j < wt[i - 1] ? dp[i - 1][j] :
                        Math.max(dp[i - 1][j], dp[i - 1][j - wt[i - 1]] + val[i - 1]);
            }
        }
        return dp[N][W];
    }

    public static void main(String[] args) {
        int N = 3, W = 4;
        int[] wt = {2, 1, 2};
        int[] val = {4, 2, 1};
        Package01 package01 = new Package01();
        System.out.println(package01.knapsack(W, N, wt, val));
    }
}
