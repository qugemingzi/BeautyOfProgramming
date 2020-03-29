package algorithm;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * @author pray chow
 * 逃离迷宫，有起点和终点，每次可以选择四个方向走一格或者飞行到关于当前点中心对称的格，且每一次移动的目的地不能存在障碍物。
 * 具体地说，n行m列，当前位于A(x,y)，中心对称的点B(x’,y’)满足
 *      x + x’ = n + 1 且 y + y’ = m + 1
 * 需要注意的是飞行方法最多使用5次。
 * INPUT :
 * 		n行m列，接下来n行，每行长度为m的字符描述该迷宫，其中
 * 			.   <=> 通路
 * 			#  <=> 障碍
 * 			S  <=> 起点
 * 			E  <=> 终点
 * 		保证只有一个S和E，n,m in [2,500]
 * OUTPUT :
 * 		最少花费步数到达终点，否则输出-1
 * INPUT :
 * 		4 4
 * 		#S . .
 * 		E# . .
 * 		#. . .
 * 		.# . .
 * OUTPUT :
 * 		4		(1,2) jump (4,3) up (3,3) right (3,4) jump (2,1)
 * idea :
 *      bfs 搜索，栈保存状态，将每个位置抽象为 Node类，包含坐标、已走步数、剩余飞行次数
 *      move[] 搜索的四个方向，visit[][] 记录是否被搜索过。
 *      Java中是值引用，每次Node对象插入栈时都需要新建，否则对象值会乱
 */
public class Ali_4 {

    /** N*M 大小的地图，R 为限制 jump 次数，D 为四个方向
     *  起始 X 坐标和 Y 坐标
     *  结束 X 坐标和 Y 坐标
     *  四种走一步的的方向
     *  输入的地图
     *  记录是否已经遍历过
     */
    static int N, M, R = 5, D = 4;
    static int startX, startY;
    static int endX, endY;
    static int[][] move = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}};
    static char[][] map;
    static int[][]visit;

    /**
     * 检查是否在地图范围内
     * @param x X 坐标
     * @param y Y 坐标
     * @return (x, y) 是否在地图内
     */
    boolean check(int x, int y) {
        return ((x >= 1) && (x <= N) && (y >= 1) && (y <= M));
    }

    /**
     * bfs 搜索可能到达的路径，到达终点返回已走的最快的步数
     * @return 最快的步数，到达不了则返回 -1
     */
    int bfs() {
        Queue<Node> queue = new LinkedList<>();
        Node root = new Node();
        root.setX(startX); root.setY(startY);
        root.setCount(R); root.setStep(0);
        visit[startX][startY] = 1;
        queue.offer(root);
        while(!queue.isEmpty()) {
            Node cur = queue.peek();
            if(cur.getX() == endX && cur.getY() == endY) {
                return cur.getStep();
            }
            queue.poll();
            for(int i = 0; i < D; ++i) {
                Node next = new Node();
                next.setX(cur.getX() + move[i][0]); next.setY(cur.getY() + move[i][1]);
                next.setCount(cur.getCount()); next.setStep(cur.getStep() + 1);
                if(check(next.getX(), next.getY()) && visit[next.getX()][next.getY()] == 0 && map[next.getX()][next.getY()] != '#') {
                    visit[next.getX()][next.getY()] = 1;
                    queue.offer(next);
                }
            }
            if(cur.getCount() >= 1) {
                Node jump = new Node();
                jump.setX(N + 1 - cur.getX()); jump.setY(M + 1 - cur.getY());
                jump.setCount(cur.getCount() - 1); jump.setStep(cur.getStep() + 1);
                if(check(jump.getX(), jump.getY()) && visit[jump.getX()][jump.getY()] == 0 && map[jump.getX()][jump.getY()] != '#') {
                    visit[jump.getX()][jump.getY()] = 1;
                    queue.offer(jump);
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        N = scan.nextInt(); M = scan.nextInt();
        map = new char[N + 1][M + 1];
        visit = new int[N + 1][M + 1];
        String str;
        for(int i = 1; i <= N; ++i) {
            str = scan.next();
            for(int j = 1; j <= M; ++j) {
                map[i][j] = str.charAt(j-1);
                if(map[i][j] == 'S') {
                    startX = i; startY = j;
                } else if(map[i][j] == 'E') {
                    endX = i; endY = j;
                }
            }
        }
        System.out.println(new Ali_4().bfs());
        scan.close();
    }

    /**
     * 结点类
     */
    private class Node{
        /**
         * X 坐标，Y 坐标
         * 已走的步数，jump 次数限制(初始为 5)
         */
        int x;
        int y;
        int step;
        int count;

        public int getCount() {
            return count;
        }

        public int getStep() {
            return step;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public void setStep(int step) {
            this.step = step;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}
