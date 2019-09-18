import java.util.Arrays;

public class Book {

    private int PRICE = 8;
    private double DISCOUNT2 = 0.05;
    private double DISCOUNT3 = 0.10;
    private double DISCOUNT4 = 0.20;
    private double DISCOUNT5 = 0.25;

    private double F(int[] CntArr) {
        Arrays.sort(CntArr);
        assert CntArr.length == 5 : "长度不为5！";
        if (CntArr[0] == 0 && CntArr[1] == 0 && CntArr[2] == 0 && CntArr[3] == 0 && CntArr[4] == 0) {
            return 0;
        }
        double result = Double.MAX_VALUE;
        for(int temp : CntArr){
            System.out.print(temp + " ");
        }System.out.println();
        if (CntArr[0] >= 1) {
            int[] CntArr5 = new int[5];
            for (int i = 0; i < CntArr.length; i++) {
                CntArr5[i] = CntArr[i] - 1;
            }
            result = Math.min(result, 5 * PRICE * (1 - DISCOUNT5) + F(CntArr5));
            System.out.println("5 " + result);
        }
        if (CntArr[1] >= 1) {
            int[] CntArr4 = new int[5];
            CntArr4[0] = CntArr[0];
            for (int i = 1; i < CntArr.length; i++) {
                CntArr4[i] = CntArr[i] - 1;
            }
            result = Math.min(result, 4 * PRICE * (1 - DISCOUNT4) + F(CntArr4));
            System.out.println("4 " + result);
        }
        if(CntArr[2] >= 1){
            int[] CntArr3 = new int[5];
            CntArr3[0] = CntArr[0];
            CntArr3[1] = CntArr[1];
            for (int i = 2; i < CntArr.length; i++) {
                CntArr3[i] = CntArr[i] - 1;
            }
            result = Math.min(result, 3 * PRICE * (1 - DISCOUNT3) + F(CntArr3));
            System.out.println("3 " + result);
        }
        if(CntArr[3] >= 1){
            int[] CntArr2 = new int[5];
            CntArr2[0] = CntArr[0];
            CntArr2[1] = CntArr[1];
            CntArr2[2] = CntArr[2];
            CntArr2[3] = CntArr[3] - 1;
            CntArr2[4] = CntArr[4] - 1;
            result = Math.min(result, 2 * PRICE * (1 - DISCOUNT2) + F(CntArr2));
            System.out.println("2 " + result);
        }
        if(CntArr[4] >= 1){
            int[] CntArr1 = new int[5];
            CntArr1[0] = CntArr[0];
            CntArr1[1] = CntArr[1];
            CntArr1[2] = CntArr[2];
            CntArr1[3] = CntArr[3];
            CntArr1[4] = CntArr[4] - 1;
            result = Math.min(result, 1 * PRICE + F(CntArr1));
            System.out.println("1 " + result);
        }
        return result;
    }

    public static void main(String[] args) {
        Book book = new Book();
        int[] BookArr = {1, 2, 1, 2, 2};
        System.out.println("最少花费为： " + book.F(BookArr));
    }
}
