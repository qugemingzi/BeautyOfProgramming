import java.util.Scanner;

public class A360_2 {
    /*
        两人抽奖，n 中奖票，m 不中奖票，A 先抽，B 后抽，B 抽完后还可以带走一张票，有人抽到中奖票即该人胜，结束。
        抽过的奖被丢弃，B 带走的票中奖也不算 B 胜，现 A 先抽，问 A 胜的概率，保留 4 位小数。
        INPUT :
            两个数 n, m，中奖票与不中奖票的数量 [0, 1000]
        OUTPUT :
            A 的胜率，保留 4 位小数
        IN : 1 3
        OUT: 0.5000         1/4 + 3/4 * 2/3 * (1/2 * P[1][0] + 1/2 * P[0][1]) = 1/2 P[1][0]=1, P[0][1]=0
        IN : 2 3
        OUT: 0.6000         2/5 + 3/5 * 2/4 * (1/3 * P[2][0] + 2/3 * P[1][1]) = 3/5 P[2][0]=1, P[1][1]=1/2
        idea :
            P[i][j] 表示还有 i 张中奖票 j 张不中奖票时 A 先抽中奖的概率
            由上述知
                P[0][i] = 0  i in [0, m]
                P[i][0] = 1  i in [1, n]
                P[i][j] = i/(i+j) + j/(i+j) * (j-1)/(i+j-1) * [(j-2)/(i+j-2) * P[i][j-3] + i/(i+j-2) * P[i-1][j-2]]
            不过上述因为要访问 P[i][j-3] P[i-1][j-2] 我们需要置初值保证可访问，有两种方法。
                1、提前求出第 0 行和第 0、1、2 列的概率
                2、将第 -1、-2 列置为 0，再初始化第 0 行和第 0 列。其实可以扩两列，不过我们求的就是 P[n][m+2]
            第1、多求一下两列的概率，略显臃肿，且那两列也得自己推理，第2、则简明些，但递推公式需要更改，且需处理边界条件
     */

    static double getProb(int n, int m){
        if(n == 0)
            return 0.0;
        else if(m == 0)
            return 1.0;
        else if(m == 1)
            return (double) n/(n+1);
        double[][] p = new double[n+1][m+1];
        for(int i = 0; i <= m; ++i)
            p[0][i] = 0.0;
        for(int i = 1; i <= n; ++i) {
            p[i][0] = 1.0;
            p[i][1] = (double) i/(i+1);
            p[i][2] = (double) (i*i+i+2)/(i*i+3*i+2);
        }
        p[1][2] = 1.0/3;                        // 特殊处理的位置
        for(int i = 1; i <= n; ++i){
            for(int j = 3; j <= m; ++j){
                double temp = (double) i/(i+j);
                temp += (j*(j-1)*((j-2)*p[i][j-3]+i*p[i-1][j-2]))/((i+j)*(i+j-1)*(i+j-2));
                if(temp - 0.0 < 0.0001)         // 接近0防止越界
                    temp = 0.0;
                else if(1.0 - temp < 0.0001)    // 接近1防止越界
                    temp = 1.0;
                p[i][j]  = temp;
            }
        }
//        // 打印概率表
        for(int i = 0; i <= n; ++i){
            for(int j = 0; j <= m; ++j){
                System.out.print(String.format("%.4f", p[i][j]) + "\t");
            } System.out.println();
        }
        return p[n][m];
    }

    public static void main(String []args){
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt(), m = scan.nextInt();
        System.out.println(String.format("%.4f", getProb(n, m)));
        scan.close();
    }
}
