import java.util.ArrayList;

public class NIM_3 {
    /*
        假设有两堆石头，有两个玩家根据如下规则轮流取石头：
        每人每次可以从两堆石头中各取出数量相同的石头，或者仅从一堆石头中取出任意数量的石头。
        最后把剩下的石头一次拿光的获胜。
     */

    // n, m分别是两堆石头的数量
    // 返回先取者是否能赢得游戏
    /*
        采取类似构造质数的“筛子”方法。
        先将数字从 2 开始依次排列：
        2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20...
        2 是质数，则 2 的倍数不是质数，“筛掉”这些：
        2 3   5   7   9    11    13    15    17    19   ...
        依次为 3 的倍数：
        2 3   5   7        11    13          17    19   ...
        如法炮制，我们得到了后面的质数。
        该想法也是依次“筛掉”一些，我们先构造(1, 1) ~ (10, 10)的情况，定义必胜的为安全局面，
        否则为不安全局面：
        1,1
        1,2  2,2
        1,3  2,3  3,3
        1,4  2,4  3,4  4,4
        1,5  2,5  3,5  4,5  5,5
        1,6  2,6  3,6  4,6  5,6  6,6
        1,7  2,7  3,7  4,7  5,7  6,7  7,7
        1,8  2,8  3,8  4,8  5,8  6,8  7,8  8,8
        1,9  2,9  3,9  4,9  5,9  6,9  7,9  8,9  9,9
        1,10 2,10 3,10 4,10 5,10 6,10 7,10 8,10 9,10 10,10
        首先(1, 1) ... (10, 10)都为安全局面，且(1, 2)为不安全局面，而可以一步到(1, 2)的
        所有情况都为安全局面，包括(1, n), (n+1, n+2), (2, n)，筛掉后为：
        1,2


                  3,5
                  3,6  4,6
                  3,7  4,7  5,7
                  3,8  4,8  5,8  6,8
                  3,9  4,9  5,9  6,9  7,9
                  3,10 4,10 5,10 6,10 7,10 8,10
        下一组数为(3, 5)，它同样为不安全局面，因为(3, 5)的任何一个符合规则的变化都是一个
        安全局面，于是根据和(1, 2)一样的筛法，筛掉(3, n), (5, n), (n+3, n+5)为：
        1,2


                  3,5

                       4,7
                       4,8
                       4,9       6,9
                       4,10      6,10 7,10
        之后为(4, 7), (6, 10)，得到如下不安全局面表
        n   1   2   3   4   5   6   7   8   9   10 ...
        an  1   3   4   6   8   9   11  12  14  16 ...
        bn  2   5   7   10  13  15  18  20  23  26 ...
        满足规律为：
        1）a1 = 1, b1 = 2
        2）an为之前a1, b1, ..., a(n-1), b(n-1)共 2n-2 个数最小的整数
        3）bn = an + n
        解法 1 即为自底向上的求解方法。
     */
    static boolean nim_1(int n, int m){
        // 特殊情况
        if(n == m)
            return true;
        // 交换大小
        if(n > m) {
            int t = n;
            n = m;
            m = t;
        }
        // 基本情况
        if(n == 1 && m == 2)
            return false;
        // arrayList 存取不安全局面下的 bn 值
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(2);

        int x = 1; // 记录 an 值
        int delta = 1; // 记录 bn-an 值，即 n 值
        int addition = 0; // 记录求取次数，为缩减数组大小

        while(n > x){
            // 找到下一个没存过的 x 为新的 an
            while(arrayList.indexOf(++x) != -1);
            delta++;
            arrayList.add(x + delta);
            addition++;
            if(addition > 100){
                // 数组大小超过 100 时，删除无用的不安全局面，
                // 使数组保持较小的规模，降低调用 indexof()
                // 函数的时间复杂度
                ShrinkArray(arrayList, x);
                addition = 0;
            }
        }
        if((x != n) || arrayList.indexOf(m) == -1)
            return true;
        else
            return false;
    }

    // 缩减前面的无用数组
    static void ShrinkArray(ArrayList<Integer> arrayList, int x){
        ArrayList<Integer> delList = new ArrayList<>();
        for(int i : arrayList){
            if(arrayList.get(i) <= x){
                delList.add(arrayList.get(i));
            }else{
                arrayList.removeAll(delList);
            }
        }
    }

    /*
        存在 an, bn 的通项公式，将nim_1()时间复杂度O(N)降为O(1)。
        an = [a * n], bn = [b * n], []表示向下取整
        a = (1 + sqrt(5)) / 2,
        b = (3 + sqrt(5)) / 2
     */
    static boolean nim_2(int n, int m){
        double a, b;
        a = (1 + Math.sqrt(5)) / 2;
        b = a + 1;
        if(n == m)
            return true;
        if(n > m) {
            int t = n;
            n = m;
            m = t;
        }
        return (n != (int)((m-n)*a));
    }

    public static void main(String []args){
        System.out.println(nim_1(16, 26) + ", " + nim_2(16, 26));
    }
    /*
        extension:
        1）如何得到必胜策略呢？即游戏过程
            我们只考虑先取者必胜的情况，一般如(n, m) (n < m)。策略为将安全局面变为不安全局面
            if n in an:
                m > bn: m -> bn
                m < bn: n -> a(m-n), m -> b(m-n)
            else n not in an, n in bn:
                m -> an
        2）NIM_4 两个玩家一堆石头，两人依次拿，最后拿光者赢，规则如下：
            1）第一个玩家不可以拿光全部的石头
            2）第一次拿完石头后，每人每次最多只能拿掉对方前一次所拿石头的两倍
           该游戏有无必胜的算法？(Hint: Fibonacci)
            结论：Fibonacci 数，先手败；非 Fibonacci 数，先手胜。
            证 1：Fibonacci 数，先手败。
                f(1) = 1, f(2) = 2 ... 易知f(2)先手败，假设 i <= k, f(i)都为先手败。
                i = k+1 时，f(i) = f(k) + f(k-1)
                若先手取 x >= f(k-1)，则 2x >= 2f(k-1) > f(k)，先手败。
                若先手取 x < f(k-1)，则单独对于f(k-1)这一堆石头，先手败，即先手拿不到
                这一堆石头的最后，后手能拿到f(k-1)这一堆的最后。其中后手最多可以取得
                2/3*f(k-1) 个石头，因为先手最少取 1/3*f(k-1) 个石头，那么对于f(k)这堆，
                先手最多可以取 2*2/3*f(k-1) = 4/3*f(k-1) < f(k)
                    <== 4*f(k-1) < 3*f(k)
                    <== 3*f(k) - 4*f(k-1) > 0
                    <== 3*f(k-2) - f(k-1) > 0
                    <== 3*f(k-2) - (f(k-2) + f(k-3)) > 0
                    <== 2*f(k-2) - f(k-3) > 0               证毕
                所以先手不能一次取完f(k)堆，根据假设，先手败                    证毕
            证 2：非 Fibonacci 数，先手胜。
                首先根据 Zeckendorf(齐肯多夫)定理：
                任意正整数可表示为若干个不连续 Fibonacci 数之和。(归纳法-是否为 Fib 数证)
                即 n = f(a1) + f(a2) + ... + f(ap), a1 > a2 > ... > ap 且间隔至少为 2
                先手先取f(ap)堆，因为 a(p-1) >= ap + 2, f(a(p-1)) > 2*f(ap)，所以后手不能
                一下取完f(a(p-1))堆，根据已有假设，则对于后手而言f(a(p-1))堆...f(a1)堆都是
                拿不到该堆最后的石头，故先手胜。                                证毕
     */
}
