package com.gsafety.bean;

interface AnoymsInnerClass{
}

public class OuterClass {
    public AnoymsInnerClass doit(){
        return new AnoymsInnerClass(){

           private String value = "AnoymsInnerClass!";

            public String getValue() {
                return value;
            }

            @Override
            public String toString() {
                return "$classname{" +
                        "value='" + value + '\'' +
                        '}';
            }
        };
    }

    public static void main(String[] args) {
        OuterClass o = new OuterClass();
        System.out.println(o.doit().toString());
    }
}
