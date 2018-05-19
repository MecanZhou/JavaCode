package com.gsafety.test.unit9;

public class Test9_1 {

    public static int GetNum(int num1, int num2){
        int s = num1 + (int)(Math.random()*(num2-num1));
        if (s%2==0){
            return s;
        }else {
            return s+1;
        }
    }

    public static void main(String[] args) {
        int sum = 0;
        for (int i = 0; i < 6; i++) {
            int s = GetNum(2,32);
            if (i!=5) {
                System.out.print(s + "+");
            }else {
                System.out.print(s);
            }
            sum+=s;
        }
        System.out.print("="+sum);
    }
}
