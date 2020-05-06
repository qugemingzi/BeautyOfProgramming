package algorithm;

import java.util.*;

/**
 * @author pray chow
 * LeetCode 473
 * 给定一个数组，问能否拼成正方形，即能否将数组等分成四等份
 * INPUT :
 *      t 测试样例个数   in [1, 100]
 *      n 数组长度       in [1, 20]
 *      n 个数 Li        in [1, 1e4]
 * OUTPUT :
 *      "YES" OR "NO"
 * IN :
 *      3
 *      4 1 1 1 1
 *      5 1 1 1 1 2
 *      8 1 1 1 1 2 2 4 4
 * OUT :
 *      YES
 *      NO
 *      YES
 * 不会做，应该是用 dp 来做，先想想二等分怎么做再来补坑
 * dfs思想，目标把数放到四个桶中，且值都为 1/4。
 * 倒序排序，方便先把大的先分配，小的后分配，LeetCode上跑的速度 31ms V.S. 1460ms。
 */
public class PDD_2 {

    static boolean dfs(int index, Integer[] nums, int[] bucket, int target) {
        if (index >= nums.length) {
            return bucket[0] == target && bucket[1] == target
                    && bucket[2] == target && bucket[3] == target;
        }
        for (int i = 0; i < 4; ++i) {
            if (bucket[i] + nums[index] > target) {
                continue;
            }
            bucket[i] += nums[index];
            if (dfs(index + 1, nums, bucket, target)) {
                return true;
            }
            bucket[i] -= nums[index];
        }
        return false;
    }

    static boolean makeSquare(int[] nums) {
        if (nums.length < 4) {
            return false;
        }
        int sum = 0;
        for (int temp : nums) {
            sum += temp;
        }
        if (sum % 4 != 0) {
            return false;
        }
        Integer[] newNums = new Integer[nums.length];
        for (int i = 0; i < nums.length; ++i) {
            newNums[i] = nums[i];
        }
        Arrays.sort(newNums, (i1, i2) -> i2 - i1);
        int[] bucket = new int[4];
        return dfs(0, newNums, bucket, sum / 4);
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int t = scan.nextInt();
        int[] arr = new int[t];
        List<int[]> list = new ArrayList<>(t);
        for (int i = 0; i < t; ++i) {
            arr[i] = scan.nextInt();
            int[] tmp = new int[arr[i]];
            for (int j = 0; j < arr[i]; ++j) {
                tmp[j] = scan.nextInt();
            }
            list.add(tmp);
        }
        scan.close();
        for (int i = 0; i < t; ++i) {
            int[] nums = list.get(i);
            if (makeSquare(nums)) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }
    }
}
