import java.util.*;


public class BiggestK {
    /*
        寻找无序数组最大的 K 个数？数组特大？
        idea 1: 先排序O(nlogn)，在找 K 个数，总为O(nlogn+K)。我们发现 K = 1 时不需要所有的都排序，
            想到选择排序/冒泡排序可以依次把 K 个最大的找出来，为O(n+K)，K<logn 时，选择部分排序。
        idea 2: 快速排序，每次以 X 为界，分为两部分 Sa, Sb，其中 Sa > X, Sb < X。那么每次分支都可以
            剪枝，若 |Sa| < K, 则再从 Sb 中找 K-|Sa| 个最大的；否则从 Sa 中找最大的 K 个。O(nlogK)。
        idea 3: 寻找第 K 大的数，二分搜索，给定一个 p，可以在O(n)时间找出所有不小于 p 的数，假如
            n 个数中最大的 Vmax，最小的 Vmin，即二分查找。
            while(Vmax - Vmin > delta){
                Vmid = Vmin + (Vmax - Vmin) * 0.5;
                if(f(arr, n, Vmid) >= K) // f(arr, n, K)返回数组 arr[0,...,n-1]中大于等于Vmid的数个数
                    Vmin = Vmid;
                else
                    Vmax = Vmid;
            }
            上述代码中 delta 取值为比任意两个不等数差值最小值还要小，若都为整数，可取 0.5，循环后得到一
            区间 [Vmin, Vmax]，该区间中包含的一个整数或者多个相等整数即为第 K 大的数，时间复杂度为
            O(nlog(Vmax-Vmin)/delta)，数据分布均匀的话，时间复杂度为O(nlogn)。
            换个角度，若考虑为二进制，或者最大的数就在[0, 2^(m-1)]区间中，可转化为二进制表示，依次从
            最高位 m-1 考虑，为 1 的和为 0 的，为 1 的数 > K，则从为 1 的那些数中继续寻找第 K 大的数。
        idea 4: 若 n 很大呢？
            先将前 K 个数用数组存取，每来一个数 Y 就与当前大小为 k 的数组的最小值比较，若 Y 大，则将原
            最小值拿出来，将 Y 放入数组中，时间复杂度为O(nK)。
            进一步，用容量为 K 的最小堆来替代数组，时间复杂度为O(nlogK)。见LeetCode 1537(找最小的 K 个数)
            Java 中使用 PriorityQueue 作为最小(大)堆。
        idea 5: 上述都是类线性时间，有无线性时间复杂度的算法呢？
            若都为正整数且取值范围不大的话，可申请空间，记录每个整数出现的次数，再从大到小取最大的 K 个。
            比如所有整数都在 (0, MAX]，数组 count[MAX} 记录每个整数出现的次数，我们只需要扫描一遍就得到
            count 数组，然后寻找第 K 大的数。
            for(sumCount = 0, v = MAX-1; v >= 0; v--){
                sumCount += count[v];
                if(sumCount >= K)
                    break;
            }
            return v;
            极端情况下，若 n 各个数都不同，只需要用一个bit存储整数是否存在。
            实际情况下，不能保证所有的整数都是正整数且取值范围不大。上面的方法仍然可以推广适用。
            最大数为 Vmax, 最小数为 Vmin，区间 [Vmin, Vmax] 分为 M 块，每个区间 d=(Vmax-Vmin)/M，
            扫描一遍所有元素，统计每个小区间的元素个数，然后知道第 K 大的数在哪一个区间，继续分块处理，
            时间复杂度为O((N+M)logM(Vmax-Vmin)/delta)。
        多种方法分情况描述会给面试官深刻的印象了。
        extension:
        1）找最大的 K 个不同的浮点数？
            最小堆插入时搜索一下是否存在于堆中。
        2）如果找第 k ~ m(0<k<=m<=n)大的数？
            分两次，第一次找 k 大的，第二次删去 m+1 大的。或者用两次 idea 3，可找到第 k, m 大的。
        3）每个网页都有 page rank，寻找权重最大的 K 个网页？网页权重会不断更新，如何快速更新并及时返回
        权重最大的 K 个网页？
            堆排序，当每个网页更新的时候，更新堆。（映射二分堆？
        4）实际应用中，有“精确度”的问题，并不需要严格返回最大的 K 个元素，边界位置允许误差，当用户输入
        一个 query 时，对于文档 d 有一个相关性衡量权重 f(query, d)，搜索引擎需要返回权重最大的 K 个网页。
        若每个网页 10 个网页，那第 1000 页后的“精确度”无伤大雅，如何改进？网页数目大到一台机器容不下咋办？
            归并排序，分为几个机器，分别返回 K 个最大网页，并集一定有最大的 K 个网页，若每个机器返回 K' 个，
            则保证准确率为 90% ？或者最差的不会超过 110% ？最好情况为 K'=K/M，最坏情况为 K'=K。
            -----------
            ----------- K'个
            +++++++++++
            +++++++++++ K-K' 个
            共 M 个机器，(1 - (K-K'+1)/(K'M-K'+1))^M 为准确率，每个机器的交出的 K' 的最后一位若没有抽中
            为最大的 K 个数，则剩余的也不会被抽中，该位被抽中的概率为上面都被选中后概率 (K-K'+1)/(K'M-K'+1)
            M 位最后一位都没有被选中的概率即为准确的概率。
        5）相似关键字对于 K 个最大的文档有什么帮助呢？
            有帮助，可以在在前的 K'M 个中选择，或者在“近义词”相关文档中搜索部分，再在全局文档中搜索部分。
     */

