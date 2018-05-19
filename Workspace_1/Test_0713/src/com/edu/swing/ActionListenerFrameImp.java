package com.edu.swing;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ActionListenerFrameImp extends JFrame implements ActionListener{	

	/**
	 * 串行缺省标识
	 */
	private static final long serialVersionUID = 1L;
	
	JButton redButton,greenButton,blueButton;

	public ActionListenerFrameImp() throws HeadlessException {		
		
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
		
		
		Container contentPane= this.getContentPane();
		//事件处理案例
		contentPane.setLayout(new FlowLayout());
		
		redButton=new JButton("红色");
		redButton.addActionListener(this);
		greenButton=new JButton("绿色");
		greenButton.addActionListener(this);
		blueButton=new JButton("蓝色");
		blueButton.addActionListener(this);
		
		contentPane.add(redButton);
		contentPane.add(greenButton);
		contentPane.add(blueButton);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		
		new ActionListenerFrameImp();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object eventSource= e.getSource();
		if (eventSource==redButton) {
			getContentPane().setBackground(Color.RED);
		} else if(eventSource==greenButton){
			getContentPane().setBackground(Color.GREEN);
		}else if (eventSource==blueButton) {
			getContentPane().setBackground(Color.BLUE);
		}
		
	}

}
