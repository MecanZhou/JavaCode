package com.gsafety.exception;

public class MyException extends Exception {
    private String msc;
    public MyException(String msc) {
       super(msc);
    }
    public String getMsc() {
        return msc;
    }
}
