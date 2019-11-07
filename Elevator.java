import java.util.Scanner;

public class Elevator {
    private int nPerson[]; // nPerson[i]表示第i层的乘客数目
    private int N;         // 共N层

    public Elevator(int N, int []nPerson){
        this.N = N;
        this.nPerson = nPerson;
    }

    // 暴力求解，时间复杂度O(N^2)
    public void violent(){
        int nFloor, nMinFloor = 0, nTargetFloor = -1;
        for(int i = 1; i <= N; i++){
            nFloor = 0;
            for(int j = 1; j < i; j++)
                nFloor += nPerson[j] * (i-j);
            for(int j = i+1; j <= N; j++)
                nFloor += nPerson[j] * (j-i);
            if(nTargetFloor == -1 || nMinFloor > nFloor){
                nMinFloor = nFloor;
                nTargetFloor = i;
            }
        }
        System.out.println("violent");
        System.out.println("目标楼层：" + nTargetFloor + " 总爬楼层数：" + nMinFloor);
    }

    // 分析楼层需要上一层的条件，时间复杂度降低为O(N)
    public void analysis_up(){
        int nMinFloor = 0, nTargetFloor = 1;
        int N1, N2, N3;
        N1 = 0; N2 = nPerson[1]; N3 = 0;
        for(int i = 2; i <= N; i++){
            N3 += nPerson[i];
            nMinFloor += nPerson[i] * (i-1);
        }
        for(int i = 2; i <= N; i++){
            if(N1 + N2 < N3){
                nTargetFloor = i;
                nMinFloor += (N1 + N2 - N3);
                N1 += N2;
                N2 = nPerson[i];
                N3 -= nPerson[i];
            }
            else
                break;
        }
        System.out.println("analysis_up");
        System.out.println("目标楼层：" + nTargetFloor + " 总爬楼层数：" + nMinFloor);
    }

    // 分析楼层需要下一层的条件，时间复杂度降低为O(N)
    public void analysis_down(){
        int nMinFloor = 0, nTargetFloor = N;
        int N1, N2, N3;
        N1 = 0; N2 = nPerson[N]; N3 = 0;
        for(int i = N-1; i >= 1; i--){
            N1 += nPerson[i];
            nMinFloor += nPerson[i] * (N-i);
        }
        for(int i = N-1; i >= 1; i--){
            if(N2 + N3 < N1){
                nTargetFloor = i;
                nMinFloor += (N2 + N3 - N1);
                N3 += N2;
                N2 = nPerson[i];
                N1 -= nPerson[i];
            }
            else
                break;
        }
        System.out.println("analysis_down");
        System.out.println("目标楼层：" + nTargetFloor + " 总爬楼层数：" + nMinFloor);
    }

    public static void main(String []args){
        System.out.println("请输入电梯层数：");
        Scanner scan = new Scanner(System.in);
        int N = scan.nextInt();
        System.out.println("请输入每层电梯人数：");
        int []nPerson = new int[N + 1];
        for(int i = 1; i <= N; i++){
            nPerson[i] = scan.nextInt();
        }
        Elevator e = new Elevator(N, nPerson);
        e.violent();
        e.analysis_up();
        e.analysis_down();

        scan.close();
    }
}
