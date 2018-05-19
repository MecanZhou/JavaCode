package com.edu.test;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;


public class TankTest extends JFrame implements KeyListener{
	
	private JLabel jLabel;
	private ImageIcon imgs,imgs1,imgs2,imgs3;

	public TankTest() throws HeadlessException {
		Toolkit kit= Toolkit.getDefaultToolkit();
		Dimension screenSize=kit.getScreenSize();
		int screenWidth=screenSize.width;
		int screenHeight= screenSize.height;
		Image img=kit.getImage("Imgs/tank.jpg");
		
		this.setTitle("Demo");
		this.setIconImage(img);		
		this.setSize(450,700);
		this.setLocationRelativeTo(null);
//		this.setLocation(screenWidth/2-this.getWidth()/2, screenHeight/2-this.getWidth()/2);		
		this.getContentPane().setBackground(Color.BLACK);
//		this.setLayout(new FlowLayout());
		
		jLabel = new JLabel();
		imgs=new ImageIcon("Imgs/logo.png");
		imgs1=new ImageIcon("Imgs/logo1.png");
		imgs2=new ImageIcon("Imgs/logo2.png");
		imgs3=new ImageIcon("Imgs/logo3.png");
		jLabel.setIcon(imgs1);		
		this.add(jLabel);
		addKeyListener(this);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		
		new TankTest();

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO 自动生成的方法存根
		int code = e.getKeyCode();
		int step=10;
//		System.out.println(code);
		Point p=jLabel.getLocation();
		if (code==38||code==87) {
			System.out.println("向上");
			jLabel.setIcon(imgs);
			p.setLocation(p.getX(),p.getY()-step);
			jLabel.setLocation(p);
		} else if(code==40||code==83){
			System.out.println("向下");
			jLabel.setIcon(imgs1);
			p.setLocation(p.getX(),p.getY()+step);
			jLabel.setLocation(p);
		}else if (code==37||code==65) {
			System.out.println("向左");
			jLabel.setIcon(imgs2);
			p.setLocation(p.getX()-step,p.getY());
			jLabel.setLocation(p);
		}else if (code==39||code==68) {
			System.out.println("向右");
			jLabel.setIcon(imgs3);
			p.setLocation(p.getX()+step,p.getY());
			jLabel.setLocation(p);
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO 自动生成的方法存根
		
	}

}
