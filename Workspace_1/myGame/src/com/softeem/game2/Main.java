package com.softeem.game2;

import javax.swing.JFrame;

/**
 * 窗体类
 * @author Administrator
 *
 */
public class Main extends JFrame{

	public Main()
	{
		this.add(new GamePanel());
		this.setTitle("飞机大战v1.0");//设置标题
		this.setSize(450, 700);//设置窗体大小
		this.setResizable(false);//设置窗体禁止改变大小
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//当窗体关闭时自动退出java虚拟机
		this.setLocationRelativeTo(null);//设置窗体显示的相对位置:默认屏幕正中间
		this.setVisible(true);//显示窗体
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
