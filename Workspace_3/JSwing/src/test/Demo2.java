package test;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import sun.reflect.generics.tree.Tree;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Demo2 extends JFrame{

	/**
	 * ����ϵͳǰ��
	*/
	
	public Demo2() {
		
		this.setSize(1000, 600);
		int windowWidth = this.getWidth(); //��ô��ڿ�
		int windowHeight = this.getHeight(); //��ô��ڸ�
		Toolkit kit = Toolkit.getDefaultToolkit(); //���幤�߰�
		Dimension screenSize = kit.getScreenSize(); //��ȡ��Ļ�ĳߴ�
		int screenWidth = screenSize.width; //��ȡ��Ļ�Ŀ�
		int screenHeight = screenSize.height; //��ȡ��Ļ�ĸ�
		this.setLocation(screenWidth/2-windowWidth/2, screenHeight/2-windowHeight/2);//���ô��ھ�����ʾ
		//this.setLocation(200, 100);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.setSize(10, 5);
		
		
		JMenu mnNewMenu = new JMenu("\u6587\u4EF6");
		mnNewMenu.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		mnNewMenu.setPreferredSize(new Dimension(50, 20));
		menuBar.add(mnNewMenu);
		
		
		
		JMenu mnNewMenu_2 = new JMenu("\u4EFF\u771F");
		mnNewMenu_2.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		mnNewMenu_2.setPreferredSize(new Dimension(50, 20));
		menuBar.add(mnNewMenu_2);
		
		JMenu menu = new JMenu("\u7A97\u53E3");
		menu.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		menu.setPreferredSize(new Dimension(50, 20));
		menuBar.add(menu);
		
		JCheckBoxMenuItem checkBoxMenuItem = new JCheckBoxMenuItem("\u4EFF\u771F\u6A21\u578B");
		checkBoxMenuItem.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent arg0) {
			
			}
		});
		menu.add(checkBoxMenuItem);
		
		JCheckBoxMenuItem checkBoxMenuItem_1 = new JCheckBoxMenuItem("\u4EFF\u771F\u5C5E\u6027");
		menu.add(checkBoxMenuItem_1);
		
		JToolBar toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		JToolBar toolBar_1 = new JToolBar();
		toolBar_1.setSize(new Dimension(20, 20));
		toolBar_1.setPreferredSize(new Dimension(20, 20));
		toolBar_1.setBackground(Color.GRAY);
		toolBar.add(toolBar_1);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.WEST);
		
		JTree tree1 = new JTree();
		panel.add(tree1);
		
		this.setVisible(true);
	} 
	public static void main(String[] args) {
		
		Demo2 demo2=new Demo2();
		
	}

}
