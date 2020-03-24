import java.util.Scanner;

public class A360_2 {

    static double getProb(int n, int m){
        double[][] p = new double[n+1][m+1];
        for(int i = 0; i <= m; ++i)
            p[0][i] = 0.0;
        for(int i = 1; i <= n; ++i) {
            p[i][0] = 1.0;
            p[i][1] = (double) i/(i+1);
            p[i][2] = (double) (i*i+i+2)/(i*i+3*i+2);
        }
        p[1][2] = 1.0/3;
        if(n == 0 || m <= 2)
            return p[n][m];
        for(int i = 1; i <= n; ++i){
            for(int j = 3; j <= m; ++j){
                double temp = (double) i/(i+j);
                p[i][j]  = temp + (j*(j-1)*((j-2)*p[i][j-3]+i*p[i-1][j-2]))/((i+j)*(i+j-1)*(i+j-2));
            }
        }
        return p[n][m];
    }

    public static void main(String []args){
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt(), m = scan.nextInt();
        System.out.print(String.format("%.4f", getProb(n, m)) );
        scan.close();
    }
}
