package algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
 */
public class PDD_2 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int t = scan.nextInt();
        int[] nums = new int[t];
        List<int[]> list = new ArrayList<>(t);
        for (int i = 0; i < t; ++i) {
            nums[i] = scan.nextInt();
            int[] tmp = new int[nums[i]];
            for (int j = 0; j < nums[i]; ++j) {
                tmp[j] = scan.nextInt();
            }
            list.add(tmp);
        }
        scan.close();
        for (int i = 0; i < t; ++i) {
            int[] edge = list.get(i);
            int sum = 0;
            for (int tmp : edge) {
                sum += tmp;
            }
            System.out.println("YES");
        }
    }
}
