//***************************************************************/
//
// 烙饼排序实现
//
//***************************************************************/
public class PrefixSorting {
    private int[] m_CakeArray;  // 烙饼信息数组
    private int m_nCakeCnt;     // 烙饼个数
    private int m_nMaxSwap;     // 最多交换次数，最多为m_nCakeCnt*2
    private int[] m_SwapArray;  // 交换结果数组

    private int[] m_ReverseCakeArray;       // 当前翻转烙饼信息数组
    private int[] m_ReverseCakeArraySwap;   // 当前翻转烙饼交换结果数组
    private int m_nSearch;                  // 当前搜索次数信息


    PrefixSorting(){
        m_nCakeCnt = 0;
        m_nMaxSwap = 0;
    }

    //
    // 计算烙饼翻转信息
    // @param
    // pCakeArray   存储烙饼索引数组
    // n_CakeCnt    烙饼个数
    //
    public void Run(int[] pCakeArray, int nCakeCnt){
        Init(pCakeArray, nCakeCnt);

        m_nSearch = 0;

        Search(0);
    }

    //
    // 输出烙饼交换过程
    //
    public void CakeOutput(){
        int i, j, k, t;
        for(i = 0; i < m_nMaxSwap; i++){
            for(j = 0, k = m_SwapArray[i]; j < k; j++, k--){
                t = m_CakeArray[j];
                m_CakeArray[j] = m_CakeArray[k];
                m_CakeArray[k] = t;
            }
            for(j = 0; j < m_nCakeCnt; j++){
                System.out.print(m_CakeArray[j] + " ");
            }
            System.out.println();
        }
    }

    //
    // 输出烙饼具体翻转的次数
    //
    public void Output(){
        for(int i = 0; i < m_nMaxSwap; i++){
            System.out.print(m_SwapArray[i]);
        }
        System.out.println();
        System.out.println("|Search Times| : " + m_nSearch);
        System.out.println("Total Swap Times = " + m_nMaxSwap);

        CakeOutput();
    }


    //
    // 初始化数组信息
    // @param
    // pCakeArray   存储烙饼索引数组
    // nCakeCnt     烙饼个数
    //
    private void Init(int[] pCakeArray, int nCakeCnt){
        assert pCakeArray != null;
        assert nCakeCnt > 0;

        m_nCakeCnt = nCakeCnt;

        // 初始化烙饼数组
        m_CakeArray = new int[m_nCakeCnt];
        assert m_CakeArray != null;
        for(int i = 0; i < m_nCakeCnt; i++){
            m_CakeArray[i] = pCakeArray[i];
        }

        // 设置最多交换次数信息
        m_nMaxSwap = UpperBound(m_nCakeCnt);

        // 初始化交换结果数组
        m_SwapArray = new int[m_nMaxSwap + 1];
        assert m_SwapArray != null;

        // 初始化中间交换结果信息
        m_ReverseCakeArray = new int[m_nCakeCnt];
        for(int i = 0; i < m_nCakeCnt; i++){
            m_ReverseCakeArray[i] = m_CakeArray[i];
        }
        m_ReverseCakeArraySwap = new int[m_nMaxSwap];
    }

    //
    // 寻找当前翻转的上界
    //
    private int UpperBound(int nCakeCnt){
        // 由 n*2 变成 n*2-3 ，考虑前 n-2 个，之后在翻转一次即可，上界减小
        return nCakeCnt * 2 - 3;
    }

    //
    // 寻找当前翻转的下界
    //
    private int LowerBound(int[] pCakeArray, int nCakeCnt){
        int t, ret = 0;

        // 根据当前数组的排序信息情况来判断最少需要交换多少次
        for(int i = 1; i < nCakeCnt; i++){
            // 判断位置相邻的两个烙饼，是否为尺寸排序上相邻的
            t = pCakeArray[i] - pCakeArray[i - 1];
            if((t == 1) || (t == -1)){

            }else{
                ret++;
            }
        }
        if(pCakeArray[nCakeCnt - 1] != nCakeCnt - 1){
            // 考虑最后一位，若不为最大的，还需一次翻转，下界增加
            ret++;
        }
        return ret;
    }

    //
    // 排序的主函数
    //
    private void Search(int step){
        int i, nEstimate;

        m_nSearch++;

        // 估算这次搜索所需要的最小交换次数
        nEstimate = LowerBound(m_ReverseCakeArray, m_nCakeCnt);
        if(step + nEstimate >= m_nMaxSwap){
            // 取等时也可剪枝
            return;
        }

        // 如果已经排好序，即翻转完成，输出结果
        if(IsSorted(m_ReverseCakeArray, m_nCakeCnt)){
            if(step < m_nMaxSwap){
                m_nMaxSwap = step;
                for(i = 0; i < m_nMaxSwap; i++){
                    m_SwapArray[i] = m_ReverseCakeArraySwap[i];
                }
            }
            return;
        }

        // 递归进行翻转
        for(i = 1; i < m_nCakeCnt; i++){
            Reverse(0, i);
            m_ReverseCakeArraySwap[step] = i;
            Search(step + 1);
            Reverse(0, i);
        }
    }

    //
    // true : 已经排好序
    // false : 未排序
    //
    private boolean IsSorted(int[] pCakeArray, int nCakeCnt){
        for(int i = 1; i < nCakeCnt; i++){
            if(pCakeArray[i - 1] > pCakeArray[i]){
                return false;
            }
        }
        return true;
    }

    //
    // 翻转烙饼信息
    //
    private void Reverse(int nBegin, int nEnd){
        assert nEnd > nBegin;
        int i, j, t;

        // 翻转烙饼信息
        for(i = nBegin, j = nEnd; i < j; i++, j--){
            t = m_ReverseCakeArray[i];
            m_ReverseCakeArray[i] = m_ReverseCakeArray[j];
            m_ReverseCakeArray[j] = t;
        }
    }


    public static void main(String[] args){
        PrefixSorting ps = new PrefixSorting();
        int nCakeCnt = 10;
        int[] pCakeArray = {3, 2, 1, 6, 5, 4, 9, 8, 7, 0};
        ps.Run(pCakeArray, nCakeCnt);
        ps.Output();
    }
}
