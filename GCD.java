public class GCD {
    /*
        求两数最大公约数(Greatest Common Divisor, GCD)，若两数很大，有无简单算法？
        idea 1: 辗转相除法
        idea 2: 上述方法用到取模，大数取模(除法)开销昂贵，不取模？更相减损术
            省去了除法，不过迭代次数多了，比如(10000000000000, 1)。
        idea 3: 结合辗转相除法，更相减损术
            y=k*y1, x=k*x1 => f(y, x) = k*f(y1, x1)
            y=p*y1, p 为素数, x%p != 0 => f(y, x) = f(p*y1, x) = f(y1, x)
            取 p=2 因为 p 是素数且可以将除以 2 转化为移位运算
            x=even && y=even    f(x, y) = 2*f(x/2, y/2) = 2*f(x>>1, y>>1)
            x=even && y=odd     f(x, y) = f(x/2, y) = f(x>>1, y)
            x=odd && y=even     f(x, y) = f(x, y/2) = f(x, y>>1)
            x=odd && y=odd      f(x, y) = f(y, x - y) 则x-y=even，下步又会有移位运算(除以 2)
        extension:
            位运算的技巧，还见“二进制中 1 的个数” (v &= (v-1))，还有翻转一个整数(32位)的二进制表示
     */

    // idea 1: 辗转相除法
    int gcd_1(int x, int y) {
        return (y == 0) ? x : gcd_1(y, x % y);
    }

    // idea 2: 更相减损术
    int gcd_2(int x, int y) {
        if(x < y)
            return gcd_2(y, x);
        return (y == 0) ? x : gcd_2(y, x - y);
    }

    // idea 3: 结合1 与 2
    int gcd_3(int x, int y) {
        if(x < y)
            return gcd_3(y, x);
        if(y == 0)
            return x;
        else {
            if((int)(x & 1) == 0) {
                if((int)(y & 1) == 0)
                    return (gcd_3(x >> 1, y >> 1) << 1);
                else
                    return gcd_3(x >> 1, y);
            } else {
                if((int)(y & 1) == 0)
                    return gcd_3(x, y >> 1);
                else
                    return gcd_3(y, x - y);
            }
        }
    }

    // extension : 翻转整数(32位)的二进制表示
    int reverseBin(int x){
        int res, i;
        for(res = i = 0; i < 32 && x > 0; ++i, x >>= 1)
            res = (res << 1) | (x & 1);
        return res;
    }

    public static void main(String[] args){
        GCD g = new GCD();
        System.out.println(g.gcd_1(42, 30));
        System.out.println(g.gcd_2(42, 30));
        System.out.println(g.gcd_3(42, 30));
        System.out.println(g.reverseBin(211));
    }
}
