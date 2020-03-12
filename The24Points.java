import java.util.Scanner;

public class The24Points {
    /*
        24 点游戏，给定玩家 4 张介于 1~13 的数，允许数值相同。采用加减乘除四则运算，
        允许中间运算过程存在小数，可以使用括号，但每个数只能使用一次，尝试构造一个
        表达式，使得运算结果为 24。
            e.g. 11, 8, 3, 5    (11-8) x (3+5) = 24
        idea 1: 穷举。
            每个数只能用一次，全排列 4! 种排列。4 个数之间运算需要 3 个运算符，每种
            排列有 4^3 种表达式，不考=考虑括号的情况下，共 4!*4^3 种表达式。
            再加上符号，共有 5 种加括号的方式：
            (((AB)C)D), ((A(BC))D), (A((BC)D)), (A(B(CD))), ((AB)(CD)).
            所有需要遍历的表达式最多为 4!*4^3*5 = 7680。
            接下来更细致的讨论一下具体的实现。假定 A={1, 2, 3, 4}，定义 f(A) 为对 A
            进行所有的四则混合运算所有值集合。
            首先从 A 种任意取出两个数，如取出 1 和 2，A=A-{1, 2}，对取出来的数分别进行
            四则运算，1+2=3, 1-2=-1, 1*2=2, 1/2=0.5，将所得的结果分别加入 A，可得到
            B1={3, 3, 4}, B2={-1, 3, 4}, B3={2, 3, 4}, B4={0.5, 3, 4}四个新的集合，
            f(A) = f(B1) U f(B2) U f(B3) U f(B4)，即达成分而治之的目的，由 4 个数降为
            3 个数的 4 个子问题之和。伪代码如下：
            f(Array){
                if(Array.Length < 2){
                    if(得到最终结果为 24) 输出表达式
                    else 输出无法构造符合要求的表达式
                }
                foreach(从数组种任取两个数的组合){
                    foreach(运算符 +, -, *, /){
                        1、计算该组合在此运算符下的结果
                        2、将该组合中的两个数从原数组移除，并将1、结果放入数组
                        3、对新数组递归调用 f，若找到一个表达式则返回
                        4、将1、计算结果移除，并将该组合中的两个数重新放回数组对应的位置
                    }
                }
            }
        该方法思路清晰，但存在不少冗余操作，比如加法和乘法的交换率，比如加法和乘法第一个
        操作数必须小于第二个数，即若 A>B, 只进行 B+A 的操作，A+B 时直接返回，剪枝操作。
     */

    static final double Threshold = 1e-6;
    static final int CardsNumber = 4;
    static final int ResultValue = 24;
    static double []number = new double[CardsNumber];
    static String []result = new String[CardsNumber];

    static boolean run(int n){
        if(n == 1){
            // 由于浮点数运算会有精度误差，所以用一个很小的数 1e-6 来做容差值
            if(Math.abs(number[0] - ResultValue) < Threshold){
                System.out.println(result[0]);
                return true;
            }else{
                return false;
            }
        }
        for(int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                double a = number[i], b = number[j];
                String expa = result[i], expb = result[j];

                number[j] = number[n - 1];
                result[j] = result[n - 1];

                result[i] = "(" + expa + "+" + expb + ")";
                number[i] = a + b;
                if (run(n - 1))
                    return true;
                result[i] = "(" + expa + "-" + expb + ")";
                number[i] = a - b;
                if (run(n - 1))
                    return true;
                result[i] = "(" + expb + "-" + expa + ")";
                number[i] = b - a;
                if (run(n - 1))
                    return true;
                result[i] = "(" + expa + "*" + expb + ")";
                number[i] = a * b;
                if (run(n - 1))
                    return true;
                if (b != 0) {
                    result[i] = "(" + expa + "/" + expb + ")";
                    number[i] = a / b;
                    if (run(n - 1))
                        return true;
                }
                if (a != 0) {
                    result[i] = "(" + expb + "/" + expa + ")";
                    number[i] = b / a;
                    if (run(n - 1))
                        return true;
                }
                number[i] = a;
                number[j] = b;
                result[i] = expa;
                result[j] = expb;
            }
        }
        return false;
    }

    /*
        idea 2: 分而治之。
            将 A 分为两个真子集 A1 和 A-A1，分别计算两个分支的 f(A1) 和 f(A-A1)，
            则 f(A) 为 f(A1) 和 f(A-A1) 两两元素叉乘进行加减乘除运算的结果并集。
            Fork(A, B) = U{a+b, a-b, b-a, a*b, a/b(b!=0), b/a(a!=0)} (a, b) in AxB。
            求解 Fork(A, B) 时可以将重复的结果去掉，即剪枝过程。
            在考虑别的冗余操作，例如 A={1, 2, 3, 4}，子集 {1, 3, 4}, {2, 3, 4}, {3, 4}
            都要求 f({3, 4}) ，如何解决这个冗余问题？采取表格存储所有的真子集的 f 值，
            需要求的时候先查表，以前计算过则不用再求。集合 A 共有 2^n-1 个真子集，
            在具体实现时。可以用二进制数表示集合和子集的概念。{1, 2, 3, 4} => 1111,
            {1, 4} => 1001。其中 X & A == X 时 X 为 A 的真子集(X < A)，另一真子集为 A-X。
            伪代码如下：
            run(Array){                 // Array 为初始输入的集合，其中元素表示为 ai(0 <= i <= n-1)
                for(int i = 1; i <= 2^n-1; ++i)
                    S[i] = null;        // 初始化将每个集合的结果集置为空
                for(int i = 0; i < n; ++i)
                    S[2^i] = {ai};      // 先对每个只有一个元素的真子集赋值
                for(int i = 1; i <= 2^n-1; ++i)
                    S[i] = f(i);        // 每个 i 代表 Array 的一个真子集
                Check(S[2^n-1]);        // 检查 S[2^n-1] 中是否有 24 的元素
            }
            f(int i){                   // i 的二进制可代表集合的一个真子集
                if(S[i] != null)
                    return S[i];
                for(int x = 1; x < i; ++x)
                    if(x & i == x)      // x 为 i 的真子集
                        S[i] U= Fork(f(x), f(i-x));
            }
     */

    /*
        extension:
        1）~3）Obviously
        4）加入阶乘(!)运算，上述两个算法需要怎么改动呢？
            idea 1 中将 a 与 b 的运算加入阶乘运算，即每种运算有 8 种可能。
            即 (a+b) 有 (a+b), (a!+b), (a+b!), (a+b)!, (a!+b!), (a!+b)!, (a+b!)!, (a!+b!)!
            以此类推。
            idea 2 中将 Fork(A, B) 函数更改，也是类比上述，加入阶乘运算的可能。
            不过这两种更改都有些繁琐，应该还有更好的方式。
     */

    public static void main(String []args){
        int x;
        Scanner scanner = new Scanner(System.in);
        for(int i = 0; i < CardsNumber; ++i){
            System.out.println("the " + (i+1) + "th number: ");
            x = scanner.nextInt();
            number[i] = x;
            result[i] = x+"";
        }
        if(run(CardsNumber))
            System.out.println("Success.");
        else
            System.out.println("Fail.");
    }
}
