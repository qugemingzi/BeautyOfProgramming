package algorithm;

/**
 * @author pray chow
 * LeetCode 52 八皇后问题 位运算
 * N*N格子里放置皇后，要求一个皇后的一行一列以及对角线不可以有其他的皇后，一共需要放置 N 个皇后，
 * 请问有多少种放法，打印每一种方法。
 */
public class EightQueue {

    /**
     * count : 方法数
     * N     : N 皇后
     * map   : 皇后放置表
     */
    static int count = 0;
    static int N;
    static int[][] map;

    /**
     * 递归求解 N 皇后问题
     * @param row 要放置的行数
     * @param col 之前皇后覆盖的列
     * @param left 之前皇后覆盖的左对角线
     * @param right 之前皇后覆盖的右对角线
     */
    static void queueSettle(int row, int col, int left, int right) {
        if(row >= N) {
            // 遍历到最后一行说明已经找到符合的条件了
            System.out.println("No " + (++count) + " :");
            printmap();
            return;
        }
        // 取出当前可放置皇后的格子
        // 后面的 MASK 是将高位置为 0，低位是 1 则可以放置
        int bits = (~(col | left | right)) & ((1 << N) - 1);
        while(bits > 0) {
            // 每次从当前行可用的格子中取出最右边位为 1 的格子放置皇后
            // -bits 二进制表示 取反+1
            int pos = bits & -bits;
            // v=log2(pos)，即 map 中的位置 map[row][v]
            int v = (int) (Math.log(pos) / Math.log(2));
            map[row][v] = 1;
            // 紧接着下一行放置皇后
            queueSettle(row + 1, col | pos, (left | pos) << 1, (right | pos) >> 1);
            // 当前行最右边格子用完了，将其置为 0，代表这个格子已经遍历过
            bits = bits & (bits - 1);
            // 恢复 map 值
            map[row][v] = 0;
        }
    }

    /**
     * 输出 map 表
     */
    static void printmap() {
        for(int i = 0; i < N; ++i) {
            for(int j = 0; j < N; ++j) {
                System.out.print(map[i][j] + " ");
            } System.out.println();
        }
    }

    public static void main(String[] args) {
        N = 5;
        map = new int[N][N];
        queueSettle(0, 0, 0, 0);
        System.out.println("Total Number of " + N + " Queue Problem is " + count);
    }
}
