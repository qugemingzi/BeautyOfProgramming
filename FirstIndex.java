package algorithm;

/**
 * @author pray chow
 * 有序数组，实现一个查找函数，找出指定元素在数组中第一次出现的位置
 * 要求：时间复杂度最优
 * idea : 二分，平均 O(logn) 最差 O(n)，在 arr[mid]==search 时继续二分
 */
public class FirstIndex {

    /**
     * 二分查找
     * @param arr 有序数组
     * @param search 找寻对象
     * @return 找到第一次出现对象的位置，没找到返回 -1
     */
    public int firstIndex(int[] arr, int search) {
        int start = 0, end = arr.length - 1;
        while(end >= start) {
            int mid = (start + end) >> 1;
            if(arr[mid] == search) {
                if(mid == start || arr[mid - 1] != search) {
                    return mid;
                } else {
                    end = mid - 1;
                }
            } else if (search > arr[mid]) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        FirstIndex fi = new FirstIndex();
        int[] arr = {0, 1, 1, 1, 3, 4};
        System.out.println(fi.firstIndex(arr, 1));
    }
}
