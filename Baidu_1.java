package algorithm;

import java.util.Scanner;

/**
 * @author pray chow
 * Baidu_1
 * 给定 n，找出两个数 a,b 都在区间 [1,n] 中，且 lcm(a,b)-gcd(a,b) 尽量大。
 * 输出最大的 lcm(a,b)-gcd(a,b) 其中 lcm(a,b)-最小公倍数 gcd(a,b)-最大公约数
 * INPUT :
 *      数字 n in [2,1e9]
 * OUTPUT :
 *      最大的 lcm(a,b)-gcd(a,b)
 * INPUT :
 *      5
 * OUTPUT :
 *      19      lcm(5,4)-gcd(5,4)=20-1=19
 * idea :
 *      lcm(a,b)=a*b/gcd(a,b) 且尽量保证 lcm 大 gcd 小
 *      不难发现 gcd(n,n-1)=1 因为 n 和 n-1 不可能被同一个大于 1 的数整除，
 *      所以 lcm(n,n-1)=n*(n-1)，差值为 n(n-1)-1。需注意 n 上界为 int，
 *      则 n*(n-1) 上界为 long。
 */
public class Baidu_1 {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        long n = scan.nextInt();
        System.out.println(n * (n - 1) - 1);
        scan.close();
    }
}
