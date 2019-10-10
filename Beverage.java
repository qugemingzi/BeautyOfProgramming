public class Beverage {
    private String []S;  // 饮料名字
    private int []V;     // 饮料容量
    private int []C;     // 饮料可能的最大数量
    private int []H;     // 饮料满意度
    private int []B;     // 实际购买量，即我们所求
    private int [][]opt; // dp表

    Beverage(String []s, int []v, int []c, int []h){
        S = s;
        V = v;
        C = c;
        H = h;
    }

    /*
        opt[M][i]表示从第i,i+1,...T-1种饮料中，算出总量为M的方案中满意度之和最大值
        opt[M][i] = max{k * H[i] + opt[M-k*V[i]][i+1]}
        k = 0,1,...C[i], i = 0,1,...T-1
        opt[0][M] = 0
        opt[x][M] = -INF(x!=0)
        所求即为opt[M][0]
     */
    int cal(int M, int T){
        opt = new int [M+1][T+1];
        opt[0][T] = 0;                          // 边界条件，T为饮料种类
        for(int i = 1; i <= M; i++){            // 边界条件
            opt[i][T] = 0;
        }
        for(int j = T - 1; j >= 0; j--){
            for(int i = 0; i <= M; i++){
                opt[i][j] = Integer.MIN_VALUE;
                for(int k = 0; k <= C[j]; k++){ // 遍历第j种饮料选取数量k
                    if(i < k * V[j]){
                        break;
                    }
                    int x = opt[i - k * V[j]][j + 1];
                    if(x != Integer.MIN_VALUE){
                        x += k * H[j];
                        if(x > opt[i][j]){
                            opt[i][j] = x;
                        }
                    }
                }
            }
        }

        return opt[M][0];
    }

    /*
        设置opt为-1，这样求解完的子问题就不用求解了，提高效率
     */
    int cal_2(int M, int T){
        opt = new int [M+1][T+1];
        for(int i = 1; i <= M; i++)
            for(int j = 0; j < T; j++)
                opt[i][j] = -1;
        return cal_22(M, T, 0);
    }

    int cal_22(int M, int T, int type){
        if(type == T)
            return 0;
        else if(M == 0)
            return 0;
        else if(opt[M][type] != -1) // 子问题已经求解，直接返回
            return opt[M][type];

        int ret = Integer.MIN_VALUE;
        for(int i = 0; i <= C[type]; i++){
            if(M < i * V[type]) {
                break;
            }
            int temp = cal_22(M - i * V[type], T, type + 1);
            if (temp != Integer.MIN_VALUE) {
                temp += i * H[type];
                if (temp > ret)
                    ret = temp;
            }
        }
        return opt[M][type] = ret;
    }

    /*
        采用贪心策略，将每个饮料单位容量满意度从大到小排序
        依次选取饮料则为满意度最大
        单位容量满意度相同，则容量大的在前面
        因为M = 30, V[0] = 2, V[1] = 4, H[0] = 1, H[1] = 2,
        per[0] = per[1] = 0.5, C[0] = C[1] = 10
        先选容量为2L的选10个，还可以再选2个4L的，满意度为14
        先选容量为4L的选7个，再选1个2L的，满意度为15，per相同则两者等价
        先把更大块的放进去更好的利用空间
     */
    int cal_3(int M, int T){
        double []per = new double[T];
        for(int i = 0; i < per.length; i++)
            per[i] = (double) H[i]/V[i];

        // 依照单位容量满意度排序
        for(int i = 0; i < per.length-1; i++){
            for(int j = i; j < per.length; j++){
                if((per[i] < per[j]) || ((per[i] == per[j]) && (V[i] < V[j]))){
                    double dtemp;
                    int itemp;
                    dtemp = per[i]; per[i] = per[j]; per[j] = dtemp;
                    itemp = V[i]; V[i] = V[j]; V[j] = itemp;
                    itemp = C[i]; C[i] = C[j]; C[j] = itemp;
                    itemp = H[i]; H[i] = H[j]; H[j] = itemp;
                }
            }
        }
        int happiness = 0;
        // 贪心
        for(int i = 0; i < T && M > 0; i++){
            int count = Integer.min(M / V[i], C[i]);
            M -= count * V[i];
            happiness += count * H[i];
        }

        return happiness;
    }

    void print_opt(){
        for(int i = 0; i < opt.length; i++){
            for(int j = 0; j < opt[0].length; j++){
                System.out.print(opt[i][j] + "\t");
            }System.out.println();
        }
    }


    public static void main(String []args){
        String []s = {"酸奶", "豆浆", "凉茶", "咖啡", "可乐"};
        int []v = {32, 16, 8, 4, 2};
        int []c = {10, 10, 10, 10, 10};
        int []h = {5, 4, 3, 2, 1};
        Beverage beverage = new Beverage(s, v, c, h);
        System.out.println(beverage.cal(30, 5));
        beverage.print_opt();
        System.out.println(beverage.cal_2(156, 5));
        beverage.print_opt();
        System.out.println(beverage.cal_3(30, 5));
        System.out.println(beverage.cal_3(156, 5));
    }
}
