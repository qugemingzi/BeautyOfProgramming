public class NIM_1 {
    /*
        一排石头的游戏
        N 块石头排成一行，每块石头有各自固定的位置。两个玩家依次取石头，每个玩家每次可以取
        其中任意一块石头，或者相邻的两块石头，石头在游戏过程中不能移位，最后将剩下的石头一
        次取光的玩家获胜。该游戏有必胜的策略么？
        analysis:
        将所有石头编号为 1 ~ N，所以可以同时取 1 和 2 的两块石头，但不可以同时取 1 和 3 的
        两块石头，以此类推。
        1）N = 1 或者 N = 2
            先取者即可一次取完所有的石头而获胜
        2）N = 3
            则石头标号 1、2、3，那么先取者可以取 2 号石头，先取者获胜
        3）N = 4
            取 1 或者 4 的话后取者就可以按照 2）中的方法获胜，取 2、3 也是后取者获胜
            取 12、34 后者胜，取 23 的话先取者胜。
        4）N > 4
            至此可以发现对称的规律，即先取者就取中间的一个或者两个石头，确保左右两边石头
            数目相同。则先取者只要每次以初始中心为对称轴，在与后取者所取石头位置对称的地方
            取得相同数目的石头，就可以确保每次都有石头取，并且必将取得最后的石头，获胜！
        至此先取者都将有必胜的策略！
        extension:
        1）若规定最后取光石头的人输，又该如何应对呢？这个问题才是面试者真正想考察的。woc？
            通过枚举，N = 1、4、9 先取者败；N = 2、3、5、6、7、8 先取者胜。
            具体获胜的策略和先后手获胜与 N 的关系没有搞清楚。
        2）若两个人轮流取一堆石头，每人每次最少取1块石头，最多取 K 块石头，最后取光石头的
           人赢得游戏。
            因为是一堆石头，就不用考虑相连的问题，只要一直和上一位取的石头数目相加和为 K+1
            就有必胜策略。具体如下：
            N % (K+1) == 0 先取者败，无论先取者取多少，后取者保证取的数目和为 K+1即可获胜
            N % (K+1) != 0 先取者胜，先取者取"余数"个，之后先取者保证和后取者数目和为 K+1
     */
}
