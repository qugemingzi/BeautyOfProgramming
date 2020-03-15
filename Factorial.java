public class Factorial {
    /*
        阶乘求末尾 0 个数，阶乘二进制表示中最低位 1 的位置
        idea 1: 0 为 2*5 的数量，显然 5 的个数比 2 的个数少，即求所有数中 5 的个数
        idea 2: 5 的个数 = [N/5] + [N/25] + [N/125]... 直到 N 小于 5的幂
        idea 3: 最低位 1 的位置即为求所有数中 2 的个数，采取类似上述方法求
                2 的个数 = [N/2] + [N/4] + [N/8]... 直到 N 小于 2的幂
        idea 4: 2 的个数 = N - N 二进制中 1 的数目。
        extension: 判断一个数是否为 2 的方幂
            (n > 0) && (n & (n-1) == 0)
     */

    // idea 1: 求 5 的数量
    static int fac_1(int n){
        int res = 0;
        for(int i = 1; i <= n; ++i){
            int j = i;
            while(j % 5 == 0){
                res++;
                j /= 5;
            }
        }
        return res;
    }

    // idea 2: 求 5 的数量
    static int fac_2(int n){
        int res = 0;
        while(n != 0){
            res += n / 5;
            n /= 5;
        }
        return res;
    }

    // idea 3: 求 2 的数量，类比 idea 2
    static int fac_3(int n){
        int res = 1;
        while(n != 0){
            n >>= 1;
            res += n;
        }
        return res;
    }

    // idea 4: 2 的个数 = N - N 二进制中 1 的数目
    static int fac_4(int n){
        return n-ONE_Numbers.count_3((byte)n)+1;
    }

    // extension: 是否为方幂
    static boolean isPower(int n){
        return ((n>0) && (n & (n-1)) == 0);
    }

    public static void main(String[] args){
        System.out.println(fac_1(25));
        System.out.println(fac_2(25));
        System.out.println(fac_3(10));
        System.out.println(fac_4(10));
        System.out.println(isPower(8));
    }
}
