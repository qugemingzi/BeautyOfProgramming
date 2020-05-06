package algorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author pray chow
 * LeetCode 945 数组唯一最小增量，不过数组长度不同
 * 给定 n 个数，要求给这些数加数，使得所有数没有相同的
 * INPUT :
 *      n             in [1, 1e5]
 *      n 个数 Ai     in [1, 1e9]
 * OUTPUT :
 *      最少需要增加数
 * IN :
 *      4           5
 *      1 3 1 4     3 4 3 4 5
 * OUT :
 *      1           6
 * 刚开始想用数组存储，相当于map的作用，即 new int[1e9]，but OutOfMemoryError
 * 之后用 map 替换，过了 85%，同志还需努力。。。
 */
public class PDD_1 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        Map<Integer, Integer> map = new HashMap<>(n);
        int min = (int) 1e9, max = 1;
        for (int i = 0; i < n; ++i) {
            int tmp = scan.nextInt();
            map.put(tmp, map.getOrDefault(tmp, 0) + 1);
            min = Math.min(min, tmp);
            max = Math.max(max, tmp);
        }
        scan.close();
        long res = 0;
        for (int i = min; i <= max; ++i) {
            if (map.containsKey(i) && map.get(i) > 1) {
                res += (long) map.get(i) - 1;
                map.put(i + 1, map.getOrDefault(i + 1, 0) + map.get(i) - 1);
            }
        }
        int i = max + 1;
        if (map.containsKey(i) && map.get(i) > 1) {
            int d = map.get(i) - 1;
            res += (long) (d + 1) * d / 2;
        }
        System.out.println(res);
    }
}
