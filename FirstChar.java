package algorithm;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author pray chow
 * 有一字符串，所有字符都是在ascii编码范围内，求出字符串中出现频率最高的
 * 字符，如果存在多个输出最先出现的字符。
 * idea : LinkedHashMap 记录个数，并记录插入顺序，两次遍历
 *        一次遍历可以么？倒序？
 */
public class FirstChar {

    /**
     * 正序遍历，LinkedHashMap 保证存储 key 有序
     * @param str 字符串
     * @return 输出频率最高且最先出现的字符
     */
    public char getMaxOccurChar(String str) {
        int maxCount = 1;
        if(str.length() == 0) {
            return '!';
        }
        char res = str.charAt(0);
        Map<Character, Integer> map = new LinkedHashMap<>();
        for(int i = 0; i < str.length(); ++i) {
            char c = str.charAt(i);
            if(map.get(c) == null) {
                map.put(c, 1);
            } else {
                map.put(c, map.get(c) + 1);
            }
        }
        for(Map.Entry<Character, Integer> entry : map.entrySet()) {
            if(entry.getValue() > maxCount) {
                res = entry.getKey();
                maxCount = entry.getValue();
            }
        }
        return res;
    }

    /**
     * 倒序遍历，HashMap 即可，不需要保证顺序
     * @param str 字符串
     * @return 输出频率最高且最先出现的字符
     */
    public char getMaxOccurChar_2(String str) {
        int maxCount = 1;
        if(str.length() == 0) {
            return '!';
        }
        char res = str.charAt(0);
        Map<Character, Integer> map = new HashMap<>();
        for(int i = str.length() - 1; i >= 0; --i) {
            char c = str.charAt(i);
            Integer count = map.get(c);
            if(count == null) {
                map.put(c, 1);
                count = 1;
            } else {
                map.put(c, count + 1);
                count += 1;
            }
            if(count >= maxCount) {
                maxCount = count;
                res = c;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        FirstChar fc = new FirstChar();
        System.out.println(
                fc.getMaxOccurChar("hello world, every body!"));
        System.out.println(
                fc.getMaxOccurChar_2("hello world, every body!"));
        System.out.println(fc.getMaxOccurChar("aaaahfdfbbbbbbbbbb"));
        System.out.println(fc.getMaxOccurChar_2("aaaahfdfbbbbbbbbbb"));
        System.out.println(fc.getMaxOccurChar("1357924680"));
        System.out.println(fc.getMaxOccurChar_2("1357924680"));
    }
}
