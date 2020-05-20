package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author pray chow
 * 全排列 && LeetCode 46
 * 20200520 最强大脑一题
 * ZUI + QIANG = DA * NAO
 * Z+U+I+Q+I+A+N+G+D+A+N+A+O = AA
 * 字母 in [1, 9]，求一一映射为多少？
 */
public class Permute {
    private int n = 0;

    public void helper(List<Integer> list, List<List<Integer>> res, int index) {
        if (index == n) {
            res.add(new ArrayList<>(list));
        }
        for (int i = index; i < n; i++) {
            Collections.swap(list, index, i);
            helper(list, res, index + 1);
            Collections.swap(list, index, i);
        }
    }

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new LinkedList<>();
        List<Integer> list_first = new ArrayList<>();
        for (int num : nums) {
            list_first.add(num);
        }
        n = nums.length;
        helper(list_first, res, 0);
        return res;
    }

    public void cal(List<List<Integer>> res) {
        for (List<Integer> list : res) {
            int Z = list.get(0);
            int U = list.get(1);
            int I = list.get(2);
            int Q = list.get(3);
            int N = list.get(4);
            int G = list.get(5);
            int D = list.get(6);
            int O = list.get(7);
            // 若没有第二条限制，A = list.get(8)
            int A = 6;
            int left1 = 100*Z + 10*U + 1*I;
            int left2 = 10000*Q + 1000*I + 100*A + 10*N + 1*G;
            int right1 = 10*D + 1*A;
            int right2 = 100*N + 10*A + 1*O;
            if (left1 + left2 == right1 * right2) {
                System.out.println(left1 + " + " + left2 + " = " + right1 + " * " + right2);
            }
        }
    }

    public static void main(String[] args) {
        Permute p = new Permute();
        // A = 6
        int[] nums = {1, 2, 3, 4, 5, 7, 8, 9};
        p.cal(p.permute(nums));
    }
}
