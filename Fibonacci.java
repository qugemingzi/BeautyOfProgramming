package algorithm;

/**
 * @author pray chow
 * Fibonacci 数列
 *  1、递归
 *  2、通项公式
 *  3、数组备忘
 *  4、矩阵乘积快速幂
 * extension :
 *      F(0) = 1, F(1) = 2, F(2) = 2
 *      F(n) = F(n-1) + F(n-2) + F(n-3) for n > 2
 *      1、求 F(n)
 *      2、n 较大时，求 F(n) mod M (M < 100,000)
 * idea :
 *      1、上述方法 3 即可
 *      2、保存 F(i) mod M 值即可
 */
public class Fibonacci {

    /**
     * naive 的思想，时间 O(2^n) 空间 O(1)
     */
    public int fi_1(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else {
            return fi_1(n - 1) + fi_1(n - 2);
        }
    }

    /**
     * 求解通项公式
     * 解 x^2 = x + 1 解特征根
     * x1 = (1+sqrt(5))/2 , x2 = (1-sqrt(5))/2
     * f(n) = A * x1^n + B * x2^n   代入 f(0) = 0, f(1) = 1得
     * f(n) = sqrt(5)/5 * x1^n - sqrt(5)/5 * x2^n
     * 时间 O(1) 空间 O(1), 但因为有无理数的存在可能不精确
     */
    public int fi_2(int n) {
        // f(n) = sqrt(5)/5 * x1^n - sqrt(5)/5 * x2^n
        return 0;
    }

    /**
     * 数组保存，时间 O(n) 空间 O(n)
     */
    public int fi_3(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    /**
     * 优化 fi_3() 空间 O(1)
     */
    public int fi_4(int n) {
        int a = 0;
        int b = 1;
        for (int i = 2; i <= n; i++) {
            int c = a + b;
            a = b;
            b = c;
        }
        return b;
    }

    /**
     * 二维矩阵乘积来做，二姐递推数列
     * (f(n) f(n-1)) = (f(n-1) f(n-2)) * A
     * A = (1 1)
     *     (1 0)
     * (f(n) f(n-1)) = (f(1) f(0)) * A^(n-1)
     * 快速幂求 A^(n-1) f(n) = f(1) * A^(n-1)[0,0] + f(0) * A^(n-1)[1,0]
     */
    public int fi_5(int n) {
        // 调用快速幂求矩阵的积 -> Ali_3.m_2()
        return 0;
    }

    public static void main(String[] args) {
        Fibonacci fibonacci = new Fibonacci();
        System.out.println(fibonacci.fi_1(5));
        System.out.println(fibonacci.fi_2(5));
        System.out.println(fibonacci.fi_3(5));
        System.out.println(fibonacci.fi_4(5));
        System.out.println(fibonacci.fi_5(5));
    }
}
