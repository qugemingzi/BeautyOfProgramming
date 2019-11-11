import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Meeting {
    /*
        n位学生分别对m个研究组中的若干个感兴趣，为了满足学生的需求，如果每个见面会时间为t，怎么安排总见面会时间最短？
        analysis:
        采用图模型，每个兴趣小组作为一个点，如果存在一位学生同时对这两个感兴趣，则生成一条边连接两点，
        则问题转化为从生成的图中着色，最少着色数*t即为最短时间。
        idea 1:
        暴力枚举所有颜色可能，枚举时间复杂度为O((n-1)^n)，验证为O(n^2)，总O((n-1)^n*n^2)。
        idea 2:
        尝试K种着色，从1开始，逐渐提高K，当假设待求解的图最少着色数远小于图的顶点数时，复杂度远低于idea 1。
        extension:
        n个面试，有各自的时长，至少需要多少个面试点
        idea:
        类着色问题，不过可以考虑时间点设计多项式时间算法，给出两个思路实现，时间复杂度为O(n*log n)
     */
    private int n; // 面试个数
    private int []B; // 每个面试开始时间（排号顺序）
    private int []E; // 每个面试结束时间

    public Meeting(int n, int []B, int []E){
        this.n = n;
        this.B = B;
        this.E = E;
    }

    public int calColors(){
        int nMaxColors = 0, i, j, k;
        Map<Integer, Boolean> isForbidden = new HashMap<>();
        int []color = new int[n];

        for(i = 0; i < n; i++){
            for(k = 0; k < nMaxColors; k++)
                isForbidden.put(k, false);
            for(j = 0; j < i; j++)
                if(Overlap(j, i))
                    isForbidden.put(color[j], true);
            for(k = 0; k < nMaxColors; k++)
                if(!isForbidden.get(k))
                    break;
            if(k < nMaxColors)
                color[i] = k;
            else
                color[i] = nMaxColors++;
        }
        System.out.println("每个面试对应的颜色（面试点）如下：");
        for(i = 0; i < n; i++)
            System.out.print(i + "-" + color[i] + "  ");
        System.out.println();

        return nMaxColors;
    }

    public boolean Overlap(int j, int i){
        if(E[j] > B[i])
            return true;
        else
            return false;
    }

    public static void main(String []args){
        Scanner scan = new Scanner(System.in);
        System.out.println("请输入面试个数："); // 5
        int n = scan.nextInt();
        int B[] = new int[n];
        int E[] = new int[n];
        System.out.println("请按照开始时间数组B依次输入每个面试的开始时间和结束时间！");
        // 1,5 2,3 3,4 3,6 5,6
        for(int i = 0; i < n; i++){
            B[i] = scan.nextInt();
            E[i] = scan.nextInt();
        }
        Meeting m = new Meeting(n, B, E);
        System.out.println("至少需要面试点个数为 " + m.calColors() + " 个！");

        scan.close();
    }
}
