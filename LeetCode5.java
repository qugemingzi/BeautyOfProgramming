/**
 * @author pray chow
 * LeetCode 5 最长回文子串
 */
public class LeetCode5 {

    /**
     * idea 1: dp表，O(n^2), n 为 s 的长度
     *
     * @param s 字符串
     * @return 最长回文子串
     */
    public String longestPalindrome_1(String s) {
        if (s.length() == 0) {
            return "";
        }
        int start = 0, end = 0;
        // dp[i][j] 表示从 i 到 j 是不是回文串
        boolean[][] dp = new boolean[s.length()][s.length()];
        for (int j = 0; j < s.length(); ++j) {
            for (int i = 0; i <= j; ++i) {
                if (i == j) {
                    dp[i][i] = true;
                } else {
                    boolean b = s.charAt(i) == s.charAt(j);
                    if ((i + 1) == j) {
                        dp[i][j] = b;
                    } else {
                        dp[i][j] = b ? dp[i + 1][j - 1] : false;
                    }
                }
                if (dp[i][j] && (j - i) > (end - start)) {
                    end = j;
                    start = i;
                }
            }
        }
        return s.substring(start, end + 1);
    }

    /**
     * idea 2: 从中间扩展的dp
     *
     * @param s 字符串
     * @return 最长回文子串
     */
    public String longestPalindrome_2(String s) {
        if (s.length() == 0) {
            return "";
        }
        int start = 0, end = 0;
        int len1, len2, len;
        for (int i = 0; i < s.length(); ++i) {
            len1 = getPalindrome(s, i, i);
            len2 = getPalindrome(s, i, i + 1);
            len = (len1 > len2) ? len1 : len2;
            if (len > (end - start)) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    /**
     * 得到以 left, right 为中心的最长回文子串长度
     *
     * @param s     字符串
     * @param left  左中心
     * @param right 右中心
     * @return 最长回文子串长度
     */
    public int getPalindrome(String s, int left, int right) {
        int L = left, R = right;
        while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)) {
            L--;
            R++;
        }
        return R - L - 1;
    }

    /**
     * idea 3: Manacher Algorithm
     *
     * @param s 字符串
     * @return 最长回文子串
     */
    public String longestPalindrome_3(String s) {
        String T = preProcess(s);
        // P 回文子串半径
        int[] P = new int[T.length()];
        // R 回文子串可达最右端，C 回文子串中心
        int R = 0, C = 0;
        for (int i = 1; i < T.length() - 1; ++i) {
            // i 关于 C 的镜像位置
            int i_mirror = 2 * C - i;
            P[i] = R > i ? Math.min(R - i, P[i_mirror]) : 0;
            while (T.charAt(i + P[i] + 1) == T.charAt(i - P[i] - 1)) {
                P[i]++;
            }
            if (i + P[i] > R) {
                C = i;
                R = i + P[i];
            }
        }
        int maxlen = 0, center = 0;
        for (int i = 1; i < T.length() - 1; ++i) {
            if (P[i] > maxlen) {
                maxlen = P[i];
                center = i;
            }
        }
        int start = (center - maxlen) / 2;
        return s.substring(start, start + maxlen);
    }

    /**
     * 预处理字符串 "abc" -> "^#a#b#c#$"
     *
     * @param s 字符串
     * @return 更新后的字符串
     */
    public String preProcess(String s) {
        if (s.length() == 0) {
            return "^$";
        }
        String res = "^";
        for (int i = 0; i < s.length(); ++i) {
            res += "#" + s.charAt(i);
        }
        return (res + "#$");
    }

    public static void main(String[] args) {
        LeetCode5 lc5 = new LeetCode5();
        String str = "babad";
        System.out.println(lc5.longestPalindrome_1(str));
        System.out.println(lc5.longestPalindrome_2(str));
        System.out.println(lc5.longestPalindrome_3(str));
    }
}
