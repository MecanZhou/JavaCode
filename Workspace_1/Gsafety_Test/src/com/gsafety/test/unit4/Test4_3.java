package com.gsafety.test.unit4;

/**
 * 计算1+1/2!+1/3!+...+1/20!并输出结果
 */

public class Test4_3 {
    public static void main(String[] args) {
        int n = 20;//指定算数阶乘最大值
        double sum = 0;
        //控制+运算
        for (double i = 1; i <= n; i++) {
            double s = 1;
            //控制*运算
            for (double j = 1; j <= i; j++) {
                s*=j;
            }
            sum+=1/s;
        }
        System.out.println("若n=20，1+1/2!+1/3!+...+1/(n-1)!+1/n!= "+ sum);
    }
}
