package psm.Views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.xml.transform.TransformerConfigurationException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;

import psm.Models.BusinessModel.FrmDownModelManage;
import psm.Models.DB.DBHelper;
import psm.Models.DataModel.Model;
import psm.Models.DataModel.ModelClass.Model_interaction_info;
import psm.Models.DataModel.ModelClass.Model_para_info;
import psm.Models.DataModel.ModelClass.ParaType;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FrmDownModel {

	public JFrame frame;
	private JTable tableModelInfo;
	private JTable tableModelPara;
	private JTable tableModelInteraction;
	FrmDownModelManage frmDownModelManage;
	JComboBox cBModelName ;
	List<Model> modelList;
	JScrollPane scrollPanePara;	
	private static Model model;
	String strload;  //xml文件路径
	String name =null;
	DefaultTableModel infoModel;
	DefaultTableModel ParaModel;

	/**
	 * Create the application.
	 * @throws SQLException 
	 */
	public FrmDownModel() throws SQLException {
		initialize();		
		frmDownModelManage = new FrmDownModelManage();
		model = new Model();
		modelList=frmDownModelManage.getModelName();
        getcBName();
	}

	/**
	 * 获取模型信息中的MUID和NAME属性显示combobox中
	 * @throws SQLException
	 */
	public void getcBName() throws SQLException{
		for(int i=0;i<modelList.size();i++){
			cBModelName.addItem(modelList.get(i).Muid+modelList.get(i).Model_name);
		}
	}
	/**
	 * 获取模型信息显示在表格中
	 */
	public void getModelInfo(){
		tableModelInfo.removeAll();
		for(int j=0;j<tableModelInfo.getRowCount();j++){
			infoModel.removeRow(j);
		}
		name =cBModelName.getSelectedItem().toString();
		for(int i=0;i<modelList.size();i++){
			if((modelList.get(i).Muid+modelList.get(i).Model_name).equals(name)){
				String sql = "select * from model_info where muid = '"+modelList.get(i).Muid+"'";
				DBHelper db = new DBHelper("test");
				try {
					ResultSet rs = db.ExecuteQuery(sql);
					if (rs != null) {
						while (rs.next()) // 遍历model_info的所有行
						{
							infoModel.addRow(new Object[]{rs.getInt("MUID"), rs.getString("Model_version"),rs.getString("MODEL_NAME"),
									rs.getDate("Publish_time"),rs.getDate("Modified_time"),rs.getString("Sponsor")
									,rs.getString("Model_intro"),rs.getString("Development_languages")
									,rs.getString("Development_tools"),rs.getBoolean("Hava_doc"),rs.getBoolean("Hava_src")
									, rs.getBoolean("Is_verified"),  rs.getString("Target_access")});
							model=new Model();
							model=modelList.get(i);
							
						}
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		
	}
	/**
	 * 获取模型参数信息显示在表格中
	 */
	public void getModelParaInfo(){
		DefaultTableModel model=new DefaultTableModel();
		model=(DefaultTableModel)tableModelPara.getModel();
		model.setRowCount(0);
		for (int i = 0; i < modelList.size(); i++) {
			if ((modelList.get(i).Muid + modelList.get(i).Model_name)
					.equals(name)) {
				String sql = "select * from model_para_info where muid = '"
						+ modelList.get(i).Muid + "'";
				DBHelper db = new DBHelper("test");
				try {
					ResultSet rs = db.ExecuteQuery(sql);
					if (rs != null) {
						while (rs.next()) // 遍历model_info的所有行
						{		
							
							model.addRow(new Object[] {
											rs.getString("PARA_TYPE"),
											rs.getString("PARA_DEFA_VALUE"),
											rs.getString("PARA_KEY"),
											rs.getBoolean("BASIC_TYPE"),
											rs.getBoolean("PARA_LENGTH"),
											rs.getBoolean("PARA_DESC"),
											rs.getString("PARA_LABEL")});
						}
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	/**
	 * 获取模型信息转为XML
	 */
	public void modelInfoToXML(){	
		for(int i=0;i<modelList.size();i++){
			if((modelList.get(i).Muid+modelList.get(i).Model_name).equals(name)){
				String sql = "select * from model_para_info where muid = '"
						+ modelList.get(i).Muid + "'";
				DBHelper db = new DBHelper("test");
				try {
					ResultSet rs = db.ExecuteQuery(sql);
					if (rs != null) {
						while (rs.next()) // 遍历model_info的所有行
						{
							model=modelList.get(i);
						}
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}}
	}
	/**
	 * 获取模型参数信息转为XML
	 * @throws SQLException
	 */
	public void modelParaInfoToXML() throws SQLException{		
		for (int i = 0; i < modelList.size(); i++) {
			if ((modelList.get(i).Muid + modelList.get(i).Model_name)
					.equals(name)) {				
						String sql = "select * from model_para_info where muid = '"
								+ modelList.get(i).Muid + "'";
						DBHelper db = new DBHelper("test");
						try {
							ResultSet rs = db.ExecuteQuery(sql);
							if (rs != null) {
								while (rs.next()) // 遍历model_info的所有行
								{
									Model_para_info modelParaInfo = new Model_para_info();

									modelParaInfo.Mpid=rs.getInt("MPID");
					                 modelParaInfo.Muid=rs.getInt("MUID");
					 				 modelParaInfo.Para_type = ParaType.valueOf(rs.getString("Para_type"));
					                 modelParaInfo.Para_defa_value = rs.getString("Para_defa_value");
					                 modelParaInfo.Para_label =rs.getString("PARA_LABEL");
					                 modelParaInfo.Para_physicsunit = rs.getString("Para_physicsunit");
					                 modelParaInfo.Basic_type = rs.getString("Basic_type");
					                 modelParaInfo.Para_length =rs.getInt("Para_length");
					                 modelParaInfo.Para_desc =rs.getString("Para_desc");
					                 
					                 model.Model_para_infoList.add(modelParaInfo);
								}

							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
		}
	}
	 /**
	  *序列化XML文件
	  */
	
	public void modelInterInfoToXml(){
		for (int i = 0; i < modelList.size(); i++) {
			if ((modelList.get(i).Muid + modelList.get(i).Model_name)
					.equals(name)) {				
						String sql = "select * from model_interaction_info where muid = '"
								+ modelList.get(i).Muid + "'";
						DBHelper db = new DBHelper("test");
						try {
							ResultSet rs = db.ExecuteQuery(sql);
							if (rs != null) {
								while (rs.next()) // 遍历model_info的所有行
								{
									Model_interaction_info modelInterInfo = new Model_interaction_info();
									
					                 modelInterInfo.Inid = rs.getInt("INTERACTION_ID");;
					                 modelInterInfo.Muid = rs.getInt("muid");
					                 modelInterInfo.Sent_interactions =rs.getString("SENT_INTERACTIONS");
					                 modelInterInfo.Accepted_interactions =rs.getInt("ACCEPTED_INTERACTIONS");               
					                 modelInterInfo.Interaction_key = rs.getString("ACCEPTED_INTERACTIONS");
					                 modelInterInfo.Interaction_paras =rs.getString("INTERACTION_PARAS");
					                 modelInterInfo.Interaction_desc =rs.getString("INTERACTION_DESC");
					                 
					                 model.Model_interaction_infoList.add(modelInterInfo);
								}

							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			}}
	}
	 private void serializerXML(String strload) throws IOException
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
	
	 private void btnDown_Click(ActionEvent e) throws SQLException, IOException
     {
		 strload="src\\UpTempModel\\" +name+ ".xml";
         //生成XML
		modelInfoToXML();
		modelParaInfoToXML();
		modelInterInfoToXml();
    
      //file表示指定路径下的文件
		if(infoModel.getRowCount()<1){
			JOptionPane.showMessageDialog(new JPanel(), "请选择下载的模型！","提示",JOptionPane.YES_NO_CANCEL_OPTION);
		}else{
      		File file=new File(strload);
      		if(file.exists())
      		{
      			int result=JOptionPane.showOptionDialog(new JPanel(), "存在同名文件，确认覆盖？", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
      			if(result==0)
      			{
      				file.delete();
      				serializerXML(strload);
      				JOptionPane.showMessageDialog(new JPanel(), "下载成功！","提示",JOptionPane.YES_NO_CANCEL_OPTION);
      			}
      		}
      		else
      		{
      			serializerXML(strload);
      			JOptionPane.showMessageDialog(new JPanel(), "下载成功","提示",JOptionPane.YES_NO_CANCEL_OPTION);
      		}	
		}
        //frame.dispose();
     }
	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 */
	private void initialize() throws SQLException {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(240, 248, 255));
		frame.setBounds(100, 100, 636, 563);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//实现点击关闭窗口按钮的时候，只关闭子窗体，父窗体不受影响
		frame.getContentPane().setLayout(null);
		
		JPanel panelSearch = new JPanel();
		panelSearch.setBackground(new Color(240, 248, 255));
		panelSearch.setBorder(new TitledBorder(null, "\u6A21\u578B\u67E5\u8BE2", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelSearch.setBounds(31, 10, 558, 54);
		frame.getContentPane().add(panelSearch);
		panelSearch.setLayout(null);
		
		cBModelName = new JComboBox();
		//cBModelName.setModel(new DefaultComboBoxModel(new String[] {"77"}));
		cBModelName.setBackground(new Color(240, 248, 255));
		cBModelName.setBounds(163, 19, 216, 27);
		panelSearch.add(cBModelName);
		
		JButton btnSearch = new JButton("\u67E5\u627E");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getModelInfo();
				getModelParaInfo();
			}
		});
		btnSearch.setBounds(435, 19, 93, 23);
		panelSearch.add(btnSearch);
		
		JPanel panelModel = new JPanel();
		panelModel.setBackground(new Color(240, 248, 255));
		panelModel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelModel.setBounds(31, 75, 558, 422);
		frame.getContentPane().add(panelModel);
		panelModel.setLayout(null);
		
		JPanel panelInfo = new JPanel();
		panelInfo.setBackground(new Color(240, 248, 255));
		panelInfo.setBounds(10, 10, 529, 130);
		panelModel.add(panelInfo);
		panelInfo.setBorder(new TitledBorder(null, "\u6A21\u578B\u4FE1\u606F", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelInfo.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPaneInfo = new JScrollPane();
		scrollPaneInfo.getViewport().setBackground(Color.white);//这行代码是实现scrollpane的背景色
		panelInfo.add(scrollPaneInfo, BorderLayout.CENTER);
		
		infoModel=new DefaultTableModel();
		tableModelInfo = new JTable(infoModel){
			public boolean isCellEditable(int row, int column) {
				return false;
			}// 表格不允许被编辑
		};
		tableModelInfo.setAutoResizeMode(tableModelInfo.AUTO_RESIZE_OFF);
		scrollPaneInfo.setViewportView(tableModelInfo);
		tableModelInfo.setBorder(new LineBorder(new Color(0, 0, 0)));
		infoModel.setColumnIdentifiers(new Object[] { "模型ID", "模型版本","模型名称", "发布时间" ,"修改时间","录入人员","模型介绍","开发语言","开发工具","是否有文档","是否有源文件","是否进行验证","目标权限"});
		
		
		JPanel panelPara = new JPanel();
		panelPara.setBackground(new Color(240, 248, 255));
		panelPara.setBounds(10, 145, 529, 130);
		panelModel.add(panelPara);
		panelPara.setBorder(new TitledBorder(null, "\u6A21\u578B\u53C2\u6570\u4FE1\u606F", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelPara.setLayout(new BorderLayout(0, 0));
		
		scrollPanePara = new JScrollPane();
		scrollPanePara.getViewport().setBackground(Color.white);//这行代码是实现scrollpane的背景色
		panelPara.add(scrollPanePara, BorderLayout.CENTER);
		
		ParaModel = new DefaultTableModel();
		tableModelPara = new JTable(ParaModel){
			public boolean isCellEditable(int row, int column) {
				return false;
			}// 表格不允许被编辑
		};
		tableModelPara.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				tableModelPara.removeAll();
				tableModelPara.repaint();
			}
		});
		
		scrollPanePara.setViewportView(tableModelPara);
		tableModelPara.setBorder(new LineBorder(new Color(0, 0, 0)));
		ParaModel.setColumnIdentifiers(new Object[] { "参数类型", "默认值","参数关键值", "基本类型" ,"参数长度","参数描述","注释"});
		
		
		
		scrollPanePara.setViewportView(tableModelPara);
		
		JPanel panelInteraction = new JPanel();
		panelInteraction.setBackground(new Color(240, 248, 255));
		panelInteraction.setBounds(10, 280, 529, 130);
		panelModel.add(panelInteraction);
		panelInteraction.setBorder(new TitledBorder(null, "\u6A21\u578B\u4EA4\u4E92\u4FE1\u606F", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelInteraction.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPaneInteraction = new JScrollPane();
		scrollPaneInteraction.getViewport().setBackground(Color.white);//这行代码是实现scrollpane的背景色
		panelInteraction.add(scrollPaneInteraction, BorderLayout.CENTER);
		
		DefaultTableModel InteractionModel = new DefaultTableModel();
		tableModelInteraction = new JTable(InteractionModel) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}// 表格不允许被编辑
		};
		scrollPaneInteraction.setViewportView(tableModelInteraction);
		tableModelInteraction.setBorder(new LineBorder(new Color(0, 0, 0)));
		InteractionModel.setColumnIdentifiers(new Object[] { "发送交互", "接受交互","交互关键值", "交互参数" ,"描述"});
		
		
		JButton btnNewButton_1 = new JButton("\u4E0B\u8F7D");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					btnDown_Click(e);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(487, 500, 93, 23);
		frame.getContentPane().add(btnNewButton_1);
	}
}
