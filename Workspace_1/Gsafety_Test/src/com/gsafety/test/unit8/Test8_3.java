package com.gsafety.test.unit8;

public class Test8_3 {

    public static void main(String[] args) {
        String str1 = "java";
        Boolean b = new Boolean(str1);
        String str2  = b.toString();
        System.out.println(str1);
        System.out.println(str2);
        if (!str1.equals(str2)){
            System.out.println("不相同！");
        }
    }
}
