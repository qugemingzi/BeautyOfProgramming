package algorithm;

import java.util.*;

/**
 * @author pray chow
 * 计算感染人数。共有 n 个人，m 个家庭聚会，f 为初始感染编号
 * 每个家庭聚会有 q 个人，之后为 q个人的编号，返回感染人数
 * INPUT :
 *      n m f   n in [2,10000] m in [1,500]
 *      m 行 每行先输入人数 q，在输入 q 个数  q in [1,50]
 * OUTPUT :
 *      感染人数
 * IN :
 *      10 2 0
 *      2 0 3
 *      3 0 1 2
 * OUT :
 *      4
 * idea :
 *      朴素的思想，感染集合 set，家庭聚会 list，每个 list 里是个 ArrayList
 *      更新后的 set.size() 变大则需要继续寻找，否则寻找结束。
 *      可以增加 flag 数组保存家庭聚会是否已经在 set 内，减少遍历次数。
 */
public class Wangyi_3 {

    static int calNumber(Set<Integer> set, List<List<Integer>> list, boolean[] flag, int f) {
        for (int i = 0; i < list.size(); ++i) {
            List<Integer> family_list = list.get(i);
            if (family_list.contains(f)) {
                set.addAll(family_list);
                flag[i] = true;
            }
        }
        if (set.size() == 0) {
            return 0;
        }
        int preSize = 0;
        while (preSize < set.size()) {
            preSize = set.size();
            for (int i = 0; i < list.size(); ++i) {
                if (!flag[i]) {
                    List<Integer> family_list = list.get(i);
                    boolean inset = false;
                    for (int j = 0; j < family_list.size(); ++j) {
                        if (set.contains(family_list.get(j))) {
                            inset = true;
                            break;
                        }
                    }
                    if (inset) {
                        flag[i] = true;
                        set.addAll(family_list);
                    }
                }
            }
        }
        return set.size();
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int m = scan.nextInt();
        int f = scan.nextInt();
        List<List<Integer>> list = new ArrayList<>(m);
        List<Integer> family_list;
        for (int i = 0; i < m; ++i) {
            int q = scan.nextInt();
            family_list = new ArrayList<>(q);
            for (int j = 0; j < q; ++j) {
                family_list.add(scan.nextInt());
            }
            list.add(family_list);
        }
        scan.close();
        Set<Integer> set = new HashSet<>(n);
        boolean[] flag = new boolean[m];
        System.out.println(calNumber(set, list, flag, f));
    }
}
