public class Chess {
    static final private int HALF_BITS_LENGTH = 4;
    // 这个值是记忆存储单元长度的一半，在这道题里是4bit
    static final private int FULLMASK = 255;
    // 这个数字表示一个全部bit的mask，在二进制表示中，它是11111111
    static final private int RMASK = (FULLMASK >> HALF_BITS_LENGTH);
    // 这个数字表示右bits的mask，在二进制的表示中，它是00001111
    static final private int LMASK = (FULLMASK << HALF_BITS_LENGTH);
    // 这个数字表示左bits的mask，在二进制的表示中，它是11110000
    static final private int GRIDW = 3;
    // 这个数字表示将帅移动范围的行宽度

    static private int RGetter(char b){
        // 这个函数得到了b的右边的值
        return (RMASK & b);
    }

    static private int LGetter(char b){
        // 这个函数得到了b的左边的值
        return ((LMASK & b) >> HALF_BITS_LENGTH);
    }

    static private char RSetter(char b, int n){
        // 这个函数，将b的右边设置成n
        return (char)((LMASK & b) | n);
    }

    static private char LSetter(char b, int n){
        // 这个函数，将b的左边设置成n
        return (char)((RMASK & b) | (n << HALF_BITS_LENGTH));
    }

    public static void method_1(){
        // Method 1
        System.out.println("Method 1: ");
        char b = 0;
        for(b = LSetter(b, 1); LGetter(b) <= GRIDW * GRIDW; b = LSetter(b, LGetter(b) + 1))
            for(b = RSetter(b, 1); RGetter(b) <= GRIDW * GRIDW; b = RSetter(b, RGetter(b) + 1))
                if(LGetter(b) % GRIDW != RGetter(b) % GRIDW)
                    System.out.println("A = " + LGetter(b) + ", B = " + RGetter(b));
    }

    public static void method_2(){
        // Method 2 
        System.out.println("Method 2: ");
        char i = 81;
        while(--i > 0){
            if((i / 9 % 3) == (i % 9 % 3))
                continue;
            System.out.println("A = " + (i / 9 + 1) + ", B = " + (i % 9 + 1));
        }
    }

    public static void method_3(){
        // Method 3
        System.out.println("Method 3: ");
        ch z = new ch();
        for(z.x = 1; z.x <= 9; z.x++)
            for(z.y = 1; z.y <= 9; z.y++)
                if((z.x % 3) != (z.y % 3))
                    System.out.println("A = " + (int)(z.x) + ", B = " + (int)z.y);
    }

    public static void main(String[] args){
        method_1();
        method_2();
        method_3();
    }
}

class ch{
    char x = 0;
    char y = 0;
}
