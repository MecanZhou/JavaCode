package com.gsafety.bean;

public class Number {

    public double count(double x, double y){
        return x*y;
    }

    public static void main(String[] args) {
        Number n = new Number();
        double res = 0;
        try {
            res = n.count(455,(9/0));
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Result:"+res);
    }
}
