package algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author pray chow
 * 字符串匹配，给定一关键字和一字符串，提取出符合关键字要求的寄存器地址，掩码和值
 * 字符串由 {XXX[addr=0xYYY,mask=0xZZZ,val=0xWWW]} 组成，中间由逗号隔开
 * 匹配的意思为关键字完全相同，输出满足的寄存器地址，掩码和值，否则输出 FAIL
 * INPUT :
 *      匹配字符串 待匹配字符串    length < 1024
 * OUTPUT :
 *      addr mask val 通过空格隔开，一个匹配寄存器一行，换行为 \r\n，最后一项也换行
 *      否则输出 FAIL
 * IN :
 *      read read[addr=0x17,mask=0xff,val=0x7],read_his[addr=0xff,mask=0xff,val=0x1]
 *      ,read[addr=0xf0,mask=0xff,val=0x80]
 * OUT :
 *      0x17 0xff 0x7
 *      0xf0 0xff 0x80
 */
public class Huawei_2 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String str = scan.next();
        String strs = scan.next();
        scan.close();
        List<String> res = new ArrayList<>();
        String[] cur = strs.split("]");
        for (int i = 0; i < cur.length; ++i) {
            String temp = cur[i];
            if (temp.charAt(0) == ',') {
                temp = temp.substring(1, temp.length());
            }
            String[] detail = temp.split("\\[");
            if (detail[0].equals(str)) {
                String[] more = detail[1].split(",");
                String[] addr = more[0].split("=");
                String[] mask = more[1].split("=");
                String[] val = more[2].split("=");
                res.add(addr[1] + " " + mask[1] + " " + val[1]);
            }
        }
        if (res.size() == 0) {
            System.out.println("FAIL");
        } else {
            for (String temp : res) {
                System.out.print(temp + "\r\n");
            }
        }
    }
}
