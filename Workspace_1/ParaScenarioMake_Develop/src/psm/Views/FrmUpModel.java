package psm.Views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.UIManager;

import psm.Models.BusinessModel.FrmUpModelManage;
import psm.Models.DataModel.Model;
import psm.Models.DataModel.ModelClass.Model_para_info;
import psm.Models.DataModel.ModelClass.Model_cat;
import psm.Models.DataModel.ModelClass.Model_desc_para;
import psm.Models.DataModel.ModelClass.Model_interaction_info;

import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.DefaultComboBoxModel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;

import org.xml.sax.SAXException;

import com.sun.xml.internal.txw2.output.XmlSerializer;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;

import psm.Models.DataModel.ModelClass.ParaType;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author Administrator
 *
 */
public class FrmUpModel extends JFrame{

	//[start]控件的全局变量
	private JTextField tBName;
	private JTextField tBVersion;
	private JTextField dtiPulish;
	private JTextField dtiModified;
	private JTextField tBSponsor;
	private JTextField tBLanguage;
	private JTextField tBTools;
	private JTextField tBDefaValue;
	private JTextField tBParaPhysicsUnit;
	private JTextField tBParaDesc;
	private JTable tablePara;
	private JTextField tBKey;
	private JTable tableToData;
	private JTextField tBInterPara;
	private JTextField tBParaValue;
	private JTable tableDesc;
	private JTextField tBParaName;
	private JTextField tBParaUnit;
	private JTable tableType;
	JSpinner nUDSend;
	JSpinner nUDAccept;
	JComboBox comboBasicType;
	JSpinner nUDParaLeng;
	JPanel modelParaInfoTabPanel;
	JTabbedPane tabbedPane;
	JTextArea tBLabel;
	JTextArea tBDesc;
	JTextArea tBParaIntro;
	JPanel modelInfoTabPanel; 
	JButton btnUp;
	JTextArea tBIntro;
	JCheckBox cBHaveScr;
	JCheckBox cBHaveDoc;
	JCheckBox cBIsVerified;
	JComboBox comboCat;
	JComboBox tBParaType;
	JButton btndtiModified ;
	JButton btnPublishData;
	JComboBox comboAccess ;
	//[end]
	
	String strload;  //xml文件路径
	String selectMuid = "select * from MODEL_INFO";
	String selectModelParaID = "select * from MODEL_PARA_INFO";
	String selectModelInterID = "select * from MODEL_INTERACTION_INFO";
	//String selectModelDescID = "select * from MODEL_DESC_PARA";
	String selectModelCat = "select * from MODEL_CAT";
	String selectModelVer="select * from MODEL_VERIFICATION";
    int recordMuid;  //记录模型ID
    int recordModelParaID; //记录模型参数
    int recordPid;   //记录参数ID
    int recordModelInterID; //记录模型交互信息ID
    int recordModelDescID;  //记录模型描述参数ID
    int recordModelCatID;   //记录模型类型ID
    int recordModelVerID;     //记录模型分类ID
    int nextClick; //下一步按钮点击

    private static Model model;
    List<Model> ModelList;
    static List<psm.Models.DataModel.ModelClass.Model_para_info> Model_para_infoList;//记录模型参数信息表
    static List<psm.Models.DataModel.ModelClass.Model_interaction_info> Model_interaction_infoList;//记录模型交互信息表
    List<psm.Models.DataModel.ModelClass.Model_cat> Model_catList;
    List<psm.Models.DataModel.ModelClass.Model_desc_para> Model_desc_paraList;

    private FrmUpModelManage frmUpModelManage;
    public static FrmUpModel window;
    private JTextField tbDllName;

