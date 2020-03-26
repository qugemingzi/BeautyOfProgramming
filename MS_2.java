/**
 * @author pray chow
 * MicroSoft_2 && LeetCode 1246 删除最少次数的回文子串清空整个数组
 */
public class MS_2 {

    /**
     * idea : 动规 dp[i][j] 表示从 i 到 j 最少的操作数
     * dp[i][j] = 1 + dp[i+1][j]            remove arr[i] singly
     *            dp[i][k-1] + dp[k+1][j]   for all k if arr[i] == arr[k]
     * @param arr 字符数组
     * @return 最少操作数
     */
    static int minRemoves(int[] arr){
        int n = arr.length;
        if(n == 0) { return 0; }
        int[][] dp = new int[n][n];
        dp[0][0] = 1;
        for(int i = 1; i < n; ++i) {
            dp[i][i] = 1;
            dp[i-1][i] = arr[i-1] == arr[i] ? 1 : 2;
        }
        for(int len = 3; len <= n; ++len) {
            for(int start = 0; start + len - 1 < n; ++start) {
                int end = start + len - 1;
                dp[start][end] = Integer.MAX_VALUE;
                for(int i = start; i <= end; ++i) {
                    if(arr[i] == arr[start]) {
                        dp[start][end] = Math.min(dp[start][end],
                                (start + 1 <= i - 1 ? dp[start+1][i-1] : 1) + (i < end ? dp[i+1][end] : 0));
                    }
                }
            }
        }
        return dp[0][n-1];
    }

    public static void main(String[] args){
        System.out.println(minRemoves(new int[]{1, 4, 1, 3, 5}));
    }
}
