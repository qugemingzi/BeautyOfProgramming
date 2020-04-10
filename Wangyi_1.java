package algorithm;

import java.util.Scanner;

/**
 * @author pray chow
 * 给定一数组 arr，找到最大的 d 满足是所有 arr[i+1]-arr[i] 的因数
 * 默认应该是升序数组，d 不存在输出 -1
 * INPUT :
 *      n       in [2, 2e5]
 *      arr[n]  in [1, 1e18]
 * OUTPUT :
 *      d
 * IN :
 *      4
 *      1 3 7 15
 * OUT :
 *      2
 * idea :
 *      依次求差两两求 gcd
 */
public class Wangyi_1 {

    public static long gcd(long x, long y) {
        return (y == 0) ? x : gcd(y, x % y);
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        long pre = scan.nextLong(), res = 0, minus = 0;
        boolean flag =true;
        for(int i = 0; i < n - 1; ++i) {
            long cur = scan.nextLong();
            minus = cur - pre;
            pre = cur;
            if(minus <= 0) {
                flag = false;
            }
            res = i == 0 ? minus : gcd(minus, res);
        }
        if(flag) {
            System.out.println(res);
        } else {
            System.out.println(-1);
        }
        scan.close();
    }
}