	/**
	 * Launch the application.
	 */
    public void strat(){
	//public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new FrmUpModel();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws SQLException 
	 */
	public FrmUpModel() throws SQLException {
		 initialize();
		 frmUpModelManage = new FrmUpModelManage();
		 
		 tabbedPane.setSelectedIndex(0);//设置Tab页初始化开始的页数
		 //将Tab页其他页的Enable属性为False；
		 tabbedPane.setEnabledAt(1,false);
		 tabbedPane.setEnabledAt(2,false);
		 tabbedPane.setEnabledAt(3,false);

         nextClick = 0;//初始化鼠标点击“下一步”按钮的次数
         recordMuid = frmUpModelManage.findMax(selectMuid);//将数据表的最大ID寻找出来
         recordModelParaID = frmUpModelManage.findMax(selectModelParaID);
         recordModelInterID = frmUpModelManage.findMax(selectModelInterID);
         //recordModelDescID = frmUpDownModelManage.findMax(selectModelDescID);
         recordModelCatID = frmUpModelManage.findMax(selectModelCat);
         recordModelVerID=frmUpModelManage.findMax(selectModelVer);
         

         model = new Model();
         ModelList = new ArrayList<Model>();
         Model_para_infoList=model.Model_para_infoList;
         Model_interaction_infoList = model.Model_interaction_infoList;
         Model_catList = model.Model_catList;
         Model_desc_paraList = model.Model_desc_paraList;
	}

	
	
	//上传按钮
	 private void btnUp_Click(ActionEvent e) throws SQLException, IOException, ParserConfigurationException, SAXException
     {
		 strload="src\\UpTempModel\\" + tBName.getText() + ".xml";
		 
		 int result=JOptionPane.showOptionDialog(new JPanel(), "是否上传该模型？", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
			if(result==0)
			{
				 //生成XML
		         getModelInfo();
		         getModelParaInfo();
		         getModelInterInfo();
		         //getModelDescPara();
//		         if (tableType.getValueAt(0, 0)!= null)
//		         {
//		             getModelCat();
//		         }
		         serializerXML();
		         //上传数据      
		         frmUpModelManage.upModelInfo(strload);
		         frmUpModelManage.upModelParaInfo(strload);
		         frmUpModelManage.upModelInterInfo(strload);
		         frmUpModelManage.upModelVerification(strload);
		         //frmUpDownModelManage.upModelDescPara(strload);
//		         if (dataGridViewX4.Rows[0].Cells[0].Value != null)
//		         {
//		             frmUpDownModelManage.upModelCat(strload);
//		         }           
				JOptionPane.showMessageDialog(new JPanel(), "上传成功","提示",JOptionPane.YES_NO_CANCEL_OPTION);
				
				this.dispose();
				
			}
        
     }
	 
	 /**
	  *序列化XML文件
	  */
	 private void serializerXML() throws IOException
     {
		 //解决XML生成的下划线问题
		 XmlFriendlyReplacer replacer = new XmlFriendlyReplacer("__", "_");
		 HierarchicalStreamDriver hierarchicalStreamDriver = new XppDriver(replacer);
		 XStream xs = new XStream(hierarchicalStreamDriver);

		 //将对象写入文件系统中
		 try {
		 FileOutputStream fs = new FileOutputStream(strload);
		 xs.toXML(model, fs);
		 } catch (FileNotFoundException e1) {
		 e1.printStackTrace();}
     }
	 //[start]按钮点击事件方法
	//点击下一步	
	private void btnNext_Click(ActionEvent e) {
		if (tBName.getText().equals("") || tBVersion.getText().equals("")
				|| dtiPulish.getText().equals("")
				|| dtiModified.getText().equals("")) {
			JOptionPane.showMessageDialog(new JPanel(), "带“*”的必填！", "提示",
					JOptionPane.YES_NO_CANCEL_OPTION);
		} else {
			if (nextClick % 3 == 0) {
				tabbedPane.setEnabledAt(1, true);
				tabbedPane.setSelectedIndex(1);
				
			} else {
				if (tBParaPhysicsUnit.getText().equals("")) {
					JOptionPane.showMessageDialog(new JPanel(), "带“*”的必填！",
							"提示", JOptionPane.YES_NO_CANCEL_OPTION);
				} else {
					if (nextClick % 3 == 1) {
						tabbedPane.setEnabledAt(2, true);
						tabbedPane.setSelectedIndex(2);
						
					}
					if (nextClick % 3 == 2) {
						tabbedPane.setEnabledAt(3, true);
						btnUp.setEnabled(true);
						tabbedPane.setSelectedIndex(0);
						

					}
				}
			}
		}
		nextClick++;
	}
	
	//添加模型交互信息到表
     private void btnAddToData_Click(ActionEvent e)
     {
         if (tBKey.getText() != null)
         {
        	 ((DefaultTableModel) tableToData.getModel()).addRow(new Object[]{nUDSend.getValue().toString(),nUDAccept.getValue().toString(),tBKey.getText(),tBInterPara.getText(),tBDesc.getText()});
         }           
         //清除textBox里的数据
         nUDSend.setValue(0);
         nUDAccept.setValue(0);
         tBKey.setText("");
         tBInterPara.setText("");
         tBDesc.setText("");
     }

     //删除模型交互信息里面的数据
     private void btnDelete_Click(ActionEvent e)
     {
    	 int[] selected=tableToData.getSelectedRows();//获取选择的行数
         for (int i = 0; i <selected.length; i++){
        	 ((DefaultTableModel) tableToData.getModel()).removeRow(selected[i]);
         }
     }

     //添加模型参数信息
     private void btnAddPara_Click(ActionEvent e)
     {
         if (tBParaType.getSelectedItem().toString()!= null && tBParaPhysicsUnit.getText()!= null && comboBasicType.getSelectedItem().toString()!= null && nUDParaLeng.getValue().toString() != null)
         {
        	 ((DefaultTableModel) tablePara.getModel()).addRow(new Object[]{tBParaType.getSelectedItem().toString(),tBDefaValue.getText(),tBParaPhysicsUnit.getText(),
        			 comboBasicType.getSelectedItem().toString(),nUDParaLeng.getValue().toString(),tBInterPara.getText(),tBParaDesc.getText(), tBLabel.getText()});
          
         }
         //清除textBox里的数据
         tBParaType.setSelectedItem("");
         tBDefaValue.setText("");
         tBParaPhysicsUnit.setText("");
         comboBasicType.setSelectedItem("");
         nUDParaLeng.setValue(0);
         tBParaDesc.setText("");
         tBLabel.setText("");
     }

     //删除模型参数信息里面的数据
     private void btnDeletePara_Click(ActionEvent e)
     {
    	 int[] selected=tablePara.getSelectedRows();
         for (int i = 0; i <selected.length; i++){
        	 ((DefaultTableModel) tablePara.getModel()).removeRow(selected[i]);
         }
     }

     //添加模型描述参数
     private void btnAddDesc_Click(ActionEvent e)
     {
         if (tBParaName.getText()!=null)
         {
        	 ((DefaultTableModel) tableDesc.getModel()).addRow(new Object[]{tBParaName.getText(),tBParaUnit.getText(),
        			 tBParaValue.getText(),tBParaIntro.getText()});
        	 
        	 
         }
         //清除textBox里的数据
         tBParaName.setText("");
         tBParaUnit.setText("");
         tBParaValue.setText("");
         tBParaIntro.setText("");
     }

     //删除模型描述参数里面的数据
     private void btnDeleteDesc_Click(ActionEvent e)
     {
    	 int[] selected=tableDesc.getSelectedRows();
         for (int i = 0; i <selected.length; i++){
        	 ((DefaultTableModel) tableDesc.getModel()).removeRow(selected[i]);
         }
     }
//[end]
	
     //[start]模型信息获取的事件
     private void getModelInfo()
     {
         try
         {
             model.Muid = recordMuid + 1;
             model.Model_vid=recordModelVerID+1;
             model.Model_category=comboCat.getSelectedIndex()+1;
             model.Model_name = String.valueOf(tBName.getText());
             model.Model_version =String.valueOf(tBVersion.getText());
             model.Publish_time =Date.valueOf(dtiPulish.getText());
             model.Modified_time =Date.valueOf(dtiModified.getText());
             model.Development_languages = String.valueOf(tBLanguage.getText());
             model.Development_tools =String.valueOf(tBTools.getText());
             model.Hava_src = Boolean.valueOf(cBHaveScr.isSelected());
             model.Hava_doc =Boolean.valueOf(cBHaveDoc.isSelected());
             model.Is_verified = Boolean.valueOf(cBIsVerified.isSelected());
             model.Model_intro =String.valueOf(tBIntro.getText());
             model.Model_sponsor = String.valueOf(tBSponsor.getText());
             model.Target_access =String.valueOf(comboAccess.getSelectedItem());
             model.Model_target=String.valueOf(tbDllName.getText());
         }
         catch (Exception e)
         {
        	 System.out.print("模型信息上传操作失败！"+e.toString());
         }
     }
     /**
 	 * 获取模型参数信息
 	 */
     private void getModelParaInfo()
     {
         try
         {
             for (int i = 0; i < tablePara.getRowCount(); i++)
             {
                 Model_para_info modelParaInfo = new Model_para_info();

                 modelParaInfo.Mpid = recordModelParaID + (i + 1);
                 modelParaInfo.Muid = recordMuid+1;
                 modelParaInfo.Para_type = ParaType.valueOf(tablePara.getValueAt(i, 0).toString());
                 modelParaInfo.Para_defa_value = String.valueOf(tablePara.getValueAt(i, 1));
                 modelParaInfo.Para_label =String.valueOf(tablePara.getValueAt(i, 6));
                 modelParaInfo.Para_physicsunit = String.valueOf(tablePara.getValueAt(i, 2));
                 modelParaInfo.Basic_type = String.valueOf(tablePara.getValueAt(i, 3));
                 modelParaInfo.Para_length =Integer.valueOf(tablePara.getValueAt(i, 4).toString());
                 modelParaInfo.Para_desc =String.valueOf(tablePara.getValueAt(i, 5));
                 
                 Model_para_infoList.add(modelParaInfo);
             }
             
         }
         catch (Exception e)
         {
        	 System.out.print("模型参数信息上传操作失败！"+e.toString());
         }
     }
     /**
      * 获取模型交互信息
      */
     private void getModelInterInfo()
     {
         try
         {
             for (int i = 0; i < tableToData.getRowCount(); i++)
             {
                 Model_interaction_info modelInterInfo = new Model_interaction_info();
                 modelInterInfo.Inid = recordModelInterID + (i + 1);
                 modelInterInfo.Muid = recordMuid + 1;
                 modelInterInfo.Sent_interactions = String.valueOf(tableToData.getValueAt(i, 0));
                 modelInterInfo.Accepted_interactions =Integer.valueOf(tableToData.getValueAt(i, 1).toString());               
                 modelInterInfo.Interaction_key = String.valueOf(tableToData.getValueAt(i, 2));
                 modelInterInfo.Interaction_paras =String.valueOf(tableToData.getValueAt(i, 3));
                 modelInterInfo.Interaction_desc =String.valueOf(tableToData.getValueAt(i, 4));                
                 Model_interaction_infoList.add(modelInterInfo);
             }
         }
         catch (Exception e)
         {
        	 System.out.print("模型交互信息上传操作失败！"+e.toString());
         }
     }

     /**
      *  获取模型描述参数
      */
     private void getModelDescPara()
     {
         try
         {
             for (int i = 0; i < tableDesc.getRowCount() - 1; i++)
             {
                 Model_desc_para modelDescPara = new Model_desc_para();
                 modelDescPara.Dpid = recordModelDescID + (i + 1);
                 modelDescPara.Muid = recordMuid+1;
                 modelDescPara.Para_name = String.valueOf(tableDesc.getValueAt(i, 0));
                 modelDescPara.Para_value = String.valueOf(tableDesc.getValueAt(i, 2));
                 modelDescPara.Para_intro =String.valueOf(tableDesc.getValueAt(i, 3));
                 modelDescPara.Para_unit =String.valueOf(tableDesc.getValueAt(i, 1));

                 Model_desc_paraList.add(modelDescPara);
             }
         }
         catch (Exception e)
         {
        	 System.out.print("模型描述参数上传操作失败！"+e.toString());
         }
     }

     /**
      *  获取模型分类信息
      */
   
     private void getModelCat()
     {
         try
         {
             for (int i = 0; i < tableType.getRowCount(); i++)
             {
                 Model_cat modelCat = new Model_cat();
                 modelCat.Cat_id = recordModelCatID + 1;
                 modelCat.Cat_title = String.valueOf(tableType.getValueAt(i, 0));
                 modelCat.Cat_desc =String.valueOf(tableType.getValueAt(i, 1));

                 model.Model_catList.add(modelCat);
             }
         }
         catch (Exception e)
         {
        	 System.out.print("操作失败！"+e.toString());
         }
     }
     
     
     //[end]
    
     public void dtiPulishShow(String string){
    	 dtiPulish.setText(string);
    	 
     }
     
     public void dtiModifiedShow(String string){
    	 dtiModified.setText(string);
     }
     
     /**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		getContentPane().setBackground(new Color(240, 248, 255));
		setTitle("\u6A21\u578B\u4E0A\u4F20");
		setResizable(false);
		setBounds(100, 100, 756, 505);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//实现点击关闭窗口按钮的时候，只关闭子窗体，父窗体不受影响
		getContentPane().setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 742, 425);
		getContentPane().add(tabbedPane);
		
		modelInfoTabPanel = new JPanel();
		modelInfoTabPanel.setBackground(new Color(240, 248, 255));
		tabbedPane.addTab("\u6A21\u578B\u4FE1\u606F", null, modelInfoTabPanel, null);
		modelInfoTabPanel.setLayout(null);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBounds(10, 10, 717, 376);
		panel_5.setBackground(new Color(240, 248, 255));
		panel_5.setBorder(new LineBorder(SystemColor.activeCaption));
		modelInfoTabPanel.add(panel_5);
		panel_5.setLayout(null);
		
		JLabel UpDependLab = new JLabel("\u5F00\u53D1\u8BED\u8A00\uFF1A");
		UpDependLab.setBounds(408, 29, 68, 15);
		panel_5.add(UpDependLab);
		
		JLabel label_7 = new JLabel("\u662F\u5426\u6709\u6E90\u6587\u4EF6\uFF1A");
		label_7.setBounds(539, 101, 107, 15);
		panel_5.add(label_7);
		
		JLabel label_8 = new JLabel("\u662F\u5426\u8FDB\u884C\u9A8C\u8BC1\uFF1A");
		label_8.setBounds(408, 135, 96, 15);
		panel_5.add(label_8);
		
		JLabel label_9 = new JLabel("\u662F\u5426\u6709\u6587\u6863\uFF1A");
		label_9.setBounds(408, 101, 96, 15);
		panel_5.add(label_9);
		
		JLabel label_11 = new JLabel("\u76EE\u6807\u6743\u9650\uFF1A");
		label_11.setBounds(408, 216, 79, 15);
		panel_5.add(label_11);
		
		JLabel UpToolLab = new JLabel("\u5F00\u53D1\u5DE5\u5177\uFF1A");
		UpToolLab.setBounds(408, 62, 68, 15);
		panel_5.add(UpToolLab);
		
		JLabel UpNameLab = new JLabel("*\u6A21\u578B\u540D\u79F0\uFF1A");
		UpNameLab.setBounds(40, 62, 72, 15);
		panel_5.add(UpNameLab);
		
		JLabel UpVerLab = new JLabel("*\u6A21\u578B\u7248\u672C\uFF1A");
		UpVerLab.setBounds(40, 101, 72, 15);
		panel_5.add(UpVerLab);
		
		JLabel UpPubTimeLab = new JLabel("*\u53D1\u5E03\u65F6\u95F4\uFF1A");
		UpPubTimeLab.setBounds(40, 135, 72, 15);
		panel_5.add(UpPubTimeLab);
		
		JLabel UpAlterTimeLab = new JLabel("*\u4FEE\u6539\u65F6\u95F4\uFF1A");
		UpAlterTimeLab.setBounds(40, 175, 72, 15);
		panel_5.add(UpAlterTimeLab);
		
		JLabel UpPeopleLab = new JLabel("\u5F55\u5165\u4EBA\u5458\uFF1A");
		UpPeopleLab.setBounds(45, 216, 68, 15);
		panel_5.add(UpPeopleLab);
		
		JLabel UpTypeLab = new JLabel("*\u6A21\u578B\u7C7B\u578B\uFF1A");
		UpTypeLab.setBounds(40, 29, 72, 15);
		panel_5.add(UpTypeLab);
		
		JLabel UpControLab = new JLabel("\u6A21\u578B\u4ECB\u7ECD\uFF1A");
		UpControLab.setBounds(45, 263, 68, 15);
		panel_5.add(UpControLab);
		
		comboCat = new JComboBox();
		comboCat.setModel(new DefaultComboBoxModel(new String[] {"\u63A7\u5236\u5668", "\u6267\u884C\u5668", "\u88AB\u63A7\u5BF9\u8C61", "\u654F\u611F\u5668", "\u59FF\u6001\u63A7\u5236\u7CFB\u7EDF", "\u8F68\u9053\u63A7\u5236\u7CFB\u7EDF", "\u5BF9\u5730\u89C2\u6D4B\u536B\u661F", "\u4E8B\u4EF6\u53D1\u751F\u5668"}));
		comboCat.setBackground(new Color(240, 248, 255));
		comboCat.setBounds(123, 26, 158, 21);
		panel_5.add(comboCat);
		
		tBName = new JTextField();
		tBName.setBounds(123, 59, 158, 21);
		panel_5.add(tBName);
		tBName.setColumns(10);
		
		tBVersion = new JTextField();
		tBVersion.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				int keyChar=e.getKeyChar();
	        	 if (keyChar>=KeyEvent.VK_0&&keyChar<=KeyEvent.VK_9
	        			 ||keyChar==KeyEvent.VK_BACK_SPACE||keyChar==KeyEvent.VK_ENTER||keyChar==KeyEvent.VK_DECIMAL){
	        		 return;
	        	 }else{
	        		 e.consume();
	        	 }
			}
		});
		

		tBVersion.setBounds(123, 98, 158, 21);
		panel_5.add(tBVersion);
		tBVersion.setColumns(10);
		
		dtiPulish = new JTextField();
		dtiPulish.setEditable(false);
		dtiPulish.setBackground(new Color(255, 255, 255));
		dtiPulish.setBounds(123, 132, 142, 21);
		panel_5.add(dtiPulish);
		dtiPulish.setColumns(10);
		
		dtiModified = new JTextField();
		dtiModified.setBackground(new Color(255, 255, 255));
		dtiModified.setEditable(false);
		dtiModified.setBounds(123, 172, 142, 21);
		panel_5.add(dtiModified);
		dtiModified.setColumns(10);
		
		tBSponsor = new JTextField();
		tBSponsor.setBounds(123, 213, 158, 21);
		panel_5.add(tBSponsor);
		tBSponsor.setColumns(10);
		
		tBLanguage = new JTextField();
		tBLanguage.setBounds(496, 26, 158, 21);
		panel_5.add(tBLanguage);
		tBLanguage.setColumns(10);
		
		tBTools = new JTextField();
		tBTools.setBounds(496, 59, 158, 21);
		panel_5.add(tBTools);
		tBTools.setColumns(10);
		
	    cBHaveScr = new JCheckBox("\u662F");
		cBHaveScr.setBackground(new Color(240, 248, 255));
		cBHaveScr.setBounds(496, 97, 37, 23);
		panel_5.add(cBHaveScr);
		
		cBHaveDoc = new JCheckBox("\u662F");
		cBHaveDoc.setBackground(new Color(240, 248, 255));
		cBHaveDoc.setBounds(625, 97, 46, 23);
		panel_5.add(cBHaveDoc);
		
		cBIsVerified = new JCheckBox("\u662F");
		cBIsVerified.setBackground(new Color(240, 248, 255));
		cBIsVerified.setBounds(496, 131, 103, 23);
		panel_5.add(cBIsVerified);
		
		tBIntro = new JTextArea();
		tBIntro.setLineWrap(true);
		tBIntro.setBounds(123, 263, 532, 103);

		tBIntro.setBorder(BorderFactory.createLineBorder(Color.gray,1));
		panel_5.add(tBIntro);
		
		comboAccess = new JComboBox();
		comboAccess.setModel(new DefaultComboBoxModel(new String[] {"public", "private"}));
		comboAccess.setBackground(new Color(240, 248, 255));
		comboAccess.setBounds(496, 213, 158, 21);
		panel_5.add(comboAccess);
		
		btnPublishData = new JButton("...");
		btnPublishData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CalendarFrame calendarFrame=new CalendarFrame();
				calendarFrame.getfrmUpDownModel(window);
				calendarFrame.setVisible(true);
				window.setEnabled(false);
			}
		});
		btnPublishData.setBounds(264, 131, 17, 21);
		panel_5.add(btnPublishData);
		
		btndtiModified = new JButton("...");
		btndtiModified.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CalendarFrame calendarFrame=new CalendarFrame();
				calendarFrame.getfrmUpDownModel(window);
				calendarFrame.setVisible(true);
				window.setEnabled(false);
			}
		});
		btndtiModified.setBounds(264, 171, 17, 21);
		panel_5.add(btndtiModified);
		
		JLabel lbldll = new JLabel("\u6A21\u578BDLL\u540D\u79F0\uFF1A");
		lbldll.setBounds(408, 175, 96, 15);
		panel_5.add(lbldll);
		
		tbDllName = new JTextField();
		tbDllName.setBounds(496, 172, 158, 21);
		panel_5.add(tbDllName);
		tbDllName.setColumns(10);
		
	    modelParaInfoTabPanel = new JPanel();
		modelParaInfoTabPanel.setBackground(new Color(240, 248, 255));
		tabbedPane.addTab("\u6A21\u578B\u53C2\u6570\u4FE1\u606F", null, modelParaInfoTabPanel, null);
		modelParaInfoTabPanel.setLayout(null);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(new Color(240, 248, 255));
		panel_6.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u53C2\u6570\u4FE1\u606F", TitledBorder.CENTER, TitledBorder.TOP, null, Color.BLACK));
		panel_6.setBounds(26, 10, 575, 157);
		modelParaInfoTabPanel.add(panel_6);
		panel_6.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u53C2\u6570\u7C7B\u578B\uFF1A");
		lblNewLabel.setBounds(34, 31, 79, 15);
		panel_6.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u9ED8\u8BA4\u503C\uFF1A");
		lblNewLabel_1.setBounds(34, 62, 54, 15);
		panel_6.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("\u6CE8\u91CA\uFF1A");
		lblNewLabel_2.setBounds(34, 93, 54, 15);
		panel_6.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("*\u53C2\u6570\u7269\u7406\u91CF\uFF1A");
		lblNewLabel_3.setBounds(296, 31, 79, 15);
		panel_6.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("\u57FA\u672C\u7C7B\u578B\uFF1A");
		lblNewLabel_4.setBounds(301, 62, 79, 15);
		panel_6.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("\u53C2\u6570\u957F\u5EA6\uFF1A");
		lblNewLabel_5.setBounds(301, 93, 79, 15);
		panel_6.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("\u53C2\u6570\u63CF\u8FF0\uFF1A");
		lblNewLabel_6.setBounds(301, 124, 79, 15);
		panel_6.add(lblNewLabel_6);
		
		tBDefaValue = new JTextField();
		tBDefaValue.setBounds(107, 62, 158, 21);
		panel_6.add(tBDefaValue);
		tBDefaValue.setColumns(10);
		
		tBParaPhysicsUnit = new JTextField();
		tBParaPhysicsUnit.setBounds(373, 28, 158, 21);
		panel_6.add(tBParaPhysicsUnit);
		tBParaPhysicsUnit.setColumns(10);
		
		tBParaDesc = new JTextField();
		tBParaDesc.setBounds(373, 121, 158, 21);
		panel_6.add(tBParaDesc);
		tBParaDesc.setColumns(10);
		
		comboBasicType = new JComboBox();
		comboBasicType.setBackground(new Color(240, 248, 255));
		comboBasicType.setModel(new DefaultComboBoxModel(new String[] {"integer", "double"}));
		comboBasicType.setBounds(373, 59, 158, 21);
		panel_6.add(comboBasicType);
		
		nUDParaLeng = new JSpinner();
		nUDParaLeng.setBounds(373, 90, 158, 22);
		panel_6.add(nUDParaLeng);
		
		tBLabel = new JTextArea();
		tBLabel.setLineWrap(true);
		tBLabel.setBounds(107, 93, 158, 46);
		tBLabel.setBorder(BorderFactory.createLineBorder(Color.gray,1));
		panel_6.add(tBLabel);
		
		tBParaType = new JComboBox();
		tBParaType.setModel(new DefaultComboBoxModel(new String[] {"input_para", "output_para", "init_para", "model_para"}));
		tBParaType.setBackground(new Color(240, 248, 255));
		tBParaType.setBounds(107, 28, 158, 21);
		panel_6.add(tBParaType);
		
		JButton btnAddPara = new JButton("\u6DFB\u52A0");
		btnAddPara.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAddPara_Click(e);
			}
		});
		btnAddPara.setBounds(611, 45, 93, 23);
		modelParaInfoTabPanel.add(btnAddPara);
		
		JButton btnDeletePara = new JButton("\u9009\u4E2D\u5220\u9664");
		btnDeletePara.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnDeletePara_Click(e);
			}
		});
		btnDeletePara.setBounds(611, 99, 93, 23);
		modelParaInfoTabPanel.add(btnDeletePara);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().setBackground(Color.white);//这行代码是实现scrollpane的背景色
		scrollPane.setBounds(10, 177, 717, 209);
		modelParaInfoTabPanel.add(scrollPane);
		
		DefaultTableModel ParaModel = new DefaultTableModel();
		tablePara = new JTable(ParaModel){
			public boolean isCellEditable(int row, int column) {
				return false;
			}// 表格不允许被编辑
		};
		scrollPane.setViewportView(tablePara);
		tablePara.setBorder(new LineBorder(new Color(0, 0, 0)));
		ParaModel.setColumnIdentifiers(new Object[] { "参数类型", "默认值","参数关键值", "基本类型" ,"参数长度","参数描述","注释"});
		
		JPanel modelInterInfoTabPanel = new JPanel();
		modelInterInfoTabPanel.setBackground(new Color(240, 248, 255));
		tabbedPane.addTab("\u6A21\u578B\u4EA4\u4E92\u4FE1\u606F", null, modelInterInfoTabPanel, null);
		modelInterInfoTabPanel.setLayout(null);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBackground(new Color(240, 248, 255));
		panel_7.setBorder(new TitledBorder(null, "\u6A21\u578B\u4EA4\u4E92\u4FE1\u606F", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panel_7.setLayout(null);
		panel_7.setBounds(26, 10, 575, 157);
		modelInterInfoTabPanel.add(panel_7);
		
		JLabel label = new JLabel("\u53D1\u9001\u4EA4\u4E92\uFF1A");
		label.setBounds(34, 31, 79, 15);
		panel_7.add(label);
		
		JLabel label_1 = new JLabel("\u63A5\u53D7\u4EA4\u4E92\uFF1A");
		label_1.setBounds(34, 62, 79, 15);
		panel_7.add(label_1);
		
		JLabel label_2 = new JLabel("\u63CF\u8FF0\uFF1A");
		label_2.setBounds(34, 93, 54, 15);
		panel_7.add(label_2);
		
		JLabel label_3 = new JLabel("\u4EA4\u4E92\u5173\u952E\u503C\uFF1A");
		label_3.setBounds(301, 31, 79, 15);
		panel_7.add(label_3);
		
		JLabel label_4 = new JLabel("\u4EA4\u4E92\u53C2\u6570\uFF1A");
		label_4.setBounds(301, 62, 79, 15);
		panel_7.add(label_4);
		
		tBKey = new JTextField();
		tBKey.setColumns(10);
		tBKey.setBounds(373, 28, 158, 21);
		panel_7.add(tBKey);
		
		nUDSend = new JSpinner();
		nUDSend.setBounds(107, 28, 158, 22);
		panel_7.add(nUDSend);
		
		nUDAccept = new JSpinner();
		nUDAccept.setBounds(107, 59, 158, 22);
		panel_7.add(nUDAccept);
		
		tBInterPara = new JTextField();
		tBInterPara.setBounds(373, 59, 158, 21);
		panel_7.add(tBInterPara);
		tBInterPara.setColumns(10);
		
		tBDesc = new JTextArea();
		tBDesc.setLineWrap(true);
		tBDesc.setBounds(107, 93, 424, 46);
		tBDesc.setBorder(BorderFactory.createLineBorder(Color.gray,1));
		
		panel_7.add(tBDesc);
		
		JButton btnAddToData = new JButton("\u6DFB\u52A0");
		btnAddToData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAddToData_Click(e);
			}
		});
		btnAddToData.setBounds(611, 45, 93, 23);
		modelInterInfoTabPanel.add(btnAddToData);
		
		JButton btnDelete = new JButton("\u9009\u4E2D\u5220\u9664");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnDelete_Click(e);
			}
		});
		btnDelete.setBounds(611, 99, 93, 23);
		modelInterInfoTabPanel.add(btnDelete);
		//模型交互信息表的初始化
		JScrollPane scrollPaneToData = new JScrollPane();
		scrollPaneToData.getViewport().setBackground(Color.white);//这行代码是实现scrollpane的背景色
		scrollPaneToData.setBounds(10, 177, 717, 209);
		modelInterInfoTabPanel.add(scrollPaneToData);
		
		DefaultTableModel TodataModel = new DefaultTableModel();
		tableToData = new JTable(TodataModel) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}// 表格不允许被编辑
		};
		scrollPaneToData.setViewportView(tableToData);
		tableToData.setBorder(new LineBorder(new Color(0, 0, 0)));
		TodataModel.setColumnIdentifiers(new Object[] { "发送交互", "接受交互","交互关键值", "交互参数" ,"描述"});
		
		JPanel modelDescTabPanel = new JPanel();
		modelDescTabPanel.setBackground(new Color(240, 248, 255));
		tabbedPane.addTab("\u6A21\u578B\u63CF\u8FF0\u53C2\u6570", null, modelDescTabPanel, null);
		modelDescTabPanel.setLayout(null);
		
		JPanel panel_8 = new JPanel();
		panel_8.setBackground(new Color(240, 248, 255));
		panel_8.setBorder(new TitledBorder(null, "\u6A21\u578B\u63CF\u8FF0\u53C2\u6570", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panel_8.setLayout(null);
		panel_8.setBounds(26, 10, 575, 157);
		modelDescTabPanel.add(panel_8);
		
		JLabel label_5 = new JLabel("\u53C2\u6570\u540D\u79F0\uFF1A");
		label_5.setBounds(34, 31, 79, 15);
		panel_8.add(label_5);
		
		JLabel label_6 = new JLabel("\u53C2\u6570\u5355\u5143\uFF1A");
		label_6.setBounds(34, 62, 79, 15);
		panel_8.add(label_6);
		
		JLabel label_12 = new JLabel("\u53C2\u6570\u503C\uFF1A");
		label_12.setBounds(34, 93, 54, 15);
		panel_8.add(label_12);
		
		JLabel label_13 = new JLabel("\u53C2\u6570\u4ECB\u7ECD\uFF1A");
		label_13.setBounds(301, 31, 79, 15);
		panel_8.add(label_13);
		
		tBParaValue = new JTextField();
		tBParaValue.setColumns(10);
		tBParaValue.setBounds(107, 93, 158, 21);
		panel_8.add(tBParaValue);
		
		tBParaName = new JTextField();
		tBParaName.setBounds(107, 28, 158, 21);
		panel_8.add(tBParaName);
		tBParaName.setColumns(10);
		
		tBParaUnit = new JTextField();
		tBParaUnit.setBounds(107, 59, 158, 21);
		panel_8.add(tBParaUnit);
		tBParaUnit.setColumns(10);
		
		tBParaIntro = new JTextArea();
		tBParaIntro.setLineWrap(true);
		tBParaIntro.setBounds(301, 59, 236, 49);
		tBParaIntro.setBorder(BorderFactory.createLineBorder(Color.gray,1));
		panel_8.add(tBParaIntro);
		
		JButton btnAddDesc = new JButton("\u6DFB\u52A0");
		btnAddDesc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAddDesc_Click(e);
			}
		});
		btnAddDesc.setBounds(611, 45, 93, 23);
		modelDescTabPanel.add(btnAddDesc);
		
		JButton btnDeleteDesc = new JButton("\u9009\u4E2D\u5220\u9664");
		btnDeleteDesc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnDeleteDesc_Click(e);
			}
		});
		btnDeleteDesc.setBounds(611, 99, 93, 23);
		modelDescTabPanel.add(btnDeleteDesc);
		
		JScrollPane scrollPaneDesc = new JScrollPane();
		scrollPaneDesc.getViewport().setBackground(Color.white);//这行代码是实现scrollpane的背景色
		scrollPaneDesc.setBounds(10, 177, 717, 209);
		modelDescTabPanel.add(scrollPaneDesc);

		//模型描述参数表的信息建立
		
		DefaultTableModel descModel = new DefaultTableModel();
		tableDesc = new JTable(descModel) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}// 表格不允许被编辑
		};
		scrollPaneDesc.setViewportView(tableDesc);
		tableDesc.setBorder(new LineBorder(new Color(0, 0, 0)));
		descModel.setColumnIdentifiers(new Object[] { "参数名称", "参数单元", "参数值", "参数介绍" });
		
		JPanel modelTypeTabPanel = new JPanel();
		modelTypeTabPanel.setBackground(new Color(240, 248, 255));
		tabbedPane.addTab("\u6A21\u578B\u7C7B\u578B", null, modelTypeTabPanel, null);
		modelTypeTabPanel.setLayout(null);
		
		JPanel panel_9 = new JPanel();
		panel_9.setBackground(new Color(240, 248, 255));
		panel_9.setBorder(new TitledBorder(null, "\u6A21\u578B\u7C7B\u578B\u6570\u636E", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panel_9.setBounds(89, 29, 529, 297);
		modelTypeTabPanel.add(panel_9);
		panel_9.setLayout(null);
		
		DefaultTableModel typeModel = new DefaultTableModel();
		typeModel.setColumnIdentifiers(new Object[] { "模型名称", "模型描述"});
		
		JScrollPane scrollPaneType = new JScrollPane();
		scrollPaneType.getViewport().setBackground(Color.white);
		scrollPaneType.setBounds(10, 24, 509, 263);
		panel_9.add(scrollPaneType);
		tableType = new JTable(typeModel){
			public boolean isCellEditable(int row, int column) {
				return false;
			}// 表格不允许被编辑
		};
		scrollPaneType.setViewportView(tableType);
		
		JButton btnNext = new JButton("\u4E0B\u4E00\u6B65");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNext_Click(e);
			}
		});
		btnNext.setBounds(157, 435, 93, 23);
		getContentPane().add(btnNext);
		
		btnUp = new JButton("\u4E0A\u4F20");
		btnUp.setEnabled(false);
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					try {
						btnUp_Click(e);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ParserConfigurationException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					} catch (SAXException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnUp.setBounds(330, 435, 93, 23);
		getContentPane().add(btnUp);
		
		JButton btnCancel = new JButton("\u53D6\u6D88");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnCancel.setBounds(520, 435, 93, 23);
		getContentPane().add(btnCancel);
	}
}
