package test;
/**
 *BorderLayout ����
 */

import java.awt.*;

import javax.swing.*;

//�� �̳�JFrame
public class Demo1 extends JFrame{
	 //�ڶ������
	 JButton jButton1, jButton2, jButton3, jButton4, jButton5;
	 
	 
	 public Demo1() {
		//�۴������
		jButton1 = new JButton("North");
		jButton2 = new JButton("South");
		jButton3 = new JButton("West");
		jButton4 = new JButton("East");
		jButton5 = new JButton("Center");
		
		//��������
		this.add(jButton1, BorderLayout.NORTH);
		this.add(jButton2, BorderLayout.SOUTH);
		this.add(jButton3, BorderLayout.WEST);
		this.add(jButton4, BorderLayout.EAST);
		this.add(jButton5, BorderLayout.CENTER);
		
		//�����ò���,Ĭ�ϱ߽粼��
		//this.setLayout(new FlowLayout());
		
		//�����ô���
		this.setTitle("BorderLayout");
		this.setSize(500, 500);
		this.setLocation(300, 200);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//����ʾ����
		this.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		//��ʵ��������
		Demo1 demo1 = new Demo1();

	}

}
