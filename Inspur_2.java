package algorithm;

import java.util.Scanner;

/**
 * @author pray choe
 * 给定两排树
 *      1 3 5 7 9 ... 99
 *      2 4 6 8 10 ... 100
 * 给定去除的 n 棵树，问剩余最长连续树有多长，起始位置在哪里？
 * 若两段长度相同返回起始位置最小的
 * INPUT :
 *      n
 *      n 个数
 * OUTPUT :
 *      起始位置 长度
 * IN :
 *      5
 *      9 15 27 35 6
 * OUT :
 *      8 47
 * idea :
 *      暴力求解，从后往前遍历，注意边界，以及长度相同取最小的初始位置
 */
public class Inspur_2 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        boolean[] flag = new boolean[101];
        for (int i = 0; i < n; ++i) {
            int temp = scan.nextInt();
            flag[temp] = true;
        }
        scan.close();
        int maxlen = 0, index = 0, count = 0;
        for (int i = 99; i > 0; i -= 2) {
            if (!flag[i]) {
                count++;
            } else {
                if (count >= maxlen) {
                    maxlen = count;
                    index = i + 2;
                }
                count = 0;
            }
        }
        if (count >= maxlen) {
            maxlen = count;
            index = 1;
        }
        count = 0;
        for (int i = 100; i > 0; i -= 2) {
            if (!flag[i]) {
                count++;
            } else {
                if (count > maxlen || (count == maxlen && i + 2 < index)) {
                    maxlen = count;
                    index = i + 2;
                }
                count = 0;
            }
        }
        if (count > maxlen || (count == maxlen && 2 < index)) {
            maxlen = count;
            index = 2;
        }
        System.out.println(index + " " + maxlen);
    }
}
