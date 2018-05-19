package com.gsafety.test.unit9;

import java.text.DecimalFormat;

public class Test9_2 {

    public static String GetArea(double r){
        DecimalFormat myFormat = new DecimalFormat("###.00000");
        return myFormat.format(Math.PI*(r*r));
    }

    public static void main(String[] args) {
        String res = GetArea(5);
        System.out.println(res);
    }
}
