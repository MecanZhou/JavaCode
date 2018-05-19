package com.gsafety.test.unit3;

public class Test3_3 {
    public static void main(String[] args) {
        byte mybyte = 124;//声明byte型变量并赋值
        short myshort = 15445;//声明short型变量并赋值
        long mylong = 12546845;//声明long型变量并赋值

        //将所有整形数值转化为int并输出
        int byteToInt = mybyte;
        int shortToInt = myshort;
        int longToInt = (int)mylong;

        System.out.println(byteToInt);
        System.out.println(shortToInt);
        System.out.println(longToInt);
    }
}
