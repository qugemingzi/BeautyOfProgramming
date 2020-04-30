package algorithm;

import java.util.HashMap;

/**
 * @author pray chow
 * LeetCode 13 Roman -> int
 */
public class LeetCode13 {
    /**
     * 两两比对，先比较长度为 2 的，后比较长度为 1 的
     * @param s roman
     * @return int
     */
    public int romanToInt(String s) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("I", 1);
        map.put("IV", 4);
        map.put("V", 5);
        map.put("IX", 9);
        map.put("X", 10);
        map.put("XL", 40);
        map.put("L", 50);
        map.put("XC", 90);
        map.put("C", 100);
        map.put("CD", 400);
        map.put("D", 500);
        map.put("CM", 900);
        map.put("M", 1000);
        int res = 0;
        for (int i = 0; i < s.length(); ) {
            if (i + 1 < s.length() && map.containsKey(s.substring(i, i + 2))) {
                res += map.get(s.substring(i, i + 2));
                i += 2;
            } else {
                res += map.get(s.substring(i, i + 1));
                i++;
            }
        }
        return res;
    }

    private int getValue(char c) {
        switch(c) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default : return 0;
        }
    }

    /**
     * 左减右加，逐位比较
     * @param s roman
     * @return int
     */
    public int romanToInt_2(String s) {
        int res = 0;
        char[] c = s.toCharArray();
        int pre = getValue(c[0]);
        for (int i = 1; i < c.length; ++i) {
            int cur = getValue(c[i]);
            if (pre < cur) {
                res -= pre;
            } else {
                res += pre;
            }
            pre = cur;
        }
        res += pre;
        return res;
    }

    public static void main(String[] args) {
        LeetCode13 lc = new LeetCode13();
        System.out.println(lc.romanToInt("MCMXCIV"));
        System.out.println(lc.romanToInt_2("MCMXCIV"));
    }
}
