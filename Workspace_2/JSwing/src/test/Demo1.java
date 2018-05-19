package test;
/**
 *BorderLayout 测试
 */

import java.awt.*;

import javax.swing.*;

//① 继承JFrame
public class Demo1 extends JFrame{
	 //②定义组件
	 JButton jButton1, jButton2, jButton3, jButton4, jButton5;
	 
	 
	 public Demo1() {
		//③创建组件
		jButton1 = new JButton("North");
		jButton2 = new JButton("South");
		jButton3 = new JButton("West");
		jButton4 = new JButton("East");
		jButton5 = new JButton("Center");
		
		//④添加组件
		this.add(jButton1, BorderLayout.NORTH);
		this.add(jButton2, BorderLayout.SOUTH);
		this.add(jButton3, BorderLayout.WEST);
		this.add(jButton4, BorderLayout.EAST);
		this.add(jButton5, BorderLayout.CENTER);
		
		//⑤设置布局,默认边界布局
		//this.setLayout(new FlowLayout());
		
		//⑥设置窗体
		this.setTitle("BorderLayout");
		this.setSize(500, 500);
		this.setLocation(300, 200);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//⑦显示窗体
		this.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		//⑧实例化窗体
		Demo1 demo1 = new Demo1();

	}

}
