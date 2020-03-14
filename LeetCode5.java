public class LeetCode5 {
    /*
        最长回文子串
     */

    // idea 1: dp表
    public String longestPalindrome_1(String s){
        if(s.length() == 0){ return ""; }
        int start = 0, end = 0;
        boolean[][] dp = new boolean[s.length()][s.length()];
        for(int j = 0; j < s.length(); ++j){
            for(int i = 0; i <= j; ++i){
                if(i == j){ dp[i][i] = true; }
                else {
                    boolean b = s.charAt(i) == s.charAt(j);
                    if((i+1) == j){
                        dp[i][j] = b;
                    }else{
                        dp[i][j] = b ? dp[i+1][j-1] : false;
                    }
                }
                if(dp[i][j] && (j-i) > (end-start)){
                    end = j; start = i;
                }
            }
        }
        return s.substring(start, end+1);
    }

    // idea 2: 从中间扩展的dp
    public String longestPalindrome_2(String s) {
        if(s.length() == 0){ return ""; }
        int start = 0, end = 0;
        int len1= 0, len2 = 0, len = 0;
        for(int i = 0; i < s.length(); ++i){
            len1 = getPalindrome(s, i, i);
            len2 = getPalindrome(s, i, i+1);
            len = (len1 > len2) ? len1 : len2;
            if(len > (end-start)){
                start = i-(len-1)/2;
                end = i + len/2;
            }
        }
        return s.substring(start, end+1);
    }

    public int getPalindrome(String s, int left, int right){
        int L = left, R = right;
        while(L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)){
            L--;
            R++;
        }
        return R-L-1;
    }

    // idea 3: Manacher Algorithm
    public String longestPalindrome_3(String s) {
        String T = preProcess(s);
        int[] P = new int[T.length()];
        int R = 0, C = 0;
        for(int i = 1; i < T.length()-1; ++i){
            int i_mirror = 2 * C - i;
            P[i] = R > i ? Math.min(R-i, P[i_mirror]) : 0;
            while(T.charAt(i+P[i]+1) == T.charAt(i-P[i]-1)){
                P[i]++;
            }
            if(i + P[i] > R){
                C = i;
                R = i + P[i];
            }
        }
        int maxlen = 0, center = 0;
        for(int i = 1; i < T.length()-1; ++i){
            if(P[i] > maxlen){
                maxlen = P[i];
                center = i;
            }
        }
        int start = (center - maxlen) / 2;
        return s.substring(start, start+maxlen);
    }

    public String preProcess(String s){
        if(s.length() == 0){ return "^$"; }
        String res = "^";
        for(int i = 0; i < s.length(); ++i){
            res += "#" + s.charAt(i);
        }
        return (res+"#$");
    }
}
