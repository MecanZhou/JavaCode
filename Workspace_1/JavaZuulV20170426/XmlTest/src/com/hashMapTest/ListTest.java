package com.hashMapTest;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.collections.KeyValue;



public class ListTest {
	
	public Map<String,Courses> courses;
	public ListTest() {
		this.courses=new HashMap<String, Courses>();
	}
	
	public void AddMap() {
		Scanner console=new Scanner(System.in);
		int i=0;
		while(i<3){
			System.out.println("������Ҫ�����Ŀγ̺ţ�");
			String ID=console.next();
			Courses cs=courses.get(ID);
			if(cs==null){
				System.out.println("������γ�����");
				String name =console.next();
				Courses c= new Courses(ID, name);
				courses.put(ID, c);
				System.out.println(ID+"��ӳɹ���");
				i++;
			}else {
				System.out.println("��ռ�ã�");
				continue;
			}
		}
	}
	
	public void KeySet() {
		Set<String> keySet =courses.keySet();
		System.out.println("����"+courses.size());
		for(String cid:keySet){
			Courses co=courses.get(cid);
			if(cid!=null)
				System.out.println("ming:"+co.cname);
		}
			
	}

	public static void main(String[] args) {
		ListTest l=new ListTest();
		l.AddMap();
		l.KeySet();
	}

}
