package edu.jhun.test;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class SuccessFrame extends JFrame {
	
	private JMenuBar jMenuBar;
	private JMenu jMenu;
	private JMenuItem itemInsert,itemUpdate,itemDelete,itemExit;

	public SuccessFrame() throws HeadlessException {
		
		Toolkit kit= Toolkit.getDefaultToolkit();
		Dimension screenSize=kit.getScreenSize();
		int screenWidth=screenSize.width;
		int screenHeight= screenSize.height;
		Image img=kit.getImage("Imgs/qqBrowser.png");
		
		this.setTitle("登陆成功");
		this.setIconImage(img);		
		this.setSize(screenWidth*3/4, screenHeight*3/4);
		//相对值使屏幕居中
		this.setLocation(screenWidth/2-this.getWidth()/2, screenHeight/2-this.getHeight()/2);
		int frameWidth=this.getWidth();
		int frameHeight=this.getHeight();
//		Container contentPane= this.getContentPane();
//		contentPane.add(new FaceJpanel());
//		contentPane.add(new MenuPanel());
		
		JDesktopPane jDesktopPane= new JDesktopPane();
		jDesktopPane.setLayout(null);
		this.add(jDesktopPane);
		
		jMenuBar = new JMenuBar();
		jMenu = new JMenu("用户管理");
		jMenuBar.add(jMenu);
		
		itemInsert = new JMenuItem("新增用户");
		itemInsert.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				AddUserFrame addUser=new AddUserFrame();
				addUser.setLocation((frameWidth-addUser.getWidth())/2,(frameHeight-addUser.getHeight())/2-50);
				jDesktopPane.add(addUser);
				
			}
		});
		itemUpdate = new JMenuItem("编辑用户");
		itemDelete = new JMenuItem("删除用户");
		itemExit = new JMenuItem("退出");
		jMenu.add(itemInsert);
		jMenu.add(itemUpdate);
		jMenu.add(itemDelete);
		jMenu.addSeparator();
		jMenu.add(itemExit);
		
		this.setJMenuBar(jMenuBar);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}
	
//	public static void main(String[] args) {
//		new SuccessFrame();
//	}
	
}
