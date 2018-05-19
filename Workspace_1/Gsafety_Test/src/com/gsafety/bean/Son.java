package com.gsafety.bean;

public class Son extends Parent {

    private String name;

    public Son() {
        System.out.println("Son Constructor1！");
    }

    public Son(String name) {
        this.name = name;
        System.out.println("Son Constructor2！");
    }

    @Override
    public void methodTwo() {
        System.out.println("Override: MethodTwo!");
    }

    public static void main(String[] args) {
        Son s  = new Son("Mecan");
        Parent p = s;
        p.methodTwo();
    }
}
