package algorithm;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * @author pray chow
 * n 座城市，每个城市 i 的传送门通往城市 a[i]。当位于城市 i 时，可执行一下三个操作：
 *  花费 A，前往城市 a[i];
 *  花费 B，a[i]--(a[i] > 0);
 *  花费 C，a[i]++(a[i] < n).
 * 问从城市 1 到城市 n，至少需要花费多少？
 * INPUT :
 *      n A B C     n in [1, 10000] A B C in [1, 100000]
 *      n 个整数表示 a[1]...a[n]     a[i] in [1, n]
 * OUTPUT :
 *      最少花费
 * IN :
 *      7 1 1 1
 *      3 6 4 3 4 5 6
 * OUT :
 *      4
 * explanation :
 *  B a[1]-- = 2;
 *  A goto city 2;
 *  C a[2]++ = 7;
 *  A goto city 7.
 * idea :
 *      刚开始想用 dp，不过发现城市可能往后走也可能向回走，tcl...
 *      完全图的单源最短路径(dalao)，tql...
 *      dijsktra + heap O(n^2)
 */
public class Duxiaoman_2 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int a = scan.nextInt();
        int b = scan.nextInt();
        int c = scan.nextInt();
        int[] ai = new int[n + 1];
        for (int i = 1; i <= n; ++i) {
            ai[i] = scan.nextInt();
        }
        scan.close();
        boolean[] visited = new boolean[n + 1];
        int[] distance = new int[n + 1];
        Arrays.fill(distance, Integer.MAX_VALUE);
        int res = a + (n - ai[1]) * c;
        distance[1] = 0;
        PriorityQueue<Node> queue = new PriorityQueue<Node>(Comparator.comparingInt(o -> o.len));
        queue.add(new Node(1, 0));
        while (! queue.isEmpty()) {
            Node node = queue.poll();
            int cur = node.cur;
            if (cur == n) {
                res = Math.min(res, node.len);
                break;
            }
            if (visited[cur]) {
                continue;
            }
            visited[cur] = true;
            for (int i = 2; i <= n; ++i) {
                if (visited[i]) {
                    continue;
                }
                int cost = 0;
                if (ai[cur] < i) {
                    cost = (i - ai[cur]) * c;
                } else {
                    cost = (ai[cur] - i) * b;
                }
                if (distance[i] > node.len + cost + a) {
                    if (node.len + cost + a >= res) {
                        continue;
                    }
                    distance[i] = node.len + cost + a;
                    queue.add(new Node(i, distance[i]));
                }
            }
        }
        System.out.println(res);
    }

    static class Node{
        int cur;
        int len;
        public Node() {}
        public Node(int cur, int len) {
            this.cur = cur;
            this.len = len;
        }
    }
}
