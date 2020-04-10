package algorithm;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * @author pray chow
 * n*m 地图上有 0(怪物) 和 1(英雄)，每个英雄计算离他最近的怪物距离，每个
 * 位置离上下左右相邻距离为 1。若当前位置为怪物，输出 0。题目保证存在怪物。
 * INPUT :
 *      n m     in [1,1000]
 *      接下来 n 行，每行 m 个 0/1 整数
 * OUTPUT :
 *      n 行 m 列表示答案，行末无空格
 * IN :
 *      3 3
 *      1 0 1
 *      0 1 0
 *      1 0 1
 * OUT :
 *      1 0 1
 *      0 1 0
 *      1 0 1
 * idea :
 *      每个英雄位置 bfs 寻找最近的怪物
 */
public class Wangyi_2 {
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};

    static boolean isLegal (int i, int j, int n, int m) {
        return i >= 0 && i < n && j >= 0 && j < m;
    }

    static int minDistance(int i, int j, int[][] map) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{i, j});
        int res = 0;
        while (! queue.isEmpty()) {
            int[] cur = queue.poll();
            int curX = cur[0], curY = cur[1];
            if (map[curX][curY] == 0) {
                res = Math.abs(i - curX) + Math.abs(j - curY);
                break;
            }
            for (int k = 0; k < 4; ++k) {
                int nextX = curX + dx[k];
                int nextY = curY + dy[k];
                if (isLegal(nextX, nextY, map.length, map[0].length)) {
                    queue.offer(new int[]{nextX, nextY});
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int m = scan.nextInt();
        int[][] map = new int[n][m];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                map[i][j] = scan.nextInt();
            }
        }
        int[][] res = new int[n][m];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (map[i][j] != 0) {
                    res[i][j] = minDistance(i, j, map);
                }
            }
        }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m - 1; ++j) {
                System.out.print(res[i][j] + " ");
            } System.out.println(res[i][m - 1]);
        }
        scan.close();
    }
}
