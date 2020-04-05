package algorithm;

/**
 * @author pray chow
 * 大数相加
 */
public class BigNumAdd {

    public String add(String str1, String str2) {
        String result = "";
        str1 = new StringBuilder(str1).reverse().toString();
        str2 = new StringBuilder(str2).reverse().toString();
        boolean flag = false;
        for(int i  = 0; i < Math.max(str1.length(), str2.length()); ++i) {
            int num1 = (i < str1.length()) ? str1.charAt(i) - '0' : 0;
            int num2 = (i < str2.length()) ? str2.charAt(i) - '0' : 0;
            int sum = num1 + num2 + (flag ? 1 : 0);
            result = (sum % 10) + result;
            flag = sum > 9 ? true : false;
        }
        return (flag ? "1" + result : result);
    }

    public static void main(String[] args) {
        BigNumAdd bigNumAdd = new BigNumAdd();
        String str1 = "423456789";
        String str2 = "898765432";
        System.out.println(bigNumAdd.add(str1, str2));
    }
}
