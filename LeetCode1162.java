package algorithm;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author pray chow
 * LeetCode 1162 多源bfs
 */
public class LeetCode1162 {

    /**
     * 检查是否在地图界内
     * @param i x 坐标
     * @param j y 坐标
     * @param n 行限制
     * @param m 列限制
     * @return 是否在地图界内
     */
    boolean check(int i, int j, int n, int m) {
        return i >= 0 && i < n && j >= 0 && j < m;
    }

    /**
     * 计算最大距离的海洋
     * @param grid 地图
     * @return 距离陆地的海洋中最小值的最大值
     */
    public int maxDistance(int[][] grid) {
        int[][] dir = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}};
        int count = 0, n = grid.length, m = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        for(int i = 0; i < n; ++i) {
            for(int j = 0; j < m; ++j) {
                if(grid[i][j] == 1) {
                    queue.offer(new int[]{i, j});
                }
            }
        }
        if(queue.size() == n*m || queue.size() == 0) {
            return -1;
        }
        while(!queue.isEmpty()) {
            int[] cur = queue.peek();
            count = grid[cur[0]][cur[1]];
            queue.poll();
            for(int i = 0; i < 4; ++i) {
                int nextX = cur[0] + dir[i][0];
                int nextY = cur[1] + dir[i][1];
                if(check(nextX, nextY, n, m) && grid[nextX][nextY] == 0) {
                    grid[nextX][nextY] = count + 1;
                    queue.offer(new int[] {nextX, nextY});
                }
            }
        }
        return count - 1;
    }

    public static void main(String[] args) {
        LeetCode1162 lc = new LeetCode1162();
        int[][] grid = {{1, 0, 1}, {0, 0, 0}, {1, 0, 1}};
        System.out.println(lc.maxDistance(grid));
    }
}
