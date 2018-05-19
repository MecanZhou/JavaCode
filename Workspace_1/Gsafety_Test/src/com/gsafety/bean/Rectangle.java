package com.gsafety.bean;

public class Rectangle {

    private double width;
    private double height;

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double getArea(){
        return this.height*this.width;
    }

    public static void main(String[] args) {
        Rectangle r= new Rectangle(125,40);
        System.out.println("矩形的宽为："+r.width+", 高为："+r.height+", 面积为："+ r.getArea());
    }
}
