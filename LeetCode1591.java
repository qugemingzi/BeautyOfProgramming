package algorithm;

import java.util.LinkedList;
import java.util.List;

/**
 * @author pray chow
 */
public class LeetCode1591 {
    /**
     * 半暴力求解，从后往前计算，将数存放为一个有序降序链表，
     * 二分查找插入位置，累加求逆序和，时间 O(nlogn)，超时...
     * @param nums 输入数组
     * @return 逆序对数
     */
    public int reversePairs_1(int[] nums) {
        int n = nums.length;
        if (n <= 1) {
            return 0;
        }
        List<Integer> list = new LinkedList<>();
        int res = 0;
        for (int i = n - 1; i >= 0; --i) {
            if (list.size() == 0) {
                list.add(nums[i]);
            } else {
                if (nums[i] > list.get(0)) {
                    res += list.size();
                    list.add(0, nums[i]);
                } else if (nums[i] <= list.get(list.size() - 1)) {
                    list.add(nums[i]);
                } else {
                    int left = 0, right = list.size() - 1;
                    int middle = (list.size() - 1) / 2;
                    while (left < right) {
                        if (nums[i] > list.get(middle)) {
                            right = middle;
                        } else {
                            left = middle + 1;
                        }
                        middle = (left + right) / 2;
                    }
                    res += list.size() - left;
                    list.add(left, nums[i]);
                }
            }
        }
        return res;
    }

    /**
     * 归并排序计算逆序对
     * @param nums 输入数组
     * @return 逆序对数
     */
    public int reversePairs_2(int[] nums) {
        int n = nums.length;
        if (n <= 1) {
            return 0;
        }
        int[] temp = new int[n];
        return reversePairs_2(nums, 0, n - 1, temp);
    }

    /**
     * nums[left...right] 计算逆序对个数并且排序
     * @param nums 输入数组
     * @param left 左开始
     * @param right 右结束
     * @param temp 之间数组
     * @return nums[left...right] 逆序对数
     */
    public int reversePairs_2(int[] nums, int left, int right, int[] temp) {
        if (left == right) {
            return 0;
        }
        int mid = (right + left) / 2;
        int leftPairs = reversePairs_2(nums, left, mid, temp);
        int rightPairs = reversePairs_2(nums, mid + 1, right, temp);

        if (nums[mid] <= nums[mid + 1]) {
            return leftPairs + rightPairs;
        }
        int crossPairs = mergeAndCount(nums, left, mid, right, temp);
        return leftPairs + rightPairs + crossPairs;
    }

    /**
     * nums[left...mid] 有序，nums[mid + 1...right] 有序
     * 合并之且求交叉逆序对数
     * @param nums 输入数组
     * @param left 左开始
     * @param mid 中间分界
     * @param right 右结束
     * @param temp 中间数组
     * @return 交叉逆序对数
     */
    public int mergeAndCount(int[] nums, int left, int mid, int right, int[] temp) {
        for (int i = left; i <= right; ++i) {
            temp[i] = nums[i];
        }
        int i = left, j = mid + 1;
        int count = 0;
        for (int k = left; k <= right; ++k) {
            if (i == mid + 1) {
                nums[k] = temp[j++];
            } else if (j == right + 1) {
                nums[k] = temp[i++];
            } else if (temp[i] <= temp[j]) {
                nums[k] = temp[i++];
            } else {
                nums[k] = temp[j++];
                count += (mid - i + 1);
            }
        }
        return count;
    }

    public static void main(String[] args) {
        LeetCode1591 lc = new LeetCode1591();
        int[] nums = new int[]{7,5,6,4};
        System.out.println(lc.reversePairs_1(nums));
        System.out.println(lc.reversePairs_2(nums));
    }
}
