public class Link_up {
    /*
        设计一款连连看游戏的算法，你会咋做？要求如下：
        1、怎样用计算机模型描述该问题？
        2、怎样判断两个图形能否相消？
        3、怎样求出相同图形间最短路径（转弯数最少，路径经过的格子数目最少）？
        4、怎样确定死锁状态，如何设计算法解除死锁？
        根据函数 run() 的整体框架可以看到，完成该游戏还有如下问题：
        1、生成游戏初始界面。
        2、每次选择两个图形，如果图形满足条件（图形相同，两者间存在少于 3 个弯的路径），
           则两个图形都能消掉。给定具有相同图形的任意两个格子，我们需要寻找两个格子间在
           转弯最少的情况下，经过格子数目最少的路径。如果最优路径的转弯数目小于 3，则
           两个格子可以消除。
        3、判断游戏是否结束。如果所有图形全部消除，游戏结束。
        4、判断死锁，当不能再消去任意两个图形时，游戏进入“死锁”状态，如下图所示：
            a b
            b a
           此时我们也可以暂时不终止游戏，而是随机打乱局面，打破“死锁”状态。
        analysis:
        最短转弯数下的最短路径。可采用广度优先搜索。考虑图形 A(x1, y1) 和图形 B(x2, y2)。
        1）首先把图形 A(x1, y1) 压入队列。
        2）扩展 A(x1, y1) 可以直线到达的格子，即转弯数为 0 可到达的格子。定义这些格子集合
           S0 = Find(x1, y1), 若 B(x2, y2) 在 S0 中，结束搜索。
        3）否则，扩展 S0 里的空格子，找到可直线到达的格子集合 S1 = {Find(x, y) | (x, y) in S0}。
           S1 包含了 S0，更新 S1 -= S0 表示转弯数为 1 可到达的格子集合。若 B 在 S1 中，结束搜索。
        4）否则，扩展 S1 里的空格子，S2 = {Find(x, y) | (x, y) in S1}。更新 S2 -= S0 - S1 表示
           转弯数为 2 可到达的格子集合，若 B 在其中，结束搜索，否则 A, B 间不能通过转弯小于 3 的
           路径连接。
        其中更新 S1 和 S2 方法：从 A 点出发，所有点 MinCrossing(X) 置为无穷大，MinCrossing(A)=0。
        扩展到 S1 时，MinCrossing(X) = min(MinCrossing(X), 1) 即完成更新，同样类比 S2。
        下面在考虑相同转弯数，采取较小的路径格子数，加入 MinDistance(X)。从格子 X 扩展到 Y 伪代码。
            if((MinCrossing(X)+1 < MinCrossing(Y))
                || (MinCrossing(X)+1 == MinCrossing(Y))
                && (MinDistance(X)+Dist(X, Y) < MinDistance(Y))){
                MinCrossing(Y) = MinCrossing(X) + 1;
                MinDistance(Y) = MinDistance(X) + Dist(X, Y);
            }
        “死锁”问题最直接的方法就是对于尚未消除的格子，相同的图形两两计算一下是否可以消除。
        extension:
        1）是否可以通过维护任意两个格子间的最短路径实现快速搜索？每一次消除后需要维护的数据？
           优缺点？如何实现？
            可以实现快速搜索，每一次消除后需要维护两格子对应的四条直线上的所有图形的最短路径。
            优点是每次找寻两格子是否可消除时间复杂度变为O(1)，“死锁”状态检查也减少至O(N)；
            缺点是预处理的时间复杂度高，需要求O(N^2)对格子的最短路径，且每次消除后还需要维护
            若干点的数据，存储的数据量大。实现方法是找寻相同图形依次求解。
        2）围棋或者象棋中，若干操作后可能出现一个曾经出现过的状态（围棋中的打劫），如何检测？
            保存走过的状态，将状态编码为二进制数或者字符串形式。
     */
    public void run(){
        // 生成游戏初始界面
        Grid preClick = null, curClick = null;
        while(checkDone()){
            /* 监听用户动作
            if(用户点击格子(x, y)，且格子(x, y)为为空格子){
                preClick = curClick;
                curClick.setPos((x, y));
            } */
            if(preClick != null && curClick != null
                && preClick.pic == curClick.pic
                && findPath(preClick, curClick) != null){
                // 显示两个格子间的消去路径
                // 消去格子 preClick, curClick
                preClick = curClick = null;
            }

        }
    }

    // 游戏是否结束
    public static boolean checkDone(){
        // 所有的格子都为 null, 返回false
        return false;
    }

    // 找寻两格子间最短路径，满足条件返回路径，否则返回null
    public static String findPath(Grid preClick, Grid curClick){
        return new String("path");
    }

    public class Grid {
        int []pos;
        String pic;

        Grid(int []pos, String pic){
            this.pos = pos;
            this.pic = pic;
        }

        public void setPos(int[] pos) {
            this.pos = pos;
        }
    }
}
