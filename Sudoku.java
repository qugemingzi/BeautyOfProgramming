public class Sudoku {
    /*
        数独游戏，程序大致框架？什么样的数据结构存储各种元素？如何生成初始局面？
        analysis:
        idea 1:
            存储数据结构采取二维数组，随机生成一个可行解概率很低，不如从 (0, 0) 开始
            每个位置调用 GetValidValueList() 获取能放在该位置的数字列表，再从中随机
            选择，若某一位置无可选择数字，则深度优先搜索回溯，修改前一个格子的值。
            int m_size = 9;
            Cell[, ] m_cells; // 每一个格子

            boolean GenerateValidMatrix(){
                // 准备搜索
                Coord coCurrent;
                coCurrent.x = 0;
                coCurrent.y = 0;
                while(true){
                    Cell c = m_cells[coCurrent.x, coCurrent.y];
                    ArrayList al;
                    if(c.IsProcessed)
                        al = GetValidValueList(coCurrent);
                        c.ValidList = al;
                    if(c.ValidList.Count > 0)
                        c.PickNextValidValue();
                        if(coCurrent.x == this.Size-1 && coCurrent.y == this.Size-1)
                            break; // 到达矩阵最后一个位置了
                        else
                            coCurrent = NextCoord(coCurrent);
                    else
                        // 若到达起始位置，跳出
                        if(coCurrent.x == 0 && coCurrent.y == 0)
                            break;
                        else
                            c.Clear();
                            coCurrent = PrevCoord(coCurrent);
                }
                return true;
            }
            判断游戏是否结束：
            1、所有格子填完
            2、所有的行、列、小矩阵都符合条件
        idea 2:
            简单构造法，通过一个 3x3 的矩阵，构造其余 8 个矩阵。具体方法如下所示：
                a b c           d e f a b c g h i
                d e f   ===>    g h i d e f a b c   ===>
                g h i           a b c g h i d e f
                      b c a                 e f d b c a h i g
                      e f d                 h i g e f d b c a
                      h i g                 b c a h i g e f d
                d e f a b c g h i           d e f a b c g h i
                g h i d e f a b c   ===>    g h i d e f a b c
                a b c g h i d e f           a b c g h i d e f
                      c a b                 f d e c a b i g h
                      f d e                 i g h f d e c a b
                      i g h                 c a b i g h f d e
            该方法可以生成 9! 个不同的数独。当然之不包括所有的合法数独，还可以通过
            局部的行列变换增加变化的数目。优点是程序简单。
        extension:
            1）如何表示窗口，方便确定鼠标点击的位置，窗口大小为矩形。
            使用左上坐标和右下坐标两者确定，若鼠标 x 和 y 值都在两坐标间，则位于该窗口。
     */
}
