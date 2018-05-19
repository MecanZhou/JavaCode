package com.gsafety.bean;

public class StaticInnerClass {

    static int a = 100;
    static class Inner{
        public void doit(){
            System.out.println("外部类a值为："+a);
        }
    }

    public static void main(String[] args) {
        Inner inner = new Inner();
        inner.doit();
    }
}
