package com.gsafety.test.unit17;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Test17_2<T extends List> {

    private T over;

    public T getOver() {
        return over;
    }

    public void setOver(T over) {
        this.over = over;
    }

    public static void main(String[] args) {
        Test17_2<LinkedList> l1 = new Test17_2<LinkedList>();
        Test17_2<ArrayList> l2 = new Test17_2<ArrayList>();
        LinkedList<String> ll = new LinkedList<String>();
        ArrayList<String> al = new ArrayList<String>();
        l1.setOver(ll);
        l1.getOver().add(0,"这是LinkList里的元素");
        l2.setOver(al);
        l2.getOver().add(0,"这是ArrayList里的元素");
        for (int i = 0; i < ll.size(); i++) {
            System.out.println(ll.get(i));
        }
        for (int i = 0; i < al.size(); i++) {
            System.out.println(al.get(i));
        }
    }
}
