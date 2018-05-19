package com.edu.swing;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;

public class FirstFrame extends JFrame{	

	/**
	 * 串行缺省标识
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton redButton,greenButton,buleButton;

	public FirstFrame() throws HeadlessException {		
		
		Toolkit kit= Toolkit.getDefaultToolkit();
		Dimension screenSize=kit.getScreenSize();
		int screenWidth=screenSize.width;
		int screenHeight= screenSize.height;
		Image img=kit.getImage("Imgs/qqBrowser.png");
		
		this.setTitle("标题");
		this.setIconImage(img);		
		this.setSize(screenWidth/2, screenHeight/2);
		//相对值使屏幕居中
		this.setLocation(screenWidth/2-this.getWidth()/2, screenHeight/2-this.getWidth()/2);
//		this.setSize(300, 300);
//		this.setResizable(false);		
		
		Container contentPane= this.getContentPane();
		//笑脸案例
		contentPane.add(new FaceJpanel());
		//Graphics绘制案例
//		contentPane.add(new FirstJpanel());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		
		new FirstFrame();

	}

}
