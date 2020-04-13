package algorithm;

import java.util.Arrays;

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
 *      上述空间复杂度为 O(N*sum)，压缩空间为 O(sum)
 */
public class Package01X {
    /**
     * dp解决等分数组
     * @param nums 数组
     * @return 能否等分
     */
    boolean canPartition(int[] nums) {
        int sum = 0, n = nums.length;
        for (int num : nums) {
            sum += num;
        }
        if ((sum & 1) != 0) {
            return false;
        }
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

    /**
     * 压缩二维数组为一维，需注意 j 要从 sum -> 0 避免元素重复利用
     * @param nums 数组
     * @return 能否等分
     */
    boolean canPartition_2(int[] nums) {
        int sum = 0, n = nums.length;
        for (int num : nums) {
            sum += num;
        }
        if ((sum & 1) != 0) {
            return false;
        }
        sum /= 2;
        boolean[] dp = new boolean[sum + 1];
        dp[0] = true;
        for (int i = 0; i < n; ++i) {
            for (int j = sum; j >= 0; --j) {
                if (j >= nums[i]) {
                    dp[j] = dp[j] || dp[j - nums[i]];
                }
            }
        }

        return dp[sum];
    }

    /**
     * 递归实现
     * @param nums 数组
     * @return 能否等分
     */
    public boolean canPartition_3(int[] nums) {
        int sum = 0, n = nums.length;
        for (int num : nums) {
            sum += num;
        }
        if ((sum & 1) != 0) {
            return false;
        }
        sum /= 2;
        Arrays.sort(nums);
        return dfs(0, 0, nums, sum, n - 1);
    }

    /**
     * 递归函数
     * @param cur1 分组一
     * @param cur2 分组二
     * @param nums 数组
     * @param target 目标数
     * @param index 数组下标
     * @return 能否等分
     */
    private boolean dfs(int cur1, int cur2, int[] nums, int target, int index) {
        if (cur1 > target || cur2 > target || index == -1) {
            return false;
        }
        if (cur1 == target || cur2 == target) {
            return true;
        }
        return dfs(cur1 + nums[index], cur2, nums, target, index - 1) ||
                dfs(cur1, cur2 + nums[index], nums, target, index - 1);
    }

    public static void main(String[] args) {
        Package01X package01X = new Package01X();
        int[] nums1 = {1, 5, 11, 5};
        int[] nums2 = {1, 2, 3, 8};
        System.out.println(package01X.canPartition(nums1) + " " + package01X.canPartition_2(nums1) + " " + package01X.canPartition_3(nums1));
        System.out.println(package01X.canPartition(nums2) + " " + package01X.canPartition_2(nums2) + " " + package01X.canPartition_3(nums2));
    }
}
