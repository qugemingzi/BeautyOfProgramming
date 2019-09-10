#include <iostream>
using namespace std;

#define HALF_BITS_LENGTH 4
// ���ֵ�Ǽ���洢��Ԫ���ȵ�һ�룬�����������4bit
#define FULLMASK 255
// ������ֱ�ʾһ��ȫ��bit��mask���ڶ����Ʊ�ʾ�У�����11111111
#define RMASK (FULLMASK >> HALF_BITS_LENGTH)
// ������ֱ�ʾ��bits��mask���ڶ����Ƶı�ʾ�У�����00001111
#define LMASK (FULLMASK << HALF_BITS_LENGTH)
// ������ʾ��bits��mask���ڶ����Ƶı�ʾ�У�����11110000
#define RGET(b) (RMASK & b)
// �����õ���b���ұߵ�ֵ
#define LGET(b) ((LMASK & b) >> HALF_BITS_LENGTH)
// �����õ���b����ߵ�ֵ
#define RSET(b, n) (b = ((LMASK & b) | n))
// ����꣬��b���ұ����ó�n
#define LSET(b, n) (b = ((RMASK & b) | (n << HALF_BITS_LENGTH)))
// ����꣬��b��������ó�n
#define GRIDW 3
// ������ֱ�ʾ��˧�ƶ���Χ���п��

struct {
        unsigned char x:4;
        unsigned char y:4;
}z;

int main (){
    // method 1
    cout << "Method 1: " << endl;
    unsigned char b = 0;
    for(LSET(b, 1); LGET(b) <= GRIDW * GRIDW; LSET(b, (LGET(b) + 1)))
        for(RSET(b, 1); RGET(b) <= GRIDW * GRIDW; RSET(b, (RGET(b) + 1)))
            if(LGET(b) % GRIDW != RGET(b) % GRIDW)
                cout << "A = " << LGET(b) << ", B = " << RGET(b) << endl;

    // method 2
    cout << "Method 2: " << endl;
    unsigned char i = 81;
    while(i-- && i >= 0){
        if(i / 9 % 3 == i % 9 % 3)
            continue;
        cout << "A = " << i / 9 + 1 << ", B = " << i % 9 + 1 << endl;
    }

    // method 3
    cout << "Method 3: " << endl;
    for(z.x = 1; z.x <= 9; z.x++)
        for(z.y = 1; z.y <= 9; z.y++)
            if(z.x % 3 != z.y % 3)
                cout << "A = " << static_cast<int>(z.x) << ", B = " << static_cast<int>(z.y) << endl;
    return 0;
}

