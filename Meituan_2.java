import java.util.Scanner;

public class Meituan_2 {
    public static void main(String args[]){
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int[] arr = new int[n];
        for(int i = 0; i < n; ++i){
            arr[i] = scan.nextInt();
        }
        int res = 0;
        for(int i = 0; i < n; ++i){
            res = Math.max(res, longest(arr, i));
        }
        System.out.println(res);
    }

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
