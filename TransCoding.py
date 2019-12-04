# encoding=utf-8
'''
问题描述：给字母编码，'A'-1...'Z'-26
给定一个只包含数字的非空字符串，计算解码方法总数
示例："12" -> 1 2(AB) or 12(L)，输出2
思路：动态规划
dp[i] = { 1                 i>len-1 or (i==len-1 and str[i]!=0)
          dp[i+1]           str[i]=3...9
          dp[i+2]           str[i]=1 && str[i+1]=0
          dp[i+1]+dp[i+2]   str[i]=1 && str[i+1]!=0
          dp[i+2]           str[i]=2 && str[i+1]=0
          dp[i+1]           str[i]=2 && str[i+1]=7...9
          dp[i+1]+dp[i+2]   str[i]=2 && str[i+1]=1...6
所求即为dp[0]
采用备忘结构，保存已经求过的transcoding(i)，减少运算量
'''


def transcoding(i):
    if dp[i] != 0:
        return dp[i]
    if （i == len(str) - 1 and int(str[i]) != 0) or i > len(str) - 1:
        dp[i] = 1
    elif 3 <= int(str[i]) <= 9:
        dp[i] = transcoding(i + 1)
    elif int(str[i]) == 1:
        if int(str[i + 1]) == 0:
            dp[i] = transcoding(i + 2)
        else:
            dp[i] = transcoding(i + 1) + transcoding(i + 2)
    elif int(str[i]) == 2:
        if int(str[i + 1]) == 0:
            dp[i] = transcoding(i + 2)
        elif 7 <= int(str[i + 1]) <= 9:
            dp[i] = transcoding(i + 1)
        else:
            dp[i] = transcoding(i + 1) + transcoding(i + 2)
    return dp[i]


if __name__ == "__main__":
    str = input("数字字符串为 ")
    dp = [0] * 100
    transcoding(0)
    print(dp[0])
