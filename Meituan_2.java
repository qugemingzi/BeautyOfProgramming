import java.util.Scanner;

public class Meituan_2 {
    /*
        最长上升字串构造
        给定长度为 n 的正整数构成的序列，从中删除一个正整数，求删除后的最长上升子串，所有方案中最长的子串上升长度？
        最长上升子串: 后面数 > 前面数
        INPUT :
            正整数 n 为序列长度, [1, 1e5]
            n 个正整数，即序列，空格隔开, [1, 1e5]
        OUTPUT :
            输出一个正整数，即删后最长上升子串长度
        example :
            IN : 5
                 2 1 3 2 5
            OUT: 3
        idea : 删除后最长的一定是将 i 删除后造成的更长的上升子串，即只需以 i 为中心找即可，O(n^2)。暴力是O(n^3)。
     */

    public static void main(String args[]){
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt(), res = 0;
        int[] arr = new int[n];
        for(int i = 0; i < n; ++i)
            arr[i] = scan.nextInt();
        for(int i = 0; i < n; ++i)
            res = Math.max(res, longest(arr, i));
        System.out.println(res);
    }

    // 返回数组 arr[] 在位置 i 删除后两侧的最长上升字串长度
    static int longest(int[] arr, int i){
        int left = 1, right = 1;
        if(i == 0 || i == arr.length-1 || arr[i-1] >= arr[i+1])
            return 0;
        for(int j = i-1; j > 0; --j){
            if(arr[j] > arr[j-1])
                left++;
            else
                break;
        }
        for(int j = i+1; j < arr.length-1; ++j){
            if(arr[j+1] > arr[j])
                right++;
            else
                break;
        }
        return left+right;
    }
}
