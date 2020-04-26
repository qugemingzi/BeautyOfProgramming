package algorithm;

import java.util.Scanner;

/**
 * @author pray chow
 * 无限深度的满二叉树，编号为 1-2|3-4|5|6|7-...
 * Q 次询问，找编号为 x 的结点在深度为 k 的祖先结点编号为？
 * INPUT :
 *      Q 次询问   in [1, 1e4]
 *      Q 行，每行 x, k    x in [1, 1e18] k in [1, 60]
 * OUTPUT :
 *      有则输出编号，无则输出 -1
 * IN :
 *      4
 *      10 1
 *      10 2
 *      10 3
 *      10 4
 * OUT :
 *      1
 *      2
 *      5
 *      -1
 * 位运算，100%
 */
public class Tencent_5 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int q = scan.nextInt();
        long[] query = new long[q];
        int[] father = new int[q];
        for (int i = 0; i < q; ++i) {
            query[i] = scan.nextLong();
            father[i] = scan.nextInt();
        }
        scan.close();
        for (int i = 0; i < q; ++i) {
            long tmp = query[i];
            int depth = 0;
            while (tmp != 0) {
                tmp >>= 1;
                depth++;
            }
            if (father[i] >= depth) {
                System.out.println(-1);
            } else {
                long res = query[i];
                while (depth - father[i] > 0) {
                    res >>= 1;
                    depth--;
                }
                System.out.println(res);
            }
        }
    }
}
