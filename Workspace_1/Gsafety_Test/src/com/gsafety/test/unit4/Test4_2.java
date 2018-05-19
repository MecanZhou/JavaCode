package com.gsafety.test.unit4;

/**
 * 用循环控制实现控制台输出菱形
 */

public class Test4_2 {
    public static void main(String[] args) {
        int n = 5;//控制菱形对角线长度
        //输出菱形上半部分
        for (int i = 1; i <= n; i++) {
            for (int j = n - 1; j >= i; j--) {
                System.out.print(" ");//缺省打印空格
            }
            for (int k = 1; k <= 2 * i - 1; k++) {
                System.out.print("*");//打印*填充菱形
            }
            System.out.println();//打印一行后换行
        }
        //输出菱形下半部分
        for (int i = n - 1; i >= 1; i--) {
            for (int j = i; j <= n - 1; j++) {
                System.out.print(" ");
            }
            for (int k = 1; k <= 2 * i - 1; k++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
