package algorithm;

import java.util.Scanner;
import java.util.Stack;

/**
 * @author pray chow
 * LeetCode 77 全组合，输出所有的 C(n,m) m<=n
 * printAll() 递归，printAll2() 加剪枝的递归
 */
public class JD_0 {

   static Stack<Integer> stack = new Stack<>();
   static Stack<Integer> stack2 = new Stack<>();

    public static void printAll(int n, int m, int start) {
        if(stack.size() == m) {
            for(int temp : stack) {
                System.out.print(temp + " ");
            }
            System.out.println();
            return;
        }
        for(int i = start; i <= n; ++i) {
            stack.push(i);
            printAll(n, m, i+1);
            stack.pop();
        }
    }

    public static void printAll2(int n, int m, int start) {
        if(stack2.size() == m) {
            for(int temp : stack2) {
                System.out.print(temp + " ");
            }
            System.out.println();
            return;
        }
        // 此时 i 最大为 n - m + 1 + stack2.size()
        for(int i = start; i <= n - m + 1 + stack2.size(); ++i) {
            stack2.push(i);
            printAll2(n, m, i+1);
            stack2.pop();
        }
    }


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int m = scan.nextInt();
        printAll(n, m, 1);
        printAll2(n, m, 1);
        scan.close();
    }
}
