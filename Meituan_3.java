import java.util.Arrays;
import java.util.Scanner;

public class Meituan_3 {
    /*
        任务最大得分
        n 个任务，每个任务有 k 个子任务，第 i 个子任务花费时间为 ti。认为 n 个人物的第 i 个子任务需要的时间一样。
        每个任务、子任务都只能完成一次，任何任务不可重复完成。完成一个子任务得分 p，完成一个任务的子任务得额外的
        q 分，也就是会获得 pk+q 分。现共有 m 的时间，求最大的得分。
        INPUT :
            三整数 n, k, m [1, 100] [1, 100] [0, 2e9]
            俩整数 p, q [1, 100] [1, 100]
            k 整数表示每个子任务需要的时间
        OUTPUT :
            m 时间内最大得分
        example :
            IN : 3 2 8      2 2 3
                 3 1        1 2
                 9 5        1 1
            OUT: 3          5
        idea : 遍历共可完成几个任务，剩下的时间按时间升序完成子任务，时间复杂度O(n*k)。
     */

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
