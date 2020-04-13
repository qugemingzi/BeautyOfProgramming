package algorithm;

/**
 * @author pray chow
 * 0-1背包变体 LeetCode 416
 * 分割等和子集
 * 给定一个只包含正整数的非空数组，是否可以将数组分隔成两个元素和相等的子集
 * INPUT :
 *      [1, 5, 11, 5]
 *      [1, 2, 3, 5]
 * OUTPUT :
 *      true    [1, 5, 5] [11]
 *      false
 * idea :
 *      转化为能否找到一些元素和为 sum/2，其中 sum 为数组元素总和，0-1背包
 *      dp[i][j] 表示前 i 个元素能否组成 j 这个数
 *      dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i - 1]]
 *      dp[0][...] = false, dp[...][0] = true
 *      目标即为求 dp[N][sum/2]
 */
public class Package01X {

    boolean canPartition(int[] nums) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if ((sum & 1) != 0) {
            return false;
        }
        int n = nums.length;
        sum /= 2;
        boolean[][] dp = new boolean[n + 1][sum + 1];
        for (int i = 0; i <= n; ++i) {
            dp[i][0] = true;
        }
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= sum; ++j) {
                dp[i][j] = j < nums[i - 1] ? dp[i - 1][j] :
                        dp[i - 1][j] || dp[i - 1][j - nums[i - 1]];
            }
        }

        return dp[n][sum];
    }

    public static void main(String[] args) {
        Package01X package01X = new Package01X();
        int[] nums1 = {1, 5, 11, 5};
        int[] nums2 = {1, 2, 3, 8};
        System.out.println(package01X.canPartition(nums1));
        System.out.println(package01X.canPartition(nums2));
    }
}
