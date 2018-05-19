package com.gsafety.test.unit16;

import java.lang.reflect.Array;

public class Test16_1 {

    //扩展数组长度
    public Object extendArray(Object obj, int newLength){
        Class c = obj.getClass();
        if (!c.isArray()){
            System.out.println("要扩展的不是一个数组！");
            return null;
        }
        Class componentType = c.getComponentType();
        int length = Array.getLength(obj);
        Object newArray = Array.newInstance(componentType,newLength);
        System.arraycopy(obj, 0, newArray, 0, Math.min(length, newLength));

        return newArray;
    }

    //打印数组
    public void printArray(Object obj){
        Class c = obj.getClass();
        if (!c.isArray()){
            System.out.println("要打印的不是一个数组！");
            return;
        }
        System.out.println("当前数组"+c.getName()+"长度为："+Array.getLength(obj));
        for (int i = 0; i < Array.getLength(obj); i++) {
            System.out.print(Array.get(obj,i)+" ");
        }
        System.out.println();
    }

    //测试主函数
    public static void main(String[] args) {
        Test16_1 t = new Test16_1();
        double[] doubles = new double[]{88.6, 6.23, 5.06};
        //打印原始数组
        t.printArray(doubles);
        //扩展数组
        Object newArray = t.extendArray(doubles,10);
        t.printArray(newArray);
    }
}
