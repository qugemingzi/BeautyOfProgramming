import java.util.ArrayList;
import java.util.Scanner;

public class Kuaishou_2 {
    /*
        输入一个大小为N的数组A，其中元素为可重复整数，数组里面可能存在这样一些元素A[i]，
        对于A[i]，在A[0]~A[i-1]中有且只有一个元素的值大于A[i](重复元素算多个)，要求写
        代码找出这些元素并输出，如果不存在输出-1。(时间复杂度O(N))
        INPUT :
		    一行字符串，表示数组A，数字间用空格分隔
        OUTPUT :
		    满足条件的元素下标，空格分隔
        INPUT :
		    1 22 22 33 22 12 45 44 5
        OUTPUT :
		    4 7
        INPUT :
		    100 1 2 3 4 5
        OUTPUT :
		    1 2 3 4 5
        遍历数组使位置 i 维护三个数组，为 max[i], submax[i], flag[i], 分别表示从 0 位置
        到 i 位置最大的数，次大的数，最大的数是否唯一(true不唯一)，再次遍历数组比较该数值
        与最大数大小比较，与次大数大小比较及最大数是否唯一得知该位置是否满足条件。因为都是
        遍历操作，时间复杂度为O(N)。
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String str = scan.nextLine();
        scan.close();
        String[] arr_str = str.split(" ");
        int[] arr = new int[arr_str.length];
        for(int i = 0; i < arr.length; ++i)
            arr[i] = Integer.parseInt(arr_str[i]);
        int[] max = new int[arr.length], submax = new int[arr.length];
        boolean[] flag = new boolean[arr.length];
        max[0] = arr[0]; submax[0] = arr[0]; flag[0] = false;
        for(int i = 1; i < arr.length; ++i){
            if(arr[i] > max[i-1]){              // 当前数大于之前的最大数
                max[i] = arr[i];
                submax[i] = max[i-1];
                flag[i] = false;
            } else if(arr[i] == max[i-1]){      // 当前数等于之前的最大数
                max[i] = arr[i];
                submax[i] = submax[i-1];
                flag[i] = true;
            } else if(arr[i] >= submax[i-1]){   // 当前数小于之前最大数且大于等于次大数
                max[i] = max[i-1];
                submax[i] = arr[i];
                flag[i] = flag[i-1];
            } else {                            // 当前数小于次大数
                max[i] = max[i-1];
                if(i == 1)                      // 第二位特殊处理一下次大数
                    submax[i] = arr[i];
                else
                    submax[i] = submax[i-1];
                flag[i] = flag[i-1];
            }
        }
        ArrayList<Integer> arrayList = new ArrayList<>();
        for(int i = 1; i < arr.length; ++i){
            if(arr[i] < max[i] && arr[i] >= submax[i] && flag[i] == false)
                arrayList.add(i);
        }
        if(arrayList.isEmpty()){
            System.out.println(-1);
        } else {
            for(int i = 0; i < arrayList.size()-1; ++i)
                System.out.print(arrayList.get(i) + " ");
            System.out.println(arrayList.get(arrayList.size()-1));
        }
    }
}
