package com.gsafety.test.unit5;

public class Test5_2 {
    public static void main(String[] args) {
        String str1 = "fjdadfshgfdla";
        String str2 = "jdDaohklfajdi";

        if (str1.substring(2,4).equalsIgnoreCase(str2.substring(2,4))){
            System.out.println(str1+"与"+str2+"两字子串相同！");
        }else {
            System.out.println(str1+"与"+str2+"两个子串并不相同！");
        }
    }
}
