import java.util.Scanner;

public class Kuaishou_3 {
    /*
        手机号目前是11位，一般前三位是运营商固定号码，如158，176，151，130，
        后8位为随机数字，现在需要实现一个程序对输入的一批手机号进行靓号筛选
        和价值排序以确定手机号的售卖价格。
        靓号定义：
          -后8位连续3个或以上为顺子，如				xx0123xx, xx543xxx
	      -后8位连续3个或以上重复为豹子，如 		    xx6666xx, xx888xxx
        价值规则：
	      -连续出现顺子或豹子位数越多价值越大，如 	1234xxxx > 888xxxxx
	      -相同位数豹子价值大于顺子，如 				6666xxxx > 5678xxxx
	      -一个号码中同时有顺子和豹子的，取价值大的
	      -相同价值的手机号，输出顺序与输入顺序一致
        INPUT :
		    一行字符串，包含若干合法的手机号，逗号分隔
        OUTPUT :
		    价值降序排列的靓号，逗号分隔，无则输出null
        INPUT :
		    15112347234,15176313245,15176313346,15176313325,15166667234,
            15188847234,15844454321
        OUTPUT :
		    15844454321,15166667234,15112347234,15188847234
		暴力之。
     */

    static int getScore(String str){
        int score = 0, i = 0, temp = 1;             // 同长度豹子 > 顺子，豹子 2*len+1 顺子 2*len
        str = str.substring(3, 11);                 // 取后八位
        while(i < 7){                               // 找最长的上升顺子
            if(str.charAt(i+1)-str.charAt(i) == 1)
                temp++;
            else
                temp = 1;
            if(temp >= 3)
                score = Math.max(score, 2*temp);
            i++;
        }
        i = 0; temp = 1;
        while(i < 7){                               // 找最长的下降顺子
            if(str.charAt(i+1)-str.charAt(i) == -1)
                temp++;
            else
                temp = 1;
            if(temp >= 3)
                score = Math.max(score, 2*temp);
            i++;
        }
        i = 0; temp = 1;
        while(i < 7){                               // 找最长的豹子
            if(str.charAt(i+1)-str.charAt(i) == 0)
                temp++;
            else
                temp = 1;
            if(temp >= 3)
                score = Math.max(score, 2*temp+1);
            i++;
        }
        return score;
    }

    static void swap(int[] arr, int i, int j){
        int t = arr[i]; arr[i] = arr[j]; arr[j] = t;
    }

    static void swap(String[] arr, int i, int j){
        String t = arr[i]; arr[i] = arr[j]; arr[j] = t;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String str = scan.next();
        scan.close();
        String[] arr_str = str.trim().split(",");
        int[] arr_score = new int[arr_str.length];
        int num = 0;
        for(int i = 0; i < arr_str.length; ++i) {
            arr_score[i] = getScore(arr_str[i]);
            if(arr_score[i] == 0)
                num++;
        }
        String[] pn = new String[arr_str.length-num];   // 缩减数组规模
        int[] ps = new int[arr_str.length-num];
        num = 0;
        for(int i = 0; i < arr_str.length; ++i){
            if (arr_score[i] > 0) {
                pn[num] = arr_str[i];
                ps[num++] = arr_score[i];
            }
        }
        if(pn.length == 0)
            System.out.println("null");
        else {
            for (int i = 0; i < pn.length - 1; ++i) {   // 排序输出
                for (int j = i + 1; j < pn.length; ++j) {
                    if (ps[j] > ps[i]) {
                        swap(ps, i, j);
                        swap(pn, i, j);
                    }
                }
            }
            for (int i = 0; i < pn.length - 1; ++i)
                System.out.print(pn[i] + ",");
            System.out.println(pn[pn.length - 1]);
        }
    }
}
