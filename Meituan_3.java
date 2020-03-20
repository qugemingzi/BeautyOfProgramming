import java.util.Arrays;
import java.util.Scanner;

public class Meituan_3 {
    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt(),
                k = scan.nextInt(),
                m = scan.nextInt(),
                p = scan.nextInt(),
                q = scan.nextInt();
        int[] arr = new int[k];
        int sum_ti = 0;
        for (int i = 0; i < k; ++i) {
            arr[i] = scan.nextInt();
            sum_ti += arr[i];
        }
        Arrays.sort(arr);
        long score = 0, cur_score;
        if (m >= n * sum_ti) {
            System.out.println((p * k + q) * n);
            return;
        }
        for (int task_num = 0; task_num <= m / sum_ti; ++task_num) {
            cur_score = 0;
            cur_score += task_num * (p * k + q);
            int rest_time = m - task_num * sum_ti;
            for (int i = 0; i < k; ++i) {
                if ((arr[i] * (n - task_num)) < rest_time) {
                    rest_time -= (arr[i] * (n - task_num));
                    cur_score += p * (n - task_num);
                } else {
                    int rest_task_num = rest_time / arr[i];
                    cur_score += p * rest_task_num;
                    break;
                }
            }
            score = Math.max(score, cur_score);
        }
        System.out.println(score);
    }
}
