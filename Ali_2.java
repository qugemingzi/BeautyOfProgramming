public class Ali_2 {

    final int SIZE = 26;
    int[][] arr = new int[SIZE][SIZE];
    int[] dp = new int[SIZE];

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
        }
        System.out.println();
        for (int temp : ali.dp) {
            System.out.print(temp + "\t");
        }
        System.out.println();
    }
}
