package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author pray chow
 * 211 变体，给定两个点集，求两点集最近点对 Tencent_2
 * 在 ClosePair Point类基础上增加判断属于哪个集合的属性
 * 时间复杂度 O(nlogn)
 */
public class ClosePairX {
    private ArrayList<Point> arrayList;

    /**
     * 输入点集
     */
    public void inputPoint() {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        while (n-- != 0) {
            arrayList = new ArrayList<Point>();
            int k = scan.nextInt();
            int i = k;
            while (i-- != 0) {
                Point p = new Point();
                p.x = scan.nextDouble();
                p.y = scan.nextDouble();
                p.flag = false;
                arrayList.add(p);
            }
            while (k-- != 0) {
                Point p = new Point();
                p.x = scan.nextDouble();
                p.y = scan.nextDouble();
                p.flag = true;
                arrayList.add(p);
            }
            sortList();
            printPoint();
            DoublePoint close = closePair(0, getPointNum() - 1);
            System.out.println("点 1 : (" + close.a.x + ", " + close.a.y + ")");
            System.out.println("点 2 : (" + close.b.x + ", " + close.b.y + ")");
            System.out.println("最近距离 : " + String.format("%.3f", close.length));
        }
        scan.close();
    }

    /**
     * 点集排序
     */
    public void sortList() {
        Collections.sort(arrayList, (a, b) -> {
            if (a.x != b.x) {
                return (int) (a.x - b.x);
            }
            return (int) (a.y - b.y);
        });
    }

    /**
     * 点集输出
     */
    public void printPoint() {
        Iterator<Point> it = arrayList.iterator();
        while (it.hasNext()) {
            Point p = it.next();
            System.out.println( (p.flag ? "集合B " : "集合A ") + "x : " + p.x + " y : " + p.y);
        }
    }

    /**
     * @return 点集长度
     */
    public int getPointNum() {
        return arrayList.size();
    }

    /**
     * 两点距离
     *
     * @param a 点 a
     * @param b 点 b
     * @return 两点距离
     */
    private double distance(Point a, Point b) {
        return Math.sqrt((a.x - b.x) * (a.x - b.x) +
                (a.y - b.y) * (a.y - b.y));
    }

    /**
     * 递归计算最近点对
     *
     * @param left  左起始
     * @param right 右结束
     * @return 最近点对
     */
    public DoublePoint closePair(int left, int right) {
        DoublePoint dp = new DoublePoint();
        // 一个点
        if (left == right) {
            return dp;
        }
        // 两个点
        if (left + 1 == right) {
            if (arrayList.get(left).flag != arrayList.get(right).flag) {
                dp.a = arrayList.get(left);
                dp.b = arrayList.get(right);
                dp.length = distance(dp.a, dp.b);
            }
            return dp;
        }
        // 分隔子问题
        int mid = (left + right) / 2;
        DoublePoint dp1 = closePair(left, mid);
        DoublePoint dp2 = closePair(mid + 1, right);
        // 获取两个子问题中最小点对
        dp = dp1.length <= dp2.length ? dp1 : dp2;
        // 计算跨越分界的点对
        // 找出位于分界两边距离中心点水平距离小于两边最小点对的点集
        // 只有这些点集可能会比两子集更小
        ArrayList<Integer> temp = new ArrayList<>();
        for (int i = left; i <= right; ++i) {
            if (Math.abs(arrayList.get(mid).x - arrayList.get(i).x) < dp.length) {
                temp.add(i);
            }
        }
        // 区域内点按 y 排序
        Collections.sort(temp, (i1, i2) ->
                (int) (arrayList.get(i1).y - arrayList.get(i2).y));
        // 顺序扫描 temp 数组，依次比较附近 7 个点即可（抛掉自己）
        for (int i = 0; i < temp.size(); ++i) {
            for (int j = i + 1; j < i + 8 && j < temp.size(); ++j) {
                if (arrayList.get(temp.get(j)).y - arrayList.get(temp.get(i)).y < dp.length &&
                        arrayList.get(temp.get(j)).flag != arrayList.get(temp.get(i)).flag) {
                    DoublePoint dp3 = new DoublePoint();
                    dp3.a = arrayList.get(temp.get(i));
                    dp3.b = arrayList.get(temp.get(j));
                    dp3.length = distance(dp3.a, dp3.b);
                    if (dp3.length < dp.length) {
                        dp = dp3;
                    }
                }
            }
        }

        return dp;
    }

    public static void main(String[] args) {
        ClosePairX closePair = new ClosePairX();
        closePair.inputPoint();

    }

    /**
     * 点结构
     */
    private class Point {
        double x;
        double y;
        /**
         * false-集合A true-集合B
         */
        boolean flag;

        public Point() {
            x = 0.0;
            y = 0.0;
            flag = false;
        }
    }

    /**
     * 点对结构
     */
    private class DoublePoint {
        Point a = new Point();
        Point b = new Point();
        double length = Double.MAX_VALUE;
    }
}
