public class Kuaishou_1 {
    /*
        N位同学(0, 1e4)，面朝国旗间隔 1 米排成一列，数组height[i]表示这队人从前到后的身高，
        返回队中每个人与前方身高高于自己的人的最短距离，如果前方没有比他高的人，则距离为0。
        INPUT :
		    [175, 173, 174, 163, 182, 177]
        OUTPUT :
		    [0, 1, 2, 1, 0, 1]
        INPUT :
		    [175, 179, 174, 163, 176, 177]
        OUTPUT :
		    [0, 0, 1, 1, 3, 4]
		暴力之
     */

    static public int[] DistanceToHigher (int[] height) {
        if(height.length == 1)
            return new int[1];
        int[] res = new int[height.length];
        for(int i = 1; i < height.length; ++i){
            for(int j = i-1; j >= 0; --j){
                if(height[j] > height[i])
                    res[i] = i - j; break;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] height = {175, 179, 174, 163, 176, 177};
        int[] dth = DistanceToHigher(height);
        for(int distance : dth)
            System.out.print(distance +" ");
        System.out.println();
    }
}
