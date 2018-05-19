package com.gsafety.test.unit17;

import com.gsafety.util.Constants;

public class Test17_1 {

    public static void getEnum(Constants c){
        switch (c){
            case Constants_A:
                System.out.println(Constants.Constants_A);
                break;
            case Constants_B:
                System.out.println(Constants.Constants_B);
                break;
            case Constants_C:
                System.out.println(Constants.Constants_C);
                break;
        }
    }

    public static void main(String[] args) {
        Test17_1.getEnum(Constants.Constants_A);
        Test17_1.getEnum(Constants.Constants_C);
    }
}
