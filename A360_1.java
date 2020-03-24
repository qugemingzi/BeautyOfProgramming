import java.util.Scanner;

public class A360_1 {
    /*
        DNA串只有AT组成，长度为 n，每次操作为交换两个位置，或者将某个特定位置改为另一种，由一个DNA
        转为另一种DNA，最少操作次数
        INPUT :
            两行，分别为初始DNA，目标DNA，长度相同
        OUTPUT :
            最少的操作次数
        IN : ATTTAA
             TTAATT     n in [1, 1e5]
        OUT: 3          pos[0] A->T, pos[2]<->pos[5], pos[3]<->pos[4]
        idea :
            找出不同的 A 和 T 的数量，交换 min，更改 max-min，总操作数为 max。
     */

    public static void main(String []args) {
        Scanner scan = new Scanner(System.in);
        String begin = scan.next(), target = scan.next();
        int diff_A = 0, diff_T = 0;
        for(int i = 0; i < begin.length(); ++i){
            if(begin.charAt(i) != target.charAt(i)){
                if(begin.charAt(i) == 'A')
                    diff_A++;
                else
                    diff_T++;
            }
        }
        System.out.println(Math.max(diff_A, diff_T));
        scan.close();
    }
}
