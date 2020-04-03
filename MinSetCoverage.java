package algorithm;

import java.util.*;

/**
 * @author pray chow
 * 最小集合覆盖问题（最少猎头拿到全部简历）
 * 已知猎头跟简历的对照关系，猎头手中的简历可能重复，找到获取全部简历的最少的猎头
 * INPUT :
 *      A 1 2 3 4
 *      B 2 3 5
 *      C 4 5 6
 *      D 5 6 7 8
 *      E 1 4 6
 * OUTPUT :
 *      [A, D]
 * idea :
 *      回溯，类似全组合 JD_0
 */
public class MinSetCoverage {
    /**
     * @total_cands 总简历集合
     * @names 总猎头集合
     * @stack 栈存储当前选择猎头
     * @res 最少猎头集合
     * @total_nums 总简历数
     * @nums 筛选出的 res 猎头数
     */
    private static Set<Integer> total_cands;
    private static String[] names;
    private static Stack<String> stack ;
    private static List<String> res;
    private static int total_nums, nums;

    /**
     * 检查当前解是否满足条件
     * @param map 猎头简历 map
     * @return 当前解是否覆盖全部简历
     */
    public static boolean isFull(Map<String, List<Integer>> map) {
        Set<Integer> cur_set = new HashSet<>();
        for(String str : stack) {
            cur_set.addAll(map.get(str));
        }
        return cur_set.size() == total_nums;
    }

    /**
     * dfs 回溯
     * @param map 猎头简历 map
     * @param start 猎头起始位置
     */
    public static void dfs(Map<String, List<Integer>> map, int start) {
        // 当前解必须要小于满足条件的集合猎头数才有求的必要
        if(stack.size() < nums) {
            if(isFull(map)) {
                res = new ArrayList<>(stack);
                nums = res.size();
                /* System.out.println("stp: " + res); // 测试输出 */
                return;
            }
            for(int i = start; i < names.length; ++i) {
                stack.push(names[i]);
                dfs(map, start + 1);
                stack.pop();
            }
        }
    }

    /**
     * 查询
     * @param map 猎头简历 map
     * @return 最少的满足条件的猎头集合
     */
    public static List<String> query(Map<String, List<Integer>> map) {
        total_cands = new HashSet<>();
        stack = new Stack<>();
        res = new ArrayList<>();
        nums = map.size();
        names = new String[map.size()];
        int i = 0;
        for(String str : map.keySet()) {
            names[i++] = str;
            total_cands.addAll(map.get(str));
        }
        total_nums = total_cands.size();
        dfs(map, 0);
        return res;
    }

    public static void main(String[] args) {
        HashMap<String, List<Integer>> map1 = new HashMap<>(5);
        map1.put("A", Arrays.asList(1, 2, 3, 4));
        map1.put("B", Arrays.asList(2, 3, 5));
        map1.put("C", Arrays.asList(4, 5, 6));
        map1.put("D", Arrays.asList(5, 6, 7, 8));
        map1.put("E", Arrays.asList(1, 4, 6));
        // [A, D]
        System.out.println(query(map1));

        HashMap<String, List<Integer>> map2 = new HashMap<>(4);
        map2.put("A", Arrays.asList(1, 3, 4, 5));
        map2.put("B", Arrays.asList(1, 4, 6));
        map2.put("C", Arrays.asList(2, 4, 5));
        map2.put("D", Arrays.asList(1, 3, 6));
        // [C, D]
        System.out.println(query(map2));
    }
}
