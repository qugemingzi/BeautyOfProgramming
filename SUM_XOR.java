public class SUM_XOR {
    /*
        问题：1 XOR 2 XOR 3 ... XOR n = ?
        定义f(x, y)为 x 到 y 所有整数的异或值([x, y])。问题就转化为f(1, n)，
        又因为 0 XOR X = X，f(1, n) = f(0, n)。
        我们首先考虑f(2^k, 2^(k+1)-1)。
        2^k         100...000
        2^(k+1)-1   111...111
        最低位为第 0 位，最高位为第 k 位。此区间内共有 2^k 个数，当 k >= 1时，
        最高位可以消除掉，即f(2^k, 2^(k+1)-1) = f(0, 2^k-1)
        f(0, 2^(k+1)-1) = f(0, 2^k-1) XOR f(2^k, 2^(k+1)-1)
                        = f(0, 2^k-1) XOR f(0, 2^k-1)
                        = 0
        所以f(0, 2^k-1) = 0 (k >= 2)。
        下面求f(0, n) (n >= 4)，最高位 1 在 k 位(k >= 2)。
        f(0, n) = f(0, 2^k-1) XOR f(2^k, n)
                = f(2^k, n)
        其中共有 m = n+1-2^k 个数，将这 m 个数的最高位消除一些。
        1）n 为奇数，m 为偶数
            f(0, n) = f(2^k, n) = f(0, n-2^k)
                    = ... = f(0, n%4)
            n%4 == 1 时，f(0, n) = f(0, 1) = 1
            n%4 == 3 时，f(0, n) = f(0, 3) = 0
        2）n 为偶数，m 为奇数
            f(0, n) = f(2^k, n) = f(0, n-2^k) XOR 2^k
                    = f(0, n-2^k) OR 2^k = ...
                    = f(0, n%4) OR nn (nn为 n 的最低的 2 位为 0)
            n%4 == 0 时，f(0, n) = nn OR f(0, 0) = n
            n%4 == 2 时，f(0, n) = nn OR f(0, 2) = nn OR 3 = n + 1
     */
    public static void main(String []args){
        int n = 100;
        int t = n&3;
        if((t&1) != 0){
            System.out.println(1-t/3);
        } else {
            System.out.println(n+t/2);
        }
        /*if(n % 4 == 0){
            System.out.println(n);
        } else if(n % 4 == 1){
            System.out.println(1);
        } else if(n % 4 == 2){
            System.out.println(n+1);
        } else {
            System.out.println(0);
        }*/
    }
}
