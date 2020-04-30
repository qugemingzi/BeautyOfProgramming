package algorithm;

/**
 * @author pray chow
 * LeetCode 12 int -> Roman
 */
public class LeetCode12 {

    /**
     * 无脑分类
     * @param num int
     * @return roman
     */
    public String intToRoman(int num) {
        StringBuilder res = new StringBuilder();
        int i = 4;
        while (i-- > 0) {
            if (num == 0) {
                break;
            }
            int temp = num % 10;
            if (i == 3) {
                if (temp == 4) {
                    res.append("IV");
                } else if (temp == 9) {
                    res.append("IX");
                } else if (temp >= 5) {
                    res.append("V");
                    while (temp-- > 5) {
                        res.append("I");
                    }
                } else {
                    while (temp-- > 0) {
                        res.append("I");
                    }
                }
            } else if (i == 2) {
                if (temp == 4) {
                    res.insert(0, "XL");
                } else if (temp == 9) {
                    res.insert(0, "XC");
                } else if (temp >= 5) {
                    while (temp-- > 5) {
                        res.insert(0, "X");
                    }
                    res.insert(0, "L");
                } else {
                    while (temp-- > 0) {
                        res.insert(0, "X");
                    }
                }
            } else if (i == 1) {
                if (temp == 4) {
                    res.insert(0, "CD");
                } else if (temp == 9) {
                    res.insert(0, "CM");
                } else if (temp >= 5) {
                    while (temp-- > 5) {
                        res.insert(0, "C");
                    }
                    res.insert(0, "D");
                } else {
                    while (temp-- > 0) {
                        res.insert(0, "C");
                    }
                }
            } else {
                while (temp-- > 0) {
                    res.insert(0, "M");
                }
            }
            num /= 10;
        }
        return res.toString();
    }

    /**
     * 贪心
     * @param num int
     * @return roman
     */
    public String intToRoman_2(int num) {
        int[] nums = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] roman = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int index = 0;
        StringBuilder res = new StringBuilder();
        while (index < nums.length) {
            while (num >= nums[index]) {
                res.append(roman[index]);
                num -= nums[index];
            }
            index++;
        }
        return res.toString();
    }

    /**
     * 枚举，空间换时间
     * @param num int
     * @return roman
     */
    public String intToRoman_3(int num) {
        String[] roman1 = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        String[] roman2 = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] roman3 = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] roman4 = {"", "M", "MM", "MMM"};
        StringBuilder res = new StringBuilder();
        res.append(roman4[num / 1000]);
        res.append(roman3[num / 100 % 10]);
        res.append(roman2[num / 10 % 10]);
        res.append(roman1[num % 10]);
        return res.toString();
    }

    public static void main(String[] args) {
        LeetCode12 lc = new LeetCode12();
        System.out.println(lc.intToRoman(1994));
        System.out.println(lc.intToRoman_2(1994));
        System.out.println(lc.intToRoman_3(1994));
    }
}
