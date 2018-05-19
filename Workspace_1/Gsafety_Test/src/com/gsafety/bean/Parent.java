package com.gsafety.bean;

public class Parent {

    private String name;

    public Parent(String name) {
        this.name = name;
        System.out.println("Parent Constructor2！");
    }

    public Parent() {
        System.out.println("Parent Constructor1！");
    }

    public void methodOne(){
        System.out.println("Parent:MethodOne!");
    }

    public void methodTwo(){
        System.out.println("Parent:MethodTwo!");
    }
}
