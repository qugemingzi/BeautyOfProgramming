package algorithm;

/**
 * @author pray chow
 * 康威生命游戏 LeetCode 289 最强大脑一期小题目-20200513
 * 给定一个只有 0/1 组成的二维数组，1 <=> 细胞活，0 <=> 细胞死
 * 规则为计算该细胞周边的 8 个细胞的存活数量 count
 *      count < 2 || count > 3  环境太松/太挤，细胞死
 *      count == 2              环境还可，细胞维持原状态
 *      count == 3              环境最佳，细胞活
 */
public class GameOfLife {
    int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
    int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
    int SIZE = 8;

    public int nums(int[][] res, int i, int j) {
        int count = 0;
        for (int k = 0; k < SIZE; ++k) {
            count += res[i + dx[k]][j + dy[k]];
        }
        return count;
    }

    /**
     * 阔一圈减少判断是否出圈
     * @param board 数组
     */
    public void gameOfLife(int[][] board) {
        int n = board.length, m = board[0].length;
        int[][] res = new int[n + 2][m + 2];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                res[i + 1][j + 1] = board[i][j];
            }
        }
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= m; ++j) {
                int count = nums(res, i, j);
                if (count < 2 || count > 3) {
                    board[i - 1][j - 1] = 0;
                } else if (count == 3) {
                    board[i - 1][j - 1] = 1;
                }
            }
        }
        print(board);
    }

    /**
     * 巧用位运算不用备份数组，位存储细胞生死状态
     * @param board 数组
     */
    public void gameOfLife_2(int[][] board) {
        int n = board.length, m = board[0].length;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                int count = 0;
                for (int k = 0; k < SIZE; ++k) {
                    int ni = i + dx[k];
                    int nj = j + dy[k];
                    if (ni >= 0 && ni < n && nj >= 0 && nj < m) {
                        count += (board[ni][nj] & 1);
                    }
                }
                if (count == 2 && board[i][j] == 1 || count == 3) {
                    board[i][j] |= 2;
                }
            }
        }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                board[i][j] >>= 1;
            }
        }
        print(board);
    }

    public void print(int[][] board) {
        System.out.println("******************");
        int n = board.length, m = board[0].length;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                System.out.print(board[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        GameOfLife game = new GameOfLife();
        int[][] board = {
                {0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0},
                {0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0},
                {0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1},
                {0, 0, 1, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0},
                {0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0},
                {0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1},
                {0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0},
                {0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
                {1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0},
                {1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0}};
        game.print(board);
        // 一次周期后为 77-23+10=64
        game.gameOfLife(board);
        game.gameOfLife_2(board);
    }
}
