#include <iostream>
#include <cstddef>
#include <cassert>

using namespace std;
//***************************************************************/
//
// �ӱ�����ʵ��
//
//***************************************************************/
class prefixSorting{
public:
    prefixSorting(){
        m_nCakeCnt = 0;
        m_nMaxSwap = 0;
    }

    ~prefixSorting(){
        if(m_CakeArray != NULL){
            delete m_CakeArray;
        }
        if(m_SwapArray != NULL){
            delete m_SwapArray;
        }
        if(m_ReverseCakeArray != NULL){
            delete m_ReverseCakeArray;
        }
        if(m_ReverseCakeArraySwap != NULL){
            delete m_ReverseCakeArraySwap;
        }
    }

    //
    // �����ӱ���ת��Ϣ
    // @param
    // pCakeArray   �洢�ӱ���������
    // n_CakeCnt    �ӱ�����
    //
    void Run(int* pCakeArray, int nCakeCnt){
        Init(pCakeArray, nCakeCnt);

        m_nSearch = 0;

        Search(0);
    }

    //
    // ����ӱ���������
    //
    void CakeOutput(){
        int i, j, k, t;
        for(i = 0; i < m_nMaxSwap; i++){
            for(j = 0, k = m_SwapArray[i]; j < k; j++, k--){
                t = m_CakeArray[j];
                m_CakeArray[j] = m_CakeArray[k];
                m_CakeArray[k] = t;
            }
            for(j = 0; j < m_nCakeCnt; j++){
                cout << m_CakeArray[j] << " ";
            }
            cout << endl;
        }
    }

    //
    // ����ӱ����巭ת�Ĵ���
    //
    void Output(){
        for(int i = 0; i < m_nMaxSwap; i++){
            cout << m_SwapArray[i];
        }
        cout << "\n |Search Times| : " << m_nSearch << "\n";
        cout << "Total Swap Times = " << m_nMaxSwap << "\n";

        CakeOutput();
    }

private:

    //
    // ��ʼ��������Ϣ
    // @param
    // pCakeArray   �洢�ӱ���������
    // nCakeCnt     �ӱ�����
    //
    void Init(int* pCakeArray, int nCakeCnt){
        assert(pCakeArray != NULL);
        assert(nCakeCnt > 0);

        m_nCakeCnt = nCakeCnt;

        // ��ʼ���ӱ�����
        m_CakeArray = new int[m_nCakeCnt];
        assert(m_CakeArray != NULL);
        for(int i = 0; i < m_nCakeCnt; i++){
            m_CakeArray[i] = pCakeArray[i];
        }

        // ������ཻ��������Ϣ
        m_nMaxSwap = UpperBound(m_nCakeCnt);

        // ��ʼ�������������
        m_SwapArray = new int[m_nMaxSwap + 1];
        assert(m_SwapArray != NULL);

        // ��ʼ���м佻�������Ϣ
        m_ReverseCakeArray = new int[m_nCakeCnt];
        for(int i = 0; i < m_nCakeCnt; i++){
            m_ReverseCakeArray[i] = m_CakeArray[i];
        }
        m_ReverseCakeArraySwap = new int[m_nMaxSwap];
    }

    //
    // Ѱ�ҵ�ǰ��ת���Ͻ�
    //
    int UpperBound(int nCakeCnt){
        // �� n*2 ��� n*2-3 ������ǰ n-2 ����֮���ڷ�תһ�μ��ɣ��Ͻ��С
        return nCakeCnt * 2 - 3;
    }

    //
    // Ѱ�ҵ�ǰ��ת���½�
    //
    int LowerBound(int* pCakeArray, int nCakeCnt){
        int t, ret = 0;

        // ���ݵ�ǰ�����������Ϣ������ж�������Ҫ�������ٴ�
        for(int i = 1; i < nCakeCnt; i++){
            // �ж�λ�����ڵ������ӱ����Ƿ�Ϊ�ߴ����������ڵ�
            t = pCakeArray[i] - pCakeArray[i - 1];
            if((t == 1) || (t == -1)){

            }else{
                ret++;
            }
        }
        if(pCakeArray[nCakeCnt - 1] != nCakeCnt - 1){
            // �������һλ������Ϊ���ģ�����һ�η�ת���½�����
            ret++;
        }
        return ret;
    }

    //
    // �����������
    //
    void Search(int step){
        int i, nEstimate;

        m_nSearch++;

        // ���������������Ҫ����С��������
        nEstimate = LowerBound(m_ReverseCakeArray, m_nCakeCnt);
        if(step + nEstimate >= m_nMaxSwap){
            // ȡ��ʱҲ�ɼ�֦
            return;
        }

        // ����Ѿ��ź��򣬼���ת��ɣ�������
        if(IsSorted(m_ReverseCakeArray, m_nCakeCnt)){
            if(step < m_nMaxSwap){
                m_nMaxSwap = step;
                for(i = 0; i < m_nMaxSwap; i++){
                    m_SwapArray[i] = m_ReverseCakeArraySwap[i];
                }
            }
            return;
        }

        // �ݹ���з�ת
        for(i = 1; i < m_nCakeCnt; i++){
            Reverse(0, i);
            m_ReverseCakeArraySwap[step] = i;
            Search(step + 1);
            Reverse(0, i);
        }
    }

    //
    // true : �Ѿ��ź���
    // false : δ����
    //
    bool IsSorted(int* pCakeArray, int nCakeCnt){
        for(int i = 1; i < nCakeCnt; i++){
            if(pCakeArray[i - 1] > pCakeArray[i]){
                return false;
            }
        }
        return true;
    }

    //
    // ��ת�ӱ���Ϣ
    //
    void Reverse(int nBegin, int nEnd){
        assert(nEnd > nBegin);
        int i, j, t;

        // ��ת�ӱ���Ϣ
        for(i = nBegin, j = nEnd; i < j; i++, j--){
            t = m_ReverseCakeArray[i];
            m_ReverseCakeArray[i] = m_ReverseCakeArray[j];
            m_ReverseCakeArray[j] = t;
        }
    }


private:
    int* m_CakeArray;   // �ӱ���Ϣ����
    int m_nCakeCnt;     // �ӱ�����
    int m_nMaxSwap;     // ��ཻ�����������Ϊm_nCakeCnt*2
    int* m_SwapArray;   // �����������

    int* m_ReverseCakeArray;    // ��ǰ��ת�ӱ���Ϣ����
    int* m_ReverseCakeArraySwap;// ��ǰ��ת�ӱ������������
    int m_nSearch;              // ��ǰ����������Ϣ

};

int main(){
    prefixSorting *pS = new prefixSorting();
    int nCakeCnt = 10;
    int* pCakeArray = new int[nCakeCnt]{3, 2, 1, 6, 5, 4, 9, 8, 7, 0};
    pS->Run(pCakeArray, nCakeCnt);
    pS->Output();
    return 0;
}


