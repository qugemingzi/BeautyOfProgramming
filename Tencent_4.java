package algorithm;

import java.util.Scanner;
import java.util.Stack;

/**
 * @author pray chow
 * 双栈实现队列：1-add X 2-poll 3-peek
 * INPUT :
 *      n 操作数   n in [1, 1e6]
 *      n 行操作   X in [-1e6, 1e6]
 * OUTPUT :
 *      peek 元素     操作合法
 * IN :
 *      6
 *      add 1
 *      add 2
 *      add 3
 *      peek
 *      poll
 *      peek
 * OUT :
 *      1
 *      2
 * stack1 用来 push，stack2 用来 peek 和 pop。75%
 */
public class Tencent_4 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        String[] str = new String[n];
        scan.nextLine();
        for (int i = 0; i < n; ++i) {
            str[i] = scan.nextLine();
        }
        scan.close();
        Stack<Integer> s1 = new Stack<>();
        Stack<Integer> s2 = new Stack<>();
        for (int i = 0; i < n; ++i) {
            if (str[i].equals("peek")) {
                if (s2.isEmpty()) {
                    while (!s1.isEmpty()) {
                        s2.push(s1.pop());
                    }
                }
                System.out.println(s2.isEmpty() ? -1 : s2.peek());
            } else if (str[i].equals("poll")) {
                if (s2.isEmpty()) {
                    while (!s1.isEmpty()) {
                        s2.push(s1.pop());
                    }
                }
                if (!s2.isEmpty()) {
                    s2.pop();
                }
            } else {
                String[] temp = str[i].split(" ");
                s1.push(Integer.parseInt(temp[1]));
            }
        }
    }
}
