package algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author pray chow
 * 给定两组大小都为 n 的点集，输出最短距离（欧氏距离）
 * INPUT :
 *      n 组测试数据
 *      数组大小为 k in [1, 100000]
 *      2*k 行坐标 x, y in [1, 1e9]
 * OUTPUT :
 *      最短距离，3位小数
 * IN :
 *      2
 *      4
 *      0 0 1 0 0 1 1 1
 *      2 2 3 2 2 3 3 3
 *      4
 *      0 0 0 0 0 0 0 0
 *      0 0 0 0 0 0 0 0
 * OUT :
 *      1.414
 *      0.000
 * 暴力，50%
 * 分治法见 ClosePairX.java
 */
public class Tencent_2 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        List<List<int[][]>> list = new ArrayList<>(n);
        for (int i = 0; i < n; ++i) {
            int k = scan.nextInt();
            List<int[][]> l = new ArrayList<>(2);
            int[][] A = new int[k][2];
            int[][] B = new int[k][2];
            for (int j = 0; j < k; ++j) {
                A[j][0] = scan.nextInt();
                A[j][1] = scan.nextInt();
            }
            for (int j = 0; j < k; ++j) {
                B[j][0] = scan.nextInt();
                B[j][1] = scan.nextInt();
            }
            l.add(A);
            l.add(B);
            list.add(l);
        }
        scan.close();
        for (int i = 0; i < n; ++i) {
            List<int[][]> l = list.get(i);
            int[][] A = l.get(0);
            int[][] B = l.get(1);
            long res = Long.MAX_VALUE;
            for (int j = 0; j < A.length; ++j) {
                for (int g = 0; g < B.length; ++g) {
                    long temp = (A[j][0] - B[g][0]) * (A[j][0] - B[g][0]) + (A[j][1] - B[g][1]) * (A[j][1] - B[g][1]);
                    if (temp < res) {
                        res = temp;
                    }
                    if (res == 0) {
                        j = A.length;
                        g = B.length;
                    }
                }
            }
            System.out.println(String.format("%.3f", Math.sqrt(res)));
        }
    }
}
