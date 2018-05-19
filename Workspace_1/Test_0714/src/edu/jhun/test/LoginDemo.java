package edu.jhun.test;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedHashSet;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import edu.jhun.bean.Users;
import edu.jhun.dao.UsersDAO;
import edu.jhun.daoImpl.UsersDAOImpl;

public class LoginDemo extends JFrame{
	
	private JPanel jpanel;
	private JLabel userName,passWord;
	private JTextField textID;
	private JPasswordField textPwd;
	private JButton btnLogin, btnExit;
	private int x=40,y=50;

	public LoginDemo() {
		
		Toolkit kit= Toolkit.getDefaultToolkit();
		Dimension screenSize=kit.getScreenSize();
		int screenWidth=screenSize.width;
		int screenHeight= screenSize.height;
		Image img=kit.getImage("Imgs/qqBrowser.png");
		
		this.setTitle("标题");
		this.setIconImage(img);		
		this.setSize(screenWidth/2-300, screenHeight/2-160);		
		//相对值使屏幕居中
		this.setLocation(screenWidth/2-this.getWidth()/2, screenHeight/2-this.getWidth()/2);
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		jpanel = new JPanel();
		jpanel.setLayout(null);
//		System.out.println(this.getWidth());
//		System.out.println(this.getHeight());
//		x=this.getWidth()-380;
//		y=this.getHeight()-240;
		
		userName = new JLabel("用户名", SwingConstants.RIGHT);
		userName.setBounds(x, y, 80, 23);
		jpanel.add(userName);
		
		textID = new JTextField(20);
		textID.setBounds(x+userName.getWidth()+10, y, 160, 23);
		jpanel.add(textID);
		
		passWord = new JLabel("密  码",SwingConstants.RIGHT);
		passWord.setBounds(x, y+50, 80, 23);
		jpanel.add(passWord);
		
		textPwd = new JPasswordField(20);
		textPwd.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				
				if (e.getKeyChar()==KeyEvent.VK_ENTER) {
					btnLogin.doClick();
				}
			}
			
		});
		textPwd.setBounds(x+passWord.getWidth()+10, y+50, 160, 23);
		jpanel.add(textPwd);
		
		btnLogin = new JButton("登录");
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String idStr=textID.getText();
				String pwdStr=new String(textPwd.getPassword());
				UsersDAO users = new UsersDAOImpl();
				List<Users> list = users.findUsersByName(idStr);
				if (list==null||list.size()==0) {
					JOptionPane.showMessageDialog(null, "用户名不存在，请重新登录！");
					textID.setText("");
					textPwd.setText("");
				}
				if (idStr.equals(list.get(0).getUserName())&&pwdStr.equals(list.get(0).getUserPwd())) {
					
					LoginDemo.this.dispose();
					new SuccessFrame();
					
				} else {
					
					JOptionPane.showMessageDialog(null, "用户名或密码错误，请重新登录");
					textID.setText("");
					textPwd.setText("");
					
					return;
				}
			}
		});
		btnLogin.setBounds(textID.getX(), passWord.getY()+passWord.getHeight()+20, 70, 23);
		jpanel.add(btnLogin);
		
		btnExit =new JButton("退出");
		btnExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				int option=JOptionPane.showConfirmDialog(null, "确定要退出吗？");
				if (option==0) {
					System.exit(0);
				}
			}
		});
		btnExit.setBounds(btnLogin.getX()+btnLogin.getWidth()+20, btnLogin.getY(), 70, 23);
		jpanel.add(btnExit);
		
		Container contentPane= this.getContentPane();
		contentPane.add(jpanel);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	

	public static void main(String[] args) {
		
		new LoginDemo();
	}

}
