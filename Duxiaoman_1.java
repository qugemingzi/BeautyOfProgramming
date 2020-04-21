package algorithm;

import java.util.Scanner;

/**
 * @author pray chow
 * 3*3极大值池化：原矩阵为 n*m 的，枚举所有的 3*3 子矩阵，求最大值拼接而成，处理后的矩阵大小为 (n-2)*(m-2)
 * 例如原矩阵为
 *      1  2  3  4
 *      5  6  7  8
 *      9 10 11 12
 * 3*3 池化后为 [[11, 12]]
 * 问题为原矩阵 n*m，池化窗口 a*b。原矩阵权值为 h(i,j)=i*j mod 10 (i in [1, n], j in [1, m])
 * 输出经过池化后矩阵元素和
 * INPUT :
 *      n m a b      all in [1, 1000]
 * OUTPUT :
 *      元素和
 * IN :
 *      4 5 3 3
 * OUT :
 *      54
 * explanation :
 * 原矩阵：
 *      1 2 3 4 5
 *      2 4 6 8 0
 *      3 6 9 2 5
 *      4 8 2 6 0
 * 池化后矩阵：
 *      9 9 9
 *      9 9 9
 * 元素和为 54
 * idea 1:
 *      暴力 ＋ max=9 时剪枝
 * idea 2:
 *      滑动窗口(dalao)，单调队列
 */
public class Duxiaoman_1 {

    public static int cal(int[][] matrix, int x, int y, int a, int b) {
        int max = 0;
        for (int i = 0; i < a; ++i) {
            for (int j = 0; j < b; ++j) {
                max = Math.max(max, matrix[x + i][y + j]);
                if (max == 9) {
                    return max;
                }
            }
        }
        return max;
    }

    public static int method_1(int n, int m, int a, int b) {
        int[][] matrix = new int[n + 1][m + 1];
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= m; ++j) {
                matrix[i][j] = (matrix[i][j - 1] + i) % 10;
            }
        }
        int res = 0;
        for (int i = 1; i <= n - a + 1; ++i) {
            for (int j = 1; j <= m - b + 1; ++j) {
                res += cal(matrix, i, j, a, b);
            }
        }
        return res;
    }

    public static int method_2(int n, int m, int a, int b) {
        int res = 0;
        int[][] hang = new int[n + 1][m + 1];
        int[] queue = new int[1000 + 10];
        int front, tail;
        for (int i = 1; i <= n; ++i) {
            front = tail = 0;
            for (int j = 1; j <= m; ++j) {
                while (front < tail && (i * queue[tail - 1]) % 10 <= (i * j) % 10) {
                    tail--;
                }
                queue[tail++] = j;
                while (front < tail && j - queue[front] + 1 > b) {
                    front++;
                }
                hang[i][j] = (i * queue[front]) % 10;
            }
        }
        for (int j = b; j <= m; ++j) {
            front = tail = 0;
            for (int i = 1; i <= n; ++i) {
                while (front < tail && hang[queue[front]][j] <= hang[i][j]) {
                    tail--;
                }
                queue[tail++] = i;
                while (front < tail && i - queue[front] + 1 > a) {
                    front++;
                }
                if (i >= a) {
                    res += hang[queue[front]][j];
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int m = scan.nextInt();
        int a = scan.nextInt();
        int b = scan.nextInt();
        scan.close();
        long start1 = System.currentTimeMillis();
        System.out.println(method_1(n, m, a, b));
        long end1 = System.currentTimeMillis();
        System.out.println("Time of method_1 : " + (end1 - start1));
        start1 = System.currentTimeMillis();
        System.out.println(method_2(n, m, a, b));
        end1 = System.currentTimeMillis();
        System.out.println("Time of method_2 : " + (end1 - start1));
    }
}
