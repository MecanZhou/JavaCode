package com.gsafety.bean;

import com.gsafety.exception.MyException;

public class Main {

    public static void main(String[] args) {
        // write your code here
        Student student_1 = new Student("001","Andy", 15);
        try {
            System.out.println( student_1.speak(1001));
        } catch (MyException e) {
            System.out.println(e);
        }

    }
}
