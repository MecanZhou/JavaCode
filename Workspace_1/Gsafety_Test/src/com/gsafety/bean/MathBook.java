package com.gsafety.bean;

public class MathBook extends Book{

    public static void main(String[] args) {

        MathBook mathBook = new MathBook();
        mathBook.setBookPage(200);
        System.out.println("子类MathBook 的page 值为："+mathBook.getBookPage());
    }

}
