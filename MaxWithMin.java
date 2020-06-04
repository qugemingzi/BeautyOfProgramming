package algorithm;

import javafx.util.Pair;


/**
 * @author pray chow
 * 寻找数组中的最大值和最小值，求最少比较次数？
 * idea 1: 遍历两次分别寻找，2*N 比较次数
 * idea 2: 分组比较，将数组分成两部分，分别找到最大的值和最小的值
 * 可以通过奇偶划分，两两比较较大者放到偶数位，较小的在奇数位
 * e.g.
 * 5 6 8 3 7 9
 * 6 5 8 3 9 7
 * max = 9 min = 3
 * 分别从偶数位找到最大值，奇数位找到最小值即可，比较次数 N/2*3 = 1.5*N
 * idea 3: 上述想法需要破坏原数组，可以边遍历边比较就不会破坏原数组了
 * 先通过数组前两位置最大/小值初始值，再两两比较
 * e.g.             max min
 * 5 6| 8 3 7 9     6   5
 * 5 6 8 3| 7 9     8   3  8>3 8>6 3<5
 * 5 6 8 3 7 9|     9   3  7<9 9>8 7>3
 * max = 9 min = 3
 * 比较次数还是 1.5*N，只是不会破坏原数组
 * idea 4: 分治法。分成两部分，小小取小，大大取大，详见代码，比较次数
 * f(2) = 1
 * f(n) = 2*f(n/2) + 2
 * = 2^(log2(n) - 1) * f(n/2^(log2(n) - 1)) + 2^(log2(n) - 1) + ... + 2^2 + 2
 * = n/2 * f(2) + 2*(1 - 2^(log2(n) - 1)) / (1 - 2)
 * = n/2 + (2 - n) / (1 - 2)
 * = 1.5*n - 2
 * 比较次数仍然没有减少
 * extension :
 * 找出第二大数，比较次数？分治？
 * idea :
 * 可以用分治，Pair(max, submax) 存储最大和次大值，合并时
 * maxL > maxR : return Pair(maxL, Math.max(maxR, submaxL));
 * else        : return Pair(maxR, Math.max(maxL, submaxR));
 * tip :    Kuaishou_2 中有次大值的操作，两个数组，比较分析
 */
public class MaxWithMin {

    public Pair<Integer, Integer> method_1(int[] arr) {
        if (arr.length == 0) {
            return new Pair<>(-1, -1);
        }
        int max = arr[0], min = arr[0];
        for (int tmp : arr) {
            max = tmp > max ? tmp : max;
            min = tmp < min ? tmp : min;
        }
        return new Pair<>(max, min);
    }

    public Pair<Integer, Integer> method_2(int[] arr) {
        if (arr.length == 0) {
            return new Pair<>(-1, -1);
        }
        for (int i = 0; i < arr.length - 1; i += 2) {
            if (arr[i] < arr[i + 1]) {
                int tmp = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = tmp;
            }
        }
        int max = arr[0], min = arr[0];
        for (int i = 0; i < arr.length; i += 2) {
            max = arr[i] > max ? arr[i] : max;
        }
        for (int i = 1; i < arr.length; i += 2) {
            min = arr[i] < min ? arr[i] : min;
        }
        return new Pair<>(max, min);
    }

    public Pair<Integer, Integer> method_3(int[] arr) {
        if (arr.length == 0) {
            return new Pair<>(-1, -1);
        }
        int max = arr[0], min = arr[0];
        for (int i = 0; i < arr.length - 1; i += 2) {
            int tmax = Math.max(arr[i], arr[i + 1]);
            int tmin = Math.min(arr[i], arr[i + 1]);
            max = Math.max(max, tmax);
            min = Math.min(min, tmin);
        }
        if (arr.length % 1 == 1) {
            return new Pair<>(Math.max(max, arr[arr.length - 1]), Math.min(min, arr[arr.length - 1]));
        }
        return new Pair<>(max, min);
    }

    public Pair<Integer, Integer> method_4(int[] arr) {
        if (arr.length == 0) {
            return new Pair<>(-1, -1);
        }
        return search(arr, 0, arr.length - 1);
    }

    public Pair<Integer, Integer> search(int[] arr, int begin, int end) {
        if (end - begin <= 1) {
            return arr[begin] < arr[end] ? new Pair<>(arr[end], arr[begin]) : new Pair<>(arr[begin], arr[end]);
        }
        Pair<Integer, Integer> pair_l = search(arr, begin, begin + (end - begin) / 2);
        Pair<Integer, Integer> pair_r = search(arr, begin + (end - begin) / 2 + 1, end);
        int max = pair_l.getKey() > pair_r.getKey() ? pair_l.getKey() : pair_r.getKey();
        int min = pair_l.getValue() < pair_r.getValue() ? pair_l.getValue() : pair_r.getValue();
        return new Pair<>(max, min);
    }

    public static void main(String[] args) {
        MaxWithMin maxWithMin = new MaxWithMin();
        int[] arr = {5, 6, 8, 3, 7, 9};
        Pair<Integer, Integer> pair1 = maxWithMin.method_1(arr);
        System.out.println(pair1.getKey() + " " + pair1.getValue());
        Pair<Integer, Integer> pair2 = maxWithMin.method_2(arr);
        System.out.println(pair2.getKey() + " " + pair2.getValue());
        int[] arr_copy = {5, 6, 8, 3, 7, 9};
        Pair<Integer, Integer> pair3 = maxWithMin.method_3(arr_copy);
        System.out.println(pair3.getKey() + " " + pair3.getValue());
        Pair<Integer, Integer> pair4 = maxWithMin.method_4(arr_copy);
        System.out.println(pair4.getKey() + " " + pair4.getValue());
    }
}
