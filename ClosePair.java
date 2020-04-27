package algorithm;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author pray chow
 * 211 寻找最近点对
 * 给定一组点集，找到最近的点对。
 * 暴力法 时间复杂度 O(n^2)
 * 分治法 按照 x 轴排序，分为 [left, mid] [mid, right] 两部分
 * 合并最近点对为两部分的最近点对 + 两者间的最近点对，minLeft, minRight 分别为两个子集最近点对
 * min = min{minLeft, minRight}, 在此基础上两子集点对间还可能更小的一定是在 x 在[mid-min, mid+min]
 * 且将这一区间的点按照 y 排序，每个点只至多存在 8 个点距离可能小于 min
 * ---------
 * |   |   |
 * |   |   |
 * ---------
 * 就有如上 2min * min 的矩形区域，所以只需要比较这 8 个点与原点距离即可。
 * 否则的话两两比对最坏情况是左右各为 n/2，那么时间复杂度还是 O(n^2)。
 * 以上的 8 个点有说 6 个的感觉差异不大，这 8 个的原因是鸽巢原理在两个子集使用一下
 */
public class ClosePair {
    /**
     * 点集
     */
    private ArrayList<Point> arrayList;

    /**
     * 输入点集
     */
    public void inputPoint() {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        while (n-- != 0) {
            Point p = new Point();
            p.x = scan.nextDouble();
            p.y = scan.nextDouble();
            arrayList.add(p);
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
            System.out.println("x : " + p.x + " y : " + p.y);
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
            dp.a = arrayList.get(left);
            dp.b = arrayList.get(right);
            dp.length = distance(dp.a, dp.b);
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
            for (int j = i + 1; j < i + 8 && j < temp.size() &&
                    arrayList.get(temp.get(j)).y - arrayList.get(temp.get(i)).y < dp.length; ++j) {
                DoublePoint dp3 = new DoublePoint();
                dp3.a = arrayList.get(temp.get(i));
                dp3.b = arrayList.get(temp.get(j));
                dp3.length = distance(dp3.a, dp3.b);
                if (dp3.length < dp.length) {
                    dp = dp3;
                }
            }
        }

        return dp;
    }

    public static void main(String[] args) {
        ClosePair closePair = new ClosePair();
        closePair.arrayList = new ArrayList<Point>();
        closePair.inputPoint();
        closePair.sortList();
        closePair.printPoint();
        DoublePoint close = closePair.closePair(0, closePair.getPointNum() - 1);
        System.out.println("点 1 : (" + close.a.x + ", " + close.a.y + ")");
        System.out.println("点 2 : (" + close.b.x + ", " + close.b.y + ")");
        System.out.println("最近距离 : " + close.length);
    }

    /**
     * 点结构
     */
    private class Point {
        double x;
        double y;

        public Point() {
            x = 0.0;
            y = 0.0;
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
