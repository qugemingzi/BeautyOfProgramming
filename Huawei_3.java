package algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 * @author pray chow
 * 给定栈区分配及函数调用链，求出调用链栈总和，例如入口函数 A(100) -> B(50) -> C(120)
 * 则 A->B->C 函数调用链栈总和为 100+50+120 = 270
 * INPUT :
 *      函数个数 n，每个函数可调用函数个数      n < 100
 *      函数 1，函数 1 栈大小 可调用函数 1...m
 *      ...
 *      函数 n，函数 n 栈大小 可调用函数 1...k
 * OUTPUT :
 *      所有调用链栈总和最大值，入口函数可能不唯一，
 *      所有调用链中只要存在一个递归调用 (1->2->3->1)，输出 R
 *      若调用链中有函数未给出调用栈大小，输出 NA
 * IN :
 *      5 2 3 1 0 0
 *      1 20 2 3
 *      2 30 3 4 5
 *      3 50 4
 *      4 60
 *      5 80
 * OUT :
 *      160
 * explanation :
 *      1->2->3->4  160
 *      1->2->4     110
 *      1->2->5     130
 *      1->3->4     130
 */
public class Huawei_3 {
    static int maxStack = 0;
    static boolean isR = false;
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int[] fucNums = new int[n + 1];
        for (int i = 1; i <= n; ++i) {
            fucNums[i] = scan.nextInt();
        }
        scan.nextLine();
        String[] infoNums = new String[n + 1];
        for (int i = 1; i <= n; ++i) {
            infoNums[i] = scan.nextLine();
        }
        scan.close();
        // graph generator && check NA
        List<List<Integer>> list = new ArrayList<>(n + 1);
        List<Integer> vertex0 = new ArrayList<>();
        list.add(vertex0);
        int[] inDegree = new int[n + 1];
        int[] stackSize = new int[n + 1];
        for (int i = 1; i <= n; ++i) {
            String[] info = infoNums[i].split(" ");
            if (info.length != 2 + fucNums[i]) {
                System.out.println("NA");
                return;
            }
            stackSize[i] = Integer.parseInt(info[1]);
            List<Integer> edge = new ArrayList<>(info.length - 2);
            for (int j = 2; j < info.length; ++j) {
                int vertex = Integer.parseInt(info[j]);
                inDegree[vertex]++;
                edge.add(vertex);
            }
            list.add(edge);
        }
        // calculate && check R
        boolean[] flag = new boolean[n + 1];
        Stack<Integer> stack = new Stack<>();
        for (int i = 1; i <= n; ++i) {
            if (inDegree[i] == 0) {
                dfs(stack, i, 0, list, stackSize, flag);
                if (isR) {
                    System.out.println("R");
                    return;
                }
            }
        }
        // check R
        for (int i = 1; i <= n; ++i) {
            if (!flag[i]) {
                System.out.println("R");
                return;
            }
        }
        // print
        System.out.println(maxStack);
    }

    public static void dfs(Stack<Integer> stack, int vertex, int curStack, List<List<Integer>> list, int[] stackSize, boolean[] flag) {
        if (isR) {
            return;
        }
        if (stack.contains(vertex)) {
            isR = true;
            return;
        }
        curStack += stackSize[vertex];
        List<Integer> edge = list.get(vertex);
        stack.push(vertex);
        if (edge.size() == 0) {
            System.out.println("Stack : " + stack);
            System.out.println("Current Stack Size : " + curStack);
            maxStack = Math.max(curStack, maxStack);
        } else {
            for (int next : edge) {
                dfs(stack, next, curStack, list, stackSize, flag);
            }
        }
        stack.pop();
        flag[vertex] = true;
    }
}
