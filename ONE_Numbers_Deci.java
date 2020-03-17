public class ONE_Numbers_Deci {
    /*
        给定十进制正整数 N，输出从 1 开始到 N 的所有整数中，出现的“1”个数
        N = 2: 1, 2 number = 1;
        N = 12: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 number = 5
        1、f(N) = ?
        2、f(N) == N 最大的 N ？
        analysis:
        idea 1: 遍历求取，简单直接，但是效率不高，约为O(nlogn)。
        idea 2: 找寻规律，按位数上的 1 来求
            abcde 百位 c 取 1 的个数为
            c == 0: ab * 100
            c == 1: ab * 100 + de + 1
            c > 1:  (ab + 1) * 100
            时间复杂度为O(logn)
        idea 3: 找寻规律
                        9   1
                       99   20
                      999   300
                     9999   4000
              999 999 999   900 000 000
            999 999 999 9   10 000 000 000
            数学归纳法得知 f(N) > N 在越过一个界时恒成立，有上界为 N=10^11。
            从 N=10^11-1 依次往下寻找第一个满足 f(n) == n 的 n=1 111 111 110。
        extension: 二进制表达中有多少个 1？
            f(1) = 1
            f(10) = 10(01, 10)
            f(11) = 100(01, 10, 11)
            f(100) = 101(01, 10, 11, 100)
            f(101) = 111(01, 10, 11, 100, 101)
            abcde 百位 c 取 1 的个数为
                c == 0: ab * 2^2 (ab <<= 2)
                c == 1: ab * 2^2 + de + 1
            类比上述 idea 2 即可。
     */
    // idea 1: 遍历
    static int f_1(int n) {
        int count = 0, oneNum, temp;
        for (int i = 0; i <= n; ++i) {
            oneNum = 0;
            temp = i;
            while (temp != 0) {
                oneNum += (temp % 10 == 1) ? 1 : 0;
                temp /= 10;
            }
            count += oneNum;
        }
        return count;
    }
    // idea 2: 找规律
    static int f_2(int n){
        int count = 0, factor = 1;
        int higher = 0, middle = 0, lower = 0;
        while(n / factor != 0){
            higher = n / (factor * 10);
            middle = (n / factor) % 10;
            lower = n % factor;
            switch (middle){
                case 0:
                    count += higher * factor;
                    break;
                case 1:
                    count += higher * factor + lower + 1;
                    break;
                default:
                    count += (higher + 1) * factor;
                    break;
            }
            factor *= 10;
        }
        return count;
    }

    public static void main(String[] args){
        System.out.println(f_1(12));
        System.out.println(f_2(2) + " " + f_2(12) + " " + f_2(100000000));
        System.out.println(f_2(1111111110) + " " + f_2(1111111111) + " " + f_2(1111111109));

    }
}
