/**
 * @author pray chow
 * MicroSoft_1
 */
public class MS_1 {

    /**
     * 一共n个人，k个人一组，k%n==0。每个人有能力值，可能重复，要求把n个人分成n/k个小组，每个小组k个人，每个小组内部不允许
     * 能力值相同。定义得分为每个小组能力值max-min在求和，输出最少的得分，没法分组或者每个分组得分都为0的话输出-1。
     *
     * @param n   人数，int
     * @param k   组员数，int
     * @param arr 每人能力值，int[]
     * @return Minimum score
     * IN :		12	4	{5,10,4,8,3,6,5,10,4,8,3,6}
     * OUT :	15		{3,4,5,6},{3,4,8,10},{5,6,8,10} score=3+7+5=15
     * IN :		2	2	{1,1}
     * OUT :    -1
     * idea : 以数值为下标记录出现次数(counts[max-min+1])，统计分组情况(group[nums][k])顺序将能力值填入同一分组中，
     * 这样会比较平均，还需考虑数值出现次数等于剩余组数时，需要将该数平均分到剩余组中，直至 group 填完。
     */
    public static int Win_The_War(int n, int k, int[] arr) {
        // 小组数目
        int nums = n / k;
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int temp : arr) {
            min = Math.min(temp, min);
            max = Math.max(temp, max);
        }
        // 通过min, max缩小counts大小
        int[] counts = new int[max - min + 1];
        // 分组表
        int[][] group = new int[nums][k];
        for (int temp : arr) {
            counts[temp - min]++;
        }
        int score = 0, complete = nums, colpos = 0, rowpos;
        for (int temp : arr) {
            // 某个数比分组数还多，无法分组
            if (counts[temp - min] > nums) {
                return -1;
            }
        }
        while (complete > 0 && colpos < k) {
            // 将剩余能量值中个数与剩余组数相等的均分每组
            for (int i = 0; i <= max - min; ++i) {
                if (counts[i] == complete) {
                    for (int j = nums - complete; j < nums; ++j) {
                        group[j][colpos] = i + min;
                    }
                    // 列填完数
                    colpos++;
                    counts[i] = 0;
                }
            }
            // 行填完数
            rowpos = colpos;
            // 依次放入分组中
            for (int i = 0; i <= max - min && rowpos < k; ++i) {
                if (counts[i] > 0) {
                    group[nums - complete][rowpos] = i + min;
                    counts[i]--;
                    rowpos++;
                }
            }
            complete--;
        }
        // 统计得分
        for (int i = 0; i < nums; ++i) {
            int big = min - 1, small = max + 1;
            for (int j = 0; j < k; ++j) {
                big = Math.max(big, group[i][j]);
                small = Math.min(small, group[i][j]);
            }
            score += (big - small);
        }
        if (score == 0) {
            return -1;
        } else {
            return score;
        }
    }

    public static void main(String[] args) {
        int n = 12, k = 4;
        int[] arr = {5, 10, 4, 8, 3, 6, 5, 10, 4, 8, 3, 6};
        System.out.println(Win_The_War(n, k, arr));
    }
}
