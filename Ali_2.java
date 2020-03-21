public class Ali_2 {
    /*
        给一个音符序列，求能组成上升序列的最大长度，每个序列都是上升序列
        INPUT :
            n 序列个数 [1, 1e6]
            n 个字符串序列 [1, 100]
        OUTPUT :
            最长上升序列长度
        example :
            IN : 4
                 aaa bcd zzz bcdef
            OUT: 11 (aaabcdefzzz)
        idea : 观察序列 ab 和 aaabbbbb，这两个在生成序列时对于其他序列的影响是一样的，不过序列长度不同，所以可以将
               所有的 1e6 个序列缩减至 26*26 大小的 arr[i][j]，表示从字母 i 到 j 的最长序列长，特殊注意 arr[i][i]
               需要将所有序列求和。
     */

    final int SIZE = 26;
    int[][] arr = new int[SIZE][SIZE];
    int[] dp = new int[SIZE];

    // 预处理至 arr 二维数组中，数值取最长的，对角线特殊处理
    void preProcessing(String[] str) {
        for (String s : str) {
            int start = s.charAt(0) - 'a';
            int end = s.charAt(s.length() - 1) - 'a';
            if(start == end)
                arr[start][end] += s.length();
            else
                arr[start][end] = Math.max(s.length(), arr[start][end]);
        }
    }

    // 从尾至首，dp[i] 表示从字母 i+'a' 开始到字母 z 的最长序列。每次搜索 arr[i] 一行不为 0 的和不放 arr[i] 开头的
    // 取最大值为 dp[i]。dp[i] = max{arr[i][j] + dp[j] + arr[i][i] (j in [i+1, SIZE-1]), arr[i][i] + dp[i + 1]}.
    int sequenceLIS(String[] str) {
        preProcessing(str);
        dp[SIZE - 1] = arr[SIZE - 1][SIZE - 1];
        for (int i = SIZE - 2; i >= 0; --i) {
            int res = 0;
            for (int j = i + 1; j < SIZE; ++j) {
                if (arr[i][j] > 0)
                    res = Math.max(res, arr[i][j] + dp[j] + arr[i][i]);
            }
            dp[i] = Math.max(res, arr[i][i] + dp[i + 1]);
        }
        return dp[0];
    }

    public static void main(String[] args) {
        Ali_2 ali = new Ali_2();
        String[] str = {"aaa", "bcd", "zzz", "bcdef", "abcdeeeeeef",
                "bbbbbbc", "fghiopqrstz", "ghhi", "opq", "wyz", "bbbd"};
        System.out.println(ali.sequenceLIS(str));
        for (int i = 0; i < ali.SIZE; ++i) {
            System.out.print((char) ('a' + i) + "\t");
        } System.out.println();
        for (int temp : ali.dp) {
            System.out.print(temp + "\t");
        } System.out.println();
    }
}
