package com.gsafety.test.unit14;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Test14_1 {

    public static void main(String[] args) {
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i+1);
        }
        list.add(9);
        System.out.println("此时集合list中包含以下元素：");
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i)+" ");
            if (list.get(i)%10==0)
                System.out.println();
        }
        System.out.println();
        list.remove(10);
        System.out.println("删除索引位置为10的值后，集合list中包含以下元素：");
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i)+" ");
            if (list.get(i)%10==0)
                System.out.println();
        }
    }
}
