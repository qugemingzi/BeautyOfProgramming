import java.util.Scanner;

public class Ali_3 {
    /*
        n个人，从中选择任意数量人组成一支队伍，再从其中选出一名队长，求不同方案数对1e9+7取模结果。
        如果两个方案选取的人的集合不同或者选出的队长不同，则认为这两个方案不同。
        INPUT :
		    n in [1, 1e9]
        OUTPUT :
		    答案
        INPUT :
		    2
        OUTPUT :
		    4    因为(1’),(2’)(1’2)(12’)’表示队长
        易知 Cn,1+2*Cn,2+3*Cn,3+...+n*Cn,n = n*2^(n-1)，（哎，刚开始把公式记错了，只得暴力A了70%
        即将问题转化为大数取模，暴力的话会超时，当时提交过了70%，快速幂取模，针对的问题为a^b mod c,
        当 b 和 c 比较大时。俩公式 a*b mod c = (a mod c)*(b mod c) a^b mod c = (a mod c)^b mod c
        快速幂求解时将指数 b 转化为二进制，通过移位操作求，如 a^11 = a^(1011) = a^(2^0+2^1+2^3)。
     */

    // 暴力求解 O(n)
    static long m_1(int n, int mod){
        long r = n;
        for (int i = 0; i < n - 1; ++i) {
            r *= 2;
            r %= mod;
        }
        return r;
    }

    // 快速幂求解 11=(1011)=2^0+2^1+2^3 O(logn)
    static long m_2(int n, int mod){
        long ans = n--, base = 2;
        while(n != 0){
            if((n & 1) == 1) // 该位为 1
                ans = (ans*base) % mod;
            n >>= 1;         // 右移 1 位 <=> 除以 2
            base = (base * base) % mod;
        }
        return ans;
    }

    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int mod = 1000000007;
        System.out.println(m_1(n, mod)); // 70%通过，O(n)
        System.out.println(m_2(n, mod)); // 预测100%通过，O(logn)
    }
}
