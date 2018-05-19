package com.gsafety.bean;

import com.gsafety.exception.MyException;

public class Student {
    private String stuNum ;
    private String stuName ;
    private int stuAge ;

    @Override
    public String toString() {
        return "Student{" +
                "stuNum='" + stuNum + '\'' +
                ", stuName='" + stuName + '\'' +
                ", stuAge=" + stuAge +
                '}';
    }

    public String speak(int m) throws MyException {
        if (m>1000){
            throw new MyException("不能大于1000！");
        }
        return "Speak方法被执行！";
    }

    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public int getStuAge() {
        return stuAge;
    }

    public void setStuAge(int stuAge) {
        this.stuAge = stuAge;
    }

    public Student(String stuNum, String stuName, int stuAge) {
        this.stuNum = stuNum;
        this.stuName = stuName;
        this.stuAge = stuAge;
    }
}
