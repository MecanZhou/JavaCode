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

public class InnerClassFrame extends JFrame{	

	/**
	 * ����ȱʡ��ʶ
	 */
	private static final long serialVersionUID = 1L;
	
	JButton redButton,greenButton,blueButton;

	public InnerClassFrame() throws HeadlessException {		
		
		Toolkit kit= Toolkit.getDefaultToolkit();
		Dimension screenSize=kit.getScreenSize();
		int screenWidth=screenSize.width;
		int screenHeight= screenSize.height;
		Image img=kit.getImage("Imgs/qqBrowser.png");
		
		this.setTitle("����");
		this.setIconImage(img);		
		this.setSize(screenWidth/2, screenHeight/2);
		//���ֵʹ��Ļ����
		this.setLocation(screenWidth/2-this.getWidth()/2, screenHeight/2-this.getWidth()/2);
		
		
		Container contentPane= this.getContentPane();
		//�¼�������
		contentPane.setLayout(new FlowLayout());
		
		redButton=new JButton("��ɫ");
		redButton.addActionListener(new ChangeColorActionListener(this));
		greenButton=new JButton("��ɫ");
		greenButton.addActionListener(new ChangeColorActionListener(this));
		blueButton=new JButton("��ɫ");
		blueButton.addActionListener(new ChangeColorActionListener(this));
		
		contentPane.add(redButton);
		contentPane.add(greenButton);
		contentPane.add(blueButton);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	class ChangeColorActionListener implements ActionListener{
		
		private InnerClassFrame inner;
		
		public ChangeColorActionListener(InnerClassFrame inner) {
			super();
			this.inner = inner;
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

	public static void main(String[] args) {
		
		new InnerClassFrame();

	}

}
