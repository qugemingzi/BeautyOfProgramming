package algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pray chow
 * LeetCode 51 八皇后问题 位运算
 * N*N格子里放置皇后，要求一个皇后的一行一列以及对角线不可以有其他的皇后，一共需要放置 N 个皇后，
 * 询问各种方法，有皇后的位置为"Q"，没有的位置为"."。
 */
public class LeetCode51 {
    int[][] map;
    List<int[][]> list = new ArrayList<>();

    /**
     * @param n n-皇后
     * @return 输出各种方法
     */
    public List<List<String>> solveNQueens(int n) {
        map = new int[n][n];
        queueSettle(n, 0, 0, 0, 0);
        List<List<String>> ls = new ArrayList<>();
        List<String> tls;
        String str = "";
        for(int[][] tmp : list) {
            tls = new ArrayList<>();
            for(int i = 0; i < n; ++i) {
                for(int j = 0; j < n; ++j) {
                    str += (tmp[i][j] == 0) ? "." : "Q";
                }
                tls.add(str);
                str = "";
            }
            ls.add(tls);
        }
        return ls;
    }

    public void queueSettle(int n, int row, int col, int left, int right) {
        if(row >= n) {
            int[][] tmp = new int[n][n];
            for(int i = 0; i < n; ++i){
                for(int j = 0; j < n; ++j){
                    tmp[i][j] = map[i][j];
                }
            }
            list.add(tmp);
            return;
        }
        int bits = (~(col | left | right)) & ((1 << n) - 1);
        while(bits > 0) {
            int pos = bits & -bits;
            int v = (int) (Math.log(pos) / Math.log(2));
            map[row][v] = 1;
            queueSettle(n, row+1, col|pos, (left|pos) << 1, (right|pos) >> 1);
            bits &= (bits - 1);
            map[row][v] = 0;
        }
    }

    public static void main(String[] args) {
        LeetCode51 nq= new LeetCode51();
        System.out.println(nq.solveNQueens(5));
    }
}