    // idea 0 先排序
    Integer[] KBig_0(Integer[] arr, int k){
        if(k <= 0){ return new Integer[0]; }
        if(arr.length <= k){ return arr; }
        Arrays.sort(arr, (Integer o1, Integer o2) -> {return o2-o1;});
//        Arrays.sort(arr, new Comparator<Integer>(){
//            public int compare(Integer o1, Integer o2){
//                return o2-o1;
//            }
//        });
        return Arrays.copyOfRange(arr, 0, k);
    }

    // idea 1 选择排序
    int[] KBig_1(int[] arr, int k){
        if(k <= 0){ return new int[0]; }
        if(arr.length <= k){ return arr; }
        for(int i = 0; i < k; ++i){
            for(int j = arr.length-1; j >= i+1; --j){
                if(arr[j] > arr[j-1]){
                    swap(arr, j-1, j);
                }
            }
        }
        return Arrays.copyOfRange(arr, 0, k);
    }

    // idea 2 快速排序
    int[] KBig_2(int[] arr, int k){
        if(k <= 0){ return new int[0]; }
        if(arr.length <= k){ return arr; }
        BigK_2(arr, 0, arr.length-1, k);
        return Arrays.copyOfRange(arr, 0, k);
    }

    void BigK_2(int[] arr, int start, int end, int k){
        if(start >= end){ return; }
        int i = start, j = end, temp = arr[start];
        while(i != j){
            while(i!=j && arr[j]<=temp)
                j--;
            swap(arr, i, j);
            while(i!=j && arr[i]>=temp)
                i++;
            swap(arr, i, j);
        }
        if(i==k-1){ return; }
        else if(i >= k)
            BigK_2(arr, start, i-1, k);
        else
            BigK_2(arr, i+1, end, k);
    }

    void swap(int[] arr, int i, int j){
        int t = arr[i]; arr[i] = arr[j]; arr[j] = t;
    }

    // idea 3: 二分搜索第 K 大的数
    int[] KBig_3(int[] arr, int k){
        if(k <= 0){ return new int[0]; }
        if(arr.length <= k){ return arr; }
        float max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        float delta = 0.5f;
        for(int temp : arr){
            max = temp > max ? temp : max;
            min = temp < min ? temp : min;
        }
        while(max - min > delta){
            float mid = min + (max - min) * delta;
            if(find(arr, mid) >= k)
                min = mid;
            else
                max = mid;
        }
        int[] res = new int[k];
        int i = 0;
        int knum = Math.round((max+min)/2);
//        System.out.println(max + " " + min + " " + knum);
        for(int temp : arr){
            if(temp >= knum && i < k)
                res[i++] = temp;
        }
        return res;
    }

    int find(int[] arr, float mid){
        int res = 0;
        for(int temp : arr){
            if(temp >= mid)
                res++;
        }
        return res;
    }

    // idea 4: 最小堆
    int[] KBig_4(int[] arr, int k){
        if(k <= 0){ return new int[0]; }
        if(arr.length <= k){ return arr; }
        Queue<Integer> queue = new PriorityQueue(k);
        for(int i = 0; i < k; ++i){
            queue.offer(arr[i]);
        }
        for(int i = k; i < arr.length; ++i){
            if(arr[i] > queue.peek()){
                queue.poll();
                queue.offer(arr[i]);
            }
        }
        int[] res = new int[k];
        for(int i = k-1; i >= 0; --i){
            res[i] = queue.poll();
        }
        return res;
    }


    public static void main(String[] args){
        BiggestK biggestK = new BiggestK();
        Queue<Integer> q = new PriorityQueue<>();
        int[] arr1 = {1, 3, 4, 7, 8, 6, 4, 2};
        int[] arr2 = {1, 3, 4, 7, 8, 6, 4, 2};
        int[] arr3 = {1, 3, 4, 7, 8, 6, 4, 2};
        int[] arr4 = {1, 3, 4, 7, 8, 6, 4, 2};
        Integer[] iarr = {1, 3, 4, 7, 8, 6, 4, 2};
        System.out.println("KBig_0 sort first");
        for(int temp : biggestK.KBig_0(iarr, 4))
            System.out.print(temp + " ");
        System.out.println("\nKBig_1 selection sort");
        for(int temp : biggestK.KBig_1(arr1, 4))
            System.out.print(temp + " ");
        System.out.println("\nKBig_2 quick sort");
        for(int temp : biggestK.KBig_2(arr2, 4))
            System.out.print(temp + " ");
        System.out.println("\nKBig_3 binary search");
        for(int temp : biggestK.KBig_3(arr3, 4))
            System.out.print(temp + " ");
        System.out.println("\nKBig_4 maximum heap(PriorityQueue)");
        for(int temp : biggestK.KBig_4(arr4, 4))
            System.out.print(temp + " ");
    }
}
