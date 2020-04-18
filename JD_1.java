package algorithm;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author pray chow
 * 给定六个板，判断是否能构成长方体
 * 输入 n 组数据，每组有 12 条边
 * 输出该组样例能否组成长方形
 * 先排序再比对，可分为 3 种情况(abc, aaa, ac)
 */
public class JD_1 {

    public static boolean isRectangle(int[][] board) {
        Arrays.sort(board, (o1, o2) -> {
                if (o1[0] < o2[0]) {
                    return -1;
                } else if (o1[0] == o1[0]) {
                    return o1[1] - o2[1];
                } else {
                    return 1;
                }
                });
        for (int i = 0; i < 6; ++i) {
            System.out.println(board[i][0] + " " + board[i][1]);
        }
        int a = board[0][0];
        int b = board[0][1];
        int c = board[5][1];
        if (board[0][0] != a || board[0][1] != b || board[1][0] != a || board[1][1] != b) {
            return false;
        }
        if (board[4][0] != b || board[4][1] != c || board[5][0] != b || board[5][1] != c) {
            return false;
        }
        if ((a == b && b == c) || (a < b && b < c)) {
            if (board[2][0] != a || board[2][1] != c || board[3][0] != a || board[3][1] != c) {
                return false;
            }
        } else if (a == b) {
            if (board[2][0] != a || board[3][0] != a) {
                return false;
            }
            if (board[2][1] != board[3][1]){
                return false;
            } else if (board[2][1] != c && board[2][1] != a){
                return false;
            }
        } else if (b == c) {
            if (board[2][1] != c || board[3][1] != c) {
                return false;
            }
            if (board[2][1] != board[3][1]){
                return false;
            } else if (board[2][1] != c && board[2][1] != a){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scan= new Scanner(System.in);
        int n = scan.nextInt();
        boolean[] res = new boolean[n];
        for (int i = 0; i < n; ++i) {
            int[][] board = new int[6][2];
            for (int j = 0; j < 6; ++j) {
                int first = scan.nextInt();
                int second = scan.nextInt();
                board[j][0] = Math.min(first, second);
                board[j][1] = Math.max(first, second);
            }
            res[i] = isRectangle(board);
        }
        for (int i = 0; i < n - 1; ++i) {
            System.out.println(res[i] ? "POSSIBLE" : "IMPOSSIBLE");
        }
        System.out.print(res[n - 1] ? "POSSIBLE" : "IMPOSSIBLE");
        scan.close();
    }
}
