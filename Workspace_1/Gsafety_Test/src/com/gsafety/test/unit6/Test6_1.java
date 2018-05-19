package com.gsafety.test.unit6;

import java.util.Arrays;

public class Test6_1 {
    public static void main(String[] args) {
        int[] arr1 = new int[]{4,9,8,5,6};
        int[] arr2 = Arrays.copyOfRange(arr1,0,4);
        for (int a:arr2 ) {
            System.out.println(a);
        }
    }
}
