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
		
		this.setTitle("�����û�");
		this.setSize(300,300);
		this.setResizable(false);
		this.setIconifiable(true);
		this.setClosable(true);
		JPanel jPanel= new JPanel(null);
		
		//����û�����
		jlbUser = new JLabel("�û�����",SwingConstants.RIGHT);
		jlbUser.setBounds(x,y,80,23);
		jPanel.add(jlbUser);		
		jtfUsername = new JTextField(20);
		jtfUsername.setBounds(jlbUser.getX()+jlbUser.getWidth()+10, jlbUser.getY(), 160, 23);
		jPanel.add(jtfUsername);
		
		//���������
		jlbPwd = new JLabel("��    �룺",SwingConstants.RIGHT);
		jlbPwd.setBounds(jlbUser.getX(),jlbUser.getY()+jtfUsername.getHeight()+10,80,23);
		jPanel.add(jlbPwd);		
		jpfPwd = new JPasswordField(20);
		jpfPwd.setBounds(jlbPwd.getX()+jlbPwd.getWidth()+10,jtfUsername.getY()+jtfUsername.getHeight()+10,160,23);
		jPanel.add(jpfPwd);
		
		//����Ա���
		jlbGender = new JLabel("��    ��",SwingConstants.RIGHT);
		jlbGender.setBounds(jlbPwd.getX(),jpfPwd.getY()+jpfPwd.getHeight()+10,80,23);
		jPanel.add(jlbGender);
		jrbFamale = new JRadioButton("��",true);
		jrbFamale.setBounds(jlbGender.getX()+jlbGender.getWidth()+10,jlbGender.getY(),70,23);
		jrbMale= new JRadioButton("Ů",null);
		jrbMale.setBounds(jrbFamale.getX()+jrbFamale.getWidth()+10,jlbGender.getY(),70,23);
		
		//jrb����bg����Ůѡ��Ψһ
		bg= new ButtonGroup();
		bg.add(jrbFamale);
		bg.add(jrbMale);
		jPanel.add(jrbFamale);
		jPanel.add(jrbMale);
		
		jlbHobby = new JLabel("��    Ȥ��",SwingConstants.RIGHT);
		jlbHobby.setBounds(jlbGender.getX(),jlbGender.getY()+jrbFamale.getHeight()+10,80,23);
		jcbSing = new JCheckBox("����");
		jcbRead = new JCheckBox("�Ķ�");
		jcbBall = new JCheckBox("����");		
		jcbSing.setBounds(jrbFamale.getX(),jlbHobby.getY(),60,23);
		jcbRead.setBounds(jcbSing.getX()+jcbSing.getWidth()+5,jlbHobby.getY(),60,23);
		jcbBall.setBounds(jcbRead.getX()+jcbRead.getWidth()+5,jlbHobby.getY(),60,23);
		jPanel.add(jlbHobby);
		jPanel.add(jcbSing);
		jPanel.add(jcbRead);
		jPanel.add(jcbBall);
		
		
		jlbQues = new JLabel("��    �⣺",SwingConstants.RIGHT);
		jlbQues.setBounds(jlbHobby.getX(),jlbHobby.getY()+jlbHobby.getHeight()+10,80,23);
		jPanel.add(jlbQues);
		
		String[] items= {"����1","����2","����3","����4"};
		jcmbQuestion = new JComboBox<String>(items);
		jcmbQuestion.setBounds(jrbFamale.getX(),jlbQues.getY(),160,23);
		jPanel.add(jcmbQuestion);
		
		jlbAns = new JLabel("��    ����",SwingConstants.RIGHT);
		jlbAns.setBounds(jlbQues.getX(),jlbQues.getY()+jlbQues.getHeight()+10,80,23);
		jPanel.add(jlbAns);
		jtfAnswer = new JTextField(20);
		jtfAnswer.setBounds(jlbAns.getX()+jlbAns.getWidth()+10, jlbAns.getY(), 160, 23);
		jPanel.add(jtfAnswer);
		
		btnSave = new JButton("����");
		btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String user = jtfUsername.getText();
				String pwd = new String(jpfPwd.getPassword());
				String gender = jrbMale.isSelected()?"��":"Ů";
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
				
//				System.out.println("ע����Ϣ��");
//				System.out.println("�Ա�"+gender);
//				System.out.println("��Ȥ���ã�");
				String hobbyStr="";
				for (String string : hobby) {
					hobbyStr=hobbyStr+string+"  ";
				}
//				System.out.println();
//				System.out.println("�������⣺"+pwdQues);
//				System.out.println("����𰸣�"+pwdAns);
				String message="ע����Ϣ��\n"+"�û�����"+user+"\n�Ա�"+gender+"\n��Ȥ���ã�"+hobbyStr+"\n�������⣺"+pwdQues+"\n����𰸣�"+pwdAns;
				
				JOptionPane.showMessageDialog(null, message);
			}
		});
		btnSave.setBounds(jtfAnswer.getX(),jtfAnswer.getY()+jtfAnswer.getHeight()+20,80,23);
		jPanel.add(btnSave);
		
		this.add(jPanel);
		this.setVisible(true);
		
	}

}
