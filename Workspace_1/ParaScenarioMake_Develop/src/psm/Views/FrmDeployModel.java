package psm.Views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.UIManager;

import psm.Models.BusinessModel.FrmDeployModelManage;
import psm.Models.DataModel.Member;
import psm.Models.DataModel.ModelClass.Model_para_info;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import psm.Models.DataModel.ModelClass.ParaType;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;

//import java.awt.Window.Type;

public class FrmDeployModel{

    public JFrame frame;
	private JTable dGridViewModelPara;
	private JTextField tBModelName;
	private JTextField tBModelId;
	public static JTextField dateTimeInputReleaseTime;
	public static String str=null;
	public static FrmDeployModel window;
	FrmDeployModelManage frmDeployModelManage;
	private JTextField tBModelName_FrmDeploy;
	private JTextField tBStep;
	JCheckBox cBIsTmeConstrained;
	JCheckBox cBIsTimeRegulating;
	JCheckBox cBIsDeploied;
	DefaultTableModel ModelParaModel;
	FrmDeployModel frmDeployModel;
	JCheckBox cBmemberID;
	JCheckBox cBmemberStep;
	private JTextField tMemberName;
	private JSpinner spMemberTimer;

	/**
	 * Create the application.
	 */
	public FrmDeployModel() {
		initialize();
		frmDeployModelManage = new FrmDeployModelManage();
		FrmDeployModel_Load();
	}
	/**
	 * 加载信息
	 */
	private void FrmDeployModel_Load()
    {
        //获取当前选中的member
        Member member=frmDeployModelManage.DoGetSelectMember();
        if (member != null)
        {  	        	
            //显示成员基本信息
            tBStep.setText(member.Step);
            tBModelName_FrmDeploy.setText(member.Name);
        
            cBIsTimeRegulating.setSelected(member.IsTimeRegulating);
            cBIsTmeConstrained.setSelected( member.IsTimeConstrained);

            tMemberName.setText(member.Model.Model_target);
            if(member.memberTimer==null){
            spMemberTimer.setValue(0);
            }else{
            	spMemberTimer.setValue(Integer.valueOf(member.memberTimer));
            }
            
            //显示是否已经配置
            cBIsDeploied.setSelected(member.IsDeploied);
            //显示模型信息
            tBModelName.setText(member.Model.Model_name);
            tBModelId.setText(String.valueOf(member.Model.Muid));
            dateTimeInputReleaseTime.setText(String.valueOf( member.Model.Publish_time));
            //显示模型参数信息
            if (member.Model.Model_para_infoList.size() > 0)
            {
            	ModelParaModel.setRowCount(member.Model.Model_para_infoList.size()+1);
                int i = 0;
                for(Model_para_info model_para_info : member.Model.Model_para_infoList)
                {
                	dGridViewModelPara.setValueAt(model_para_info.Para_type, i,0);
                	dGridViewModelPara.setValueAt(model_para_info.Para_defa_value, i,1);
                	dGridViewModelPara.setValueAt(model_para_info.Para_physicsunit, i,2);
                	dGridViewModelPara.setValueAt(model_para_info.Basic_type, i,3);                    
                	dGridViewModelPara.setValueAt(model_para_info.Para_length, i,4);
                	dGridViewModelPara.setValueAt(model_para_info.Para_label, i,5);
                    i++;
                }               
            }
        }
        else{
        	System.out.println("没有成员");
        }
         
        
    }
	/**
	 * 当关闭窗体的时候，将各类信息保存
	 */
	private void FrmDeployModel_FrmClosing(){
		//获取当前选中的member
        Member member = frmDeployModelManage.DoGetSelectMember();
       
        if (member != null)
        {
            //保存成员基本信息
        	member.memberTimer=spMemberTimer.getValue().toString();
        	System.out.println(member.memberTimer);
            member.Step = tBStep.getText();
            member.Name = tBModelName_FrmDeploy.getText();
            //member.pictureOfMember.Image = picBoxMember.Image;//获取图片的来源
            if(cBIsTimeRegulating.isSelected()){
            	 member.IsTimeRegulating = true;
            }else{
            	 member.IsTimeRegulating = false;
            }
            if(cBIsTmeConstrained.isSelected()){
            member.IsTimeConstrained = true;
            }else{
            	member.IsTimeConstrained = false;
            }
            if(cBIsDeploied.isSelected()){
                member.IsDeploied = true;
                }else{
                	member.IsDeploied = false;
                }
            //保存模型信息
            member.Model.Model_name = tBModelName.getText();
            member.Model.Muid = Integer.valueOf(tBModelId.getText());
            member.Model.Publish_time =java.sql.Date.valueOf( dateTimeInputReleaseTime.getText());
            member.Model.Model_target=tMemberName.getText();
            //保存模型参数信息
            if (member.Model.Model_para_infoList.size()>0)
            {
                int i = 0;
                for(Model_para_info model_para_info : member.Model.Model_para_infoList)
                {
                    model_para_info.Para_type = ParaType.valueOf(dGridViewModelPara.getValueAt(i, 0).toString());
                    model_para_info.Para_defa_value = String.valueOf(dGridViewModelPara.getValueAt(i, 1));
                    model_para_info.Para_physicsunit = String.valueOf(dGridViewModelPara.getValueAt(i, 2));
                    model_para_info.Basic_type = String.valueOf(dGridViewModelPara.getValueAt(i, 3));
                    model_para_info.Para_length = Integer.valueOf(dGridViewModelPara.getValueAt(i, 4).toString());
                    model_para_info.Para_label = String.valueOf(dGridViewModelPara.getValueAt(i,5));
                    i++;
                }
            }
        }
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private  void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				FrmDeployModel_FrmClosing();
			}
		});
		frame.setTitle("FrmDeployModel");
		frame.setBounds(100, 100, 609, 420);
		frame.validate();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//实现点击关闭窗口按钮的时候，只关闭子窗体，父窗体不受影响
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(new Color(240, 255, 255));
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(240, 248, 255));
		tabbedPane.addTab("\u6A21\u578B\u4FE1\u606F", null, panel_1, null);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(240, 248, 255));
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u6A21\u578B\u4FE1\u606F", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u6A21\u578B\u540D\u79F0\uFF1A");
		lblNewLabel.setBounds(25, 35, 68, 15);
		panel_2.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u6A21\u578BID\uFF1A");
		lblNewLabel_1.setBounds(24, 72, 54, 15);
		panel_2.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("\u6A21\u578B\u53D1\u5E03\u65F6\u95F4\uFF1A");
		lblNewLabel_2.setBounds(286, 35, 103, 15);
		panel_2.add(lblNewLabel_2);
		
		cBIsDeploied = new JCheckBox("\u662F\u5426\u5DF2\u914D\u7F6E");
		cBIsDeploied.setBackground(new Color(240, 248, 255));
		cBIsDeploied.setBounds(286, 68, 131, 23);
		panel_2.add(cBIsDeploied);
		
		tBModelName = new JTextField();
		tBModelName.setBackground(Color.WHITE);
		tBModelName.setBounds(104, 32, 159, 21);
		panel_2.add(tBModelName);
		tBModelName.setColumns(10);
		
		tBModelId = new JTextField();
		tBModelId.setBackground(Color.WHITE);
		tBModelId.setBounds(104, 69, 159, 21);
		panel_2.add(tBModelId);
		tBModelId.setColumns(10);
		
		JButton btnNewButton = new JButton("..");
		btnNewButton.setEnabled(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CalendarFrame calendarFrame=new CalendarFrame();
				calendarFrame.getfrmDeployModel(window);
				calendarFrame.setVisible(true);
			}
		});
		btnNewButton.setBounds(506, 31, 17, 23);
		btnNewButton.setVisible(true);
		panel_2.add(btnNewButton);
		
		dateTimeInputReleaseTime = new JTextField();
		dateTimeInputReleaseTime.setBackground(Color.WHITE);
		dateTimeInputReleaseTime.setBounds(369, 31, 137, 23);
		
		panel_2.add(dateTimeInputReleaseTime);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().setBackground(Color.white);//这行代码是实现scrollpane的背景色
		scrollPane.setBounds(10, 101, 568, 250);
		panel_2.add(scrollPane);
		
		ModelParaModel = new DefaultTableModel();
		dGridViewModelPara = new JTable(ModelParaModel);
		scrollPane.setViewportView(dGridViewModelPara);
		dGridViewModelPara.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		dGridViewModelPara.setBackground(Color.WHITE);
		ModelParaModel.setColumnIdentifiers(new Object[] { "参数类型", "默认值","参数关键值", "基本类型" ,"参数长度","参数描述"});
		
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(240, 248, 255));
		tabbedPane.addTab("\u6210\u5458\u57FA\u672C\u4FE1\u606F", null, panel, null);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u6210\u5458\u57FA\u672C\u4FE1\u606F", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_3.setBackground(new Color(240, 248, 255));
		panel.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("\u6210\u5458\u540D\u79F0\uFF1A");
		lblNewLabel_3.setBounds(31, 68, 76, 15);
		panel_3.add(lblNewLabel_3);
		
		JLabel label = new JLabel("\u6210\u5458\u6B65\u957F\uFF1A");
		label.setBounds(31, 110, 76, 15);
		panel_3.add(label);
		
		tBModelName_FrmDeploy = new JTextField();
		tBModelName_FrmDeploy.setBounds(114, 65, 258, 23);
		panel_3.add(tBModelName_FrmDeploy);
		tBModelName_FrmDeploy.setColumns(10);
		
		tBStep = new JTextField();
		tBStep.setBounds(114, 106, 258, 23);
		panel_3.add(tBStep);
		tBStep.setColumns(10);
		
		cBIsTimeRegulating = new JCheckBox("\u662F\u5426\u65F6\u95F4\u8C03\u8282");
		cBIsTimeRegulating.setBounds(114, 226, 103, 23);
		cBIsTimeRegulating.setBackground(new Color(240, 248, 255));
		panel_3.add(cBIsTimeRegulating);
		
		cBIsTmeConstrained = new JCheckBox("\u662F\u5426\u65F6\u95F4\u53D7\u9650");
		cBIsTmeConstrained.setBounds(269, 226, 103, 23);
		cBIsTmeConstrained.setBackground(new Color(240, 248, 255));
		panel_3.add(cBIsTmeConstrained);
		
		JButton btnScane = new JButton("\u6D4F\u89C8");
		btnScane.setBounds(464, 273, 93, 29);
		panel_3.add(btnScane);
		
		JLabel label_1 = new JLabel("\u6210\u5458\u56FE\u7247\uFF1A");
		label_1.setBounds(397, 68, 66, 15);
		panel_3.add(label_1);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(397, 93, 160, 156);
		panel_3.add(panel_4);
		
		JLabel label_2 = new JLabel("\u6210\u5458DLL\uFF1A");
		label_2.setBounds(31, 152, 73, 15);
		panel_3.add(label_2);
		
		tMemberName = new JTextField();
		tMemberName.setColumns(10);
		tMemberName.setBounds(114, 149, 258, 21);
		panel_3.add(tMemberName);
		
		JLabel lbltimer = new JLabel("\u6210\u5458Timer\uFF1A");
		lbltimer.setBounds(31, 189, 76, 15);
		panel_3.add(lbltimer);
		
		spMemberTimer = new JSpinner();
		spMemberTimer.setBounds(114, 186, 258, 22);
		panel_3.add(spMemberTimer);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(new Color(240, 248, 255));
		tabbedPane.addTab("\u6210\u5458\u5C5E\u6027\u8BBE\u7F6E", null, panel_5, null);
		panel_5.setLayout(null);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(new Color(240, 248, 255));
		panel_6.setBorder(new TitledBorder(null, "\u6210\u5458\u5C5E\u6027\u9009\u62E9", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panel_6.setBounds(53, 30, 488, 302);
		panel_5.add(panel_6);
		panel_6.setLayout(null);
		
		cBmemberID = new JCheckBox("\u6210\u5458ID");
		cBmemberID.setBackground(new Color(240, 248, 255));
		cBmemberID.setBounds(36, 40, 103, 23);
		panel_6.add(cBmemberID);
		
		cBmemberStep = new JCheckBox("\u6210\u5458\u6B65\u957F");
		cBmemberStep.setBackground(new Color(240, 248, 255));
		cBmemberStep.setBounds(36, 91, 103, 23);
		panel_6.add(cBmemberStep);
		
		
	}

	/**
	 * 传递时间信息到text属性中
	 * @param string
	 */
    public void modelPubTextshow(String string){
		dateTimeInputReleaseTime.setText(string);
	}
}
