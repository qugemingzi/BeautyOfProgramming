package algorithm;

import java.util.*;

/**
 * @author pray chow
 * 模拟队列：1-PUSH X 2-TOP 3-POP 4-SIZE 5-CLEAR
 * 输出：TOP 有则输出，无则输出"-1"
 *       POP 无则输出"-1"
 *       SIZE 队列大小
 * INPUT :
 *      n 组测试数据
 *      每组数据有 k 次操作
 *      k 行操作
 * OUTPUT :
 *      同上
 * IN :
 *      2
 *      7
 *      PUSH 1
 *      PUSH 2
 *      TOP
 *      POP
 *      TOP
 *      POP
 *      POP
 *      5
 *      PUSH 1
 *      PUSH 2
 *      SIZE
 *      POP
 *      SIZE
 * OUT :
 *      1   first TOP
 *      2   second TOP
 *      -1  third POP
 *      2   first SIZE
 *      1   second SIZE
 * 无脑，100%
 */
public class Tencent_1 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        List<String[]> list = new ArrayList<>(n);
        for (int i = 0; i < n; ++i) {
            int k = scan.nextInt();
            scan.nextLine();
            String[] str = new String[k];
            for (int j = 0; j < k; ++j) {
                str[j] = scan.nextLine();
            }
            list.add(str);
        }
        scan.close();
        for (int i = 0; i < n; ++i) {
            String[] str = list.get(i);
            Queue<Integer> queue = new LinkedList<>();
            for (int j = 0; j < str.length; ++j) {
                if (str[j].equals("TOP")) {
                    System.out.println(queue.size() > 0 ? queue.peek() : -1);
                } else if (str[j].equals("POP")) {
                    if (queue.size() > 0) {
                        queue.poll();
                    } else {
                        System.out.println(-1);
                    }
                } else if (str[j].equals("SIZE")) {
                    System.out.println(queue.size());
                } else if (str[j].equals("CLEAR")) {
                    queue.clear();
                } else {
                    String[] temp = str[j].split(" ");
                    queue.offer(Integer.parseInt(temp[1]));
                }
            }
        }
    }
}
