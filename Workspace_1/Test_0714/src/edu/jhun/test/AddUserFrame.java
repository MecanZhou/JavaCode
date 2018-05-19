package edu.jhun.test;

import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class AddUserFrame extends JInternalFrame {
	
	private JLabel jlbUser,jlbPwd,jlbGender,jlbHobby,jlbQues,jlbAns;
	private JTextField jtfUsername,jtfAnswer;
	private JPasswordField jpfPwd;
	private JRadioButton jrbFamale,jrbMale;
	private JCheckBox jcbSing,jcbBall,jcbRead;
	private JComboBox<String> jcmbQuestion;
	private ButtonGroup bg;
	private JButton btnSave;
	private int x=10,y=30;

	public AddUserFrame() {
		
		this.setTitle("新增用户");
		this.setSize(300,300);
		this.setResizable(false);
		this.setIconifiable(true);
		this.setClosable(true);
		JPanel jPanel= new JPanel(null);
		
		//添加用户名组
		jlbUser = new JLabel("用户名：",SwingConstants.RIGHT);
		jlbUser.setBounds(x,y,80,23);
		jPanel.add(jlbUser);		
		jtfUsername = new JTextField(20);
		jtfUsername.setBounds(jlbUser.getX()+jlbUser.getWidth()+10, jlbUser.getY(), 160, 23);
		jPanel.add(jtfUsername);
		
		//添加密码组
		jlbPwd = new JLabel("密    码：",SwingConstants.RIGHT);
		jlbPwd.setBounds(jlbUser.getX(),jlbUser.getY()+jtfUsername.getHeight()+10,80,23);
		jPanel.add(jlbPwd);		
		jpfPwd = new JPasswordField(20);
		jpfPwd.setBounds(jlbPwd.getX()+jlbPwd.getWidth()+10,jtfUsername.getY()+jtfUsername.getHeight()+10,160,23);
		jPanel.add(jpfPwd);
		
		//添加性别组
		jlbGender = new JLabel("性    别：",SwingConstants.RIGHT);
		jlbGender.setBounds(jlbPwd.getX(),jpfPwd.getY()+jpfPwd.getHeight()+10,80,23);
		jPanel.add(jlbGender);
		jrbFamale = new JRadioButton("男",true);
		jrbFamale.setBounds(jlbGender.getX()+jlbGender.getWidth()+10,jlbGender.getY(),70,23);
		jrbMale= new JRadioButton("女",null);
		jrbMale.setBounds(jrbFamale.getX()+jrbFamale.getWidth()+10,jlbGender.getY(),70,23);
		
		//jrb加入bg后男女选项唯一
		bg= new ButtonGroup();
		bg.add(jrbFamale);
		bg.add(jrbMale);
		jPanel.add(jrbFamale);
		jPanel.add(jrbMale);
		
		jlbHobby = new JLabel("兴    趣：",SwingConstants.RIGHT);
		jlbHobby.setBounds(jlbGender.getX(),jlbGender.getY()+jrbFamale.getHeight()+10,80,23);
		jcbSing = new JCheckBox("唱歌");
		jcbRead = new JCheckBox("阅读");
		jcbBall = new JCheckBox("打球");		
		jcbSing.setBounds(jrbFamale.getX(),jlbHobby.getY(),60,23);
		jcbRead.setBounds(jcbSing.getX()+jcbSing.getWidth()+5,jlbHobby.getY(),60,23);
		jcbBall.setBounds(jcbRead.getX()+jcbRead.getWidth()+5,jlbHobby.getY(),60,23);
		jPanel.add(jlbHobby);
		jPanel.add(jcbSing);
		jPanel.add(jcbRead);
		jPanel.add(jcbBall);
		
		
		jlbQues = new JLabel("问    题：",SwingConstants.RIGHT);
		jlbQues.setBounds(jlbHobby.getX(),jlbHobby.getY()+jlbHobby.getHeight()+10,80,23);
		jPanel.add(jlbQues);
		
		String[] items= {"问题1","问题2","问题3","问题4"};
		jcmbQuestion = new JComboBox<String>(items);
		jcmbQuestion.setBounds(jrbFamale.getX(),jlbQues.getY(),160,23);
		jPanel.add(jcmbQuestion);
		
		jlbAns = new JLabel("答    案：",SwingConstants.RIGHT);
		jlbAns.setBounds(jlbQues.getX(),jlbQues.getY()+jlbQues.getHeight()+10,80,23);
		jPanel.add(jlbAns);
		jtfAnswer = new JTextField(20);
		jtfAnswer.setBounds(jlbAns.getX()+jlbAns.getWidth()+10, jlbAns.getY(), 160, 23);
		jPanel.add(jtfAnswer);
		
		btnSave = new JButton("保存");
		btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String user = jtfUsername.getText();
				String pwd = new String(jpfPwd.getPassword());
				String gender = jrbMale.isSelected()?"男":"女";
				List<String> hobby = new ArrayList<String>();
				if (jcbSing.isSelected()) {
					hobby.add(jcbSing.getText());
				}
				if (jcbRead.isSelected()) {
					hobby.add(jcbRead.getText());
				}
				if (jcbBall.isSelected()) {
					hobby.add(jcbBall.getText());
				}
				String pwdQues =(String)jcmbQuestion.getSelectedItem();
				String pwdAns = jtfAnswer.getText();
				
//				System.out.println("注册信息：");
//				System.out.println("性别："+gender);
//				System.out.println("兴趣爱好：");
				String hobbyStr="";
				for (String string : hobby) {
					hobbyStr=hobbyStr+string+"  ";
				}
//				System.out.println();
//				System.out.println("密码问题："+pwdQues);
//				System.out.println("密码答案："+pwdAns);
				String message="注册信息：\n"+"用户名："+user+"\n性别："+gender+"\n兴趣爱好："+hobbyStr+"\n密码问题："+pwdQues+"\n密码答案："+pwdAns;
				
				JOptionPane.showMessageDialog(null, message);
			}
		});
		btnSave.setBounds(jtfAnswer.getX(),jtfAnswer.getY()+jtfAnswer.getHeight()+20,80,23);
		jPanel.add(btnSave);
		
		this.add(jPanel);
		this.setVisible(true);
		
	}

}
