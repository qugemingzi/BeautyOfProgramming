package algorithm;

/**
 * @author pray chow
 * LeetCode 1269
 * 有一个长度为 arrLen 的数组，开始有一个指针在索引 0 处。
 * 每一步操作中，你可以将指针向左或向右移动 1 步，或者停在原地（指针不能被移动到数组范围外）。
 * 给你两个整数 steps 和 arrLen ，请你计算并返回：在恰好执行 steps 次操作以后，指针仍然指向索引 0 处的方案数。
 * 由于答案可能会很大，请返回方案数 模 10^9 + 7 后的结果。
 */
public class LeetCode1269 {
    private int MOD = 1000000007;

    /**
     * dp[i][j] 表示还剩 i 步到位置 j 的走法
     * @param steps 走的步数
     * @param arrLen 数组长度
     * @return 一共可能走法 dp[i][j]=dp[i-1][j]+dp[i-1][j-1]+dp[i-1][j+1]
     */
    public int numWays(int steps, int arrLen) {
        arrLen = Math.min(arrLen, steps + 1);
        int[][] dp = new int[steps + 1][arrLen];
        dp[0][0] = 1;
        for(int i = 1; i <= steps; ++i) {
            for(int j = 0; j < arrLen; ++j) {
                if(j - 1 >= 0) { dp[i][j] = (dp[i][j] + dp[i - 1][j - 1]) % MOD; }
                if(j + 1 < arrLen) { dp[i][j] = (dp[i][j] + dp[i - 1][j + 1]) % MOD;}
                dp[i][j] = (dp[i][j] + dp[i - 1][j]) % MOD;
            }
        }
        return dp[steps][0];
    }

    /**
     * 上述算法计算第 i 层只需求第 i-1 层的三个数，压缩空间复杂度为 f[] 和 g[]
     * @param steps 走的步数
     * @param arrLen 数组长度
     * @return 一共可能走法 g[j]=f[j]+f[j-1]+f[j+1]
     */
    public int numWays_2(int steps, int arrLen) {
        arrLen = Math.min(arrLen, steps + 1);
        int[] f = new int[arrLen];
        f[0] = 1;
        for(int i = 1; i <= steps; ++i) {
            int[] g = new int[arrLen];
            for(int j = 0; j < arrLen; ++j) {
                if(j - 1 >= 0) { g[j] = (g[j] + f[j - 1]) % MOD; }
                if(j + 1 < arrLen) { g[j] = (g[j] + f[j + 1]) % MOD;}
                g[j] = (g[j] + f[j]) % MOD;
            }
            f = g;
        }
        return f[0];
    }
}
