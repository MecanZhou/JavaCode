package psm.Views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JButton;

import psm.Models.BusinessModel.FrmDeployCombineMemberManage;
import psm.Models.BusinessModel.LogicalStructureManage;
import psm.Models.DataModel.CombineMember;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class FrmDeployCombineMember {

	public JFrame frame;
	private JTextArea tBName;
	private JTextArea tBOutline;
	private FrmDeployCombineMemberManage frmDeployCombineMemberManage=new FrmDeployCombineMemberManage();
	private LogicalStructureManage logicalStructureManage=new LogicalStructureManage();
	private CombineMember combineMember=new CombineMember();
	
	
	public FrmDeployCombineMember(LogicalStructureManage logicalStructureManage){
		frmDeployCombineMemberManage=new FrmDeployCombineMemberManage();
		this.logicalStructureManage=new LogicalStructureManage();
		this.combineMember=new CombineMember();
	}
	
	public FrmDeployCombineMember() {
		initialize();
	}

	
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 433, 366);
		
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(240, 248, 255));
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u7EC4\u5408\u6A21\u578B\u4FE1\u606F", TitledBorder.CENTER, TitledBorder.TOP, null, Color.BLACK));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel labName = new JLabel("\u540D\u79F0\uFF1A");
		labName.setBounds(23, 38, 49, 15);
		panel.add(labName);
		
		JLabel label_1 = new JLabel("\u6982\u8981\uFF1A");
		label_1.setBounds(23, 75, 49, 15);
		panel.add(label_1);
		
		tBName = new JTextArea();
		tBName.setLineWrap(true);
		tBName.setBounds(69, 34, 318, 21);
		tBName.setBorder(BorderFactory.createLineBorder(Color.gray,1));
		panel.add(tBName);
		
	    tBOutline = new JTextArea();
		tBOutline.setLineWrap(true);
		tBOutline.setBounds(69, 71, 318, 216);
		tBOutline.setBorder(BorderFactory.createLineBorder(Color.gray,1));
		panel.add(tBOutline);
		
		JButton btnSave = new JButton("\u4FDD\u5B58");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 int i = 0;
				try {
					i = frmDeployCombineMemberManage.DoSave_Click(combineMember,logicalStructureManage, tBName.getText(), tBOutline.getText());
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}   
				 if ( i== 0)
		            {
		            	frame.dispose();
		            }
		            else if (i == 2)
		            {
		            	JOptionPane.showMessageDialog(new JPanel(), "指定模型名称已经存在，请重新命名！","提示",JOptionPane.YES_NO_OPTION);
		            }
		            else if(i==3){
		            	JOptionPane.showMessageDialog(new JPanel(), "在保存组合模型时成员之间没有进行订购，不能进行组合，请重新组合！","提示",JOptionPane.YES_NO_OPTION);
		            	frame.dispose();
		            }
		            else if(i==4){
		            	JOptionPane.showMessageDialog(new JPanel(), "不能对整个方案进行组合，请重新组合！","提示",JOptionPane.YES_NO_OPTION);
		            	frame.dispose();
		            }
		            else if(i==5){
		            	JOptionPane.showMessageDialog(new JPanel(), "组合成员不能没有输入输出端！","提示",JOptionPane.YES_NO_OPTION);
		            	frame.dispose();
		            }
		            else if(i==6){
		            	JOptionPane.showMessageDialog(new JPanel(), "组合成员中不能包含定时器！","提示",JOptionPane.YES_NO_OPTION);
		            	frame.dispose();
		            }
		            else 
		            {
		            	JOptionPane.showMessageDialog(new JPanel(), "在保存组合模型时出现错误，请检查后重试！","提示",JOptionPane.YES_NO_OPTION);
		            	frame.dispose();
		         
		            }
			}
		});
		btnSave.setBackground(new Color(240, 248, 255));
		btnSave.setBounds(219, 297, 75, 23);
		panel.add(btnSave);
		
		JButton btnCancel = new JButton("\u53D6\u6D88");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showOptionDialog(new JPanel(), "确认不需要保存？", "提示",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,null,null)==0){
					frame.dispose();
				}
			}
		});
		btnCancel.setBackground(new Color(240, 248, 255));
		btnCancel.setBounds(312, 297, 75, 23);
		panel.add(btnCancel);
	}
}
