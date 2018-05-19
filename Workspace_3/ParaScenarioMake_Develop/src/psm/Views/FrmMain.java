package psm.Views;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Graphics;


import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;

import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.ImageIcon;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JLabel;


import javax.swing.Timer;

import java.awt.Color;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JTextField;

import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import psm.Controls.FrmMainManage;
import psm.Controls.InterfaceFrmMainManage;
import psm.Controls.PanelFormulaBasicInfoManage;
import psm.Controls.TrModelManage;
import psm.Models.BusinessModel.CombineMemberManage;
import psm.Models.BusinessModel.DragAndDropDragGestureListener;
import psm.Models.BusinessModel.DragAndDropDropTargetListener;
import psm.Models.BusinessModel.FrmDeployModelManage;
import psm.Models.BusinessModel.MemberManage;
import psm.Models.BusinessModel.ModelManage;
import psm.Models.BusinessModel.Newpanel;
import psm.Models.BusinessModel.PropertyManage;

import psm.Models.DataModel.CombineMember;

import psm.Models.BusinessModel.LogicalStructureManage;

import psm.Models.DataModel.Formula;
import psm.Models.DataModel.Line;
import psm.Models.DataModel.LineManage;
import psm.Models.DataModel.Member;
import psm.Models.DataModel.Model;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.JTextArea;

import org.jvnet.substance.skin.SubstanceBusinessBlackSteelLookAndFeel;

import java.awt.Font;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class FrmMain  extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3823451119175426549L;
	
	public JButton btnSaveFormula;
	public JSplitPane splitPane = new JSplitPane();
	public JSplitPane splitPane_2 = new JSplitPane();
	private JLabel timeLab = new JLabel("\u5C31\u7EEA");
	public static JTable propertyTable;
	private JTextField JTFFormulaName;
	private JTextField JTFFormulaAuthor;
	private JTextField JTFFormulaGoal;
	private JTextArea JTFFormulaOutline;
	private JTextArea JTFFormulaScale;
	private JPanel panelFormulaBasicInfo = new JPanel();
	private JPanel panelFormula = new JPanel();
	public JTree treeModel = new JTree();
	public JTree treeFormula = new JTree();
	public static JPanel panelLogicalStructure = new Newpanel();
	private Formula formula;
	PropertyManage propretyChange=new PropertyManage();
	private TrModelManage trModelManage;
	Color color;
	
    DefaultMutableTreeNode dragTreeNode = null;
    public DefaultMutableTreeNode memberNode;
    public DefaultMutableTreeNode combineMemberNode; 
    DefaultMutableTreeNode root;
    
    InterfaceFrmMainManage interfacefrmMainManage;
    PanelFormulaBasicInfoManage panelFormulaBasicInfoManage;
    FrmMainManage frmMainManage;
	LineManage lineManage;
	@SuppressWarnings("rawtypes")
	JComboBox cBUpDown;
   
    
    //add
    private List<Model> modelList=new ArrayList<Model>();
    @SuppressWarnings("unused")
	private ArrayList<CombineMember> combineMemberList;
	/**
     * 获取当前时间，在主窗体的lable上显示出来
     */
	public void getTime(){
		 final SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss E");

		 Timer timer = new Timer(1000, new ActionListener(){
		     @Override public void actionPerformed(ActionEvent e){
		    	 timeLab.setText(formatter.format(new Date()));
		     }
		 });
		 timer.setRepeats(true);
		 timer.start();
	 }
	/**
	 * 实现在逻辑界面上点击右键弹出快捷菜单
	 */

	 public void panelLogicalStructure_MouseRelease()
     {
		 panelLogicalStructure.addContainerListener(new ContainerAdapter() {
		 	@Override
		 	public void componentAdded(ContainerEvent e) {
		 		JTreeFormula_UpData();//当在逻辑窗口添加成员的时候，更新方案树
		 		JTreeModel_UpData();
		 	}
		 	@Override
		 	public void componentRemoved(ContainerEvent e) {
		 		JTreeFormula_RemoveData();//当在逻辑窗口删除成员的时候，更新方案树
		 		JTreeModel_UpData();
		 		treeFormula.updateUI();
		 		treeModel.updateUI();
		 	}
		 });
            
     }
	 
	//public void start(){
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JFrame.setDefaultLookAndFeelDecorated(true);
		        JDialog.setDefaultLookAndFeelDecorated(true);
				try {
					FrmMain window = new FrmMain();
		            UIManager.setLookAndFeel(new SubstanceBusinessBlackSteelLookAndFeel());
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}	
	
	public FrmMain() {	
		
		initialize();
		FrmMainLoad();
	}
	
	//[start] MenuAndTool Action
	//保存方案
    public void btnSaveFormula_Action()
    {
    	try {
    		interfacefrmMainManage.DoSaveFormula(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
 
    public void btnOpenFormula_Action()
    {
    	try {
			interfacefrmMainManage.DoOpenFormula(this);
			this.FrmMainLoad();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void btnPublish_Action()
    {
    	interfacefrmMainManage.DoPublishFormula();
    }
    
    public void btnUploadScheme(){
    	interfacefrmMainManage.DoUploadScheme();
    }
	//[end]
	
    //[start] PanelFormulaBasicInfo Action
    public void panelFormulaName_Action()
    {
    	panelFormulaBasicInfoManage.DoPlFormulaName_Action(JTFFormulaName.getText());
    }
    
    public void panelFormulaAuthor_Action()
    {
    	panelFormulaBasicInfoManage.DoPlFormulaAuthor_Action(JTFFormulaAuthor.getText());
    }
    
    public void panelFormulaGoal_Action()
    {
    	panelFormulaBasicInfoManage.DoPlFormulaGoal_Action(JTFFormulaGoal.getText());
    }
    
    public void panelFormulaScale_Action()
    {
    	panelFormulaBasicInfoManage.DoPlFormulaScale_Action(JTFFormulaScale.getText());
    }
    
    public void panelFormulaOutline_Action()
    {
    	panelFormulaBasicInfoManage.DoPlFormulaOutline_Action(JTFFormulaOutline.getText());
    }
    //[end]
    
	//[start] Functions
	/**
	 * 加载主界面
	 */
    
    public void FrmMainLoad()
	{
		//初始化工具
		interfacefrmMainManage=new FrmMainManage();
		frmMainManage=new FrmMainManage();
		panelFormulaBasicInfoManage=new PanelFormulaBasicInfoManage();
		new LogicalStructureManage();
		formula = frmMainManage.formula_;
		trModelManage=new TrModelManage();
		lineManage=new LineManage();
       // modelList = frmMainManage.getModelList();
        combineMemberList = frmMainManage.getCombineMemberList();
		try {
			ModelManage.setModelList(TrModelManage.DoGetModelsBasicInfo());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//初始化Model树
		JTreeModel_Load();
		//初始化方案树
		JTreeFormula_Load();
		this.JTreeFormula_UpData();
		
		this.JTreeModel_UpData();
		//初始化panel信息
		PanelFormulaBasicInfo_Load();
		PanelLogicalStructure_Load();
		//设置拖拽源，目标，连接
		DragAndDrop();
		//获取系统时间
		getTime();
		//右键快捷键弹窗
		panelLogicalStructure_MouseRelease();
	}

	
	
	public void PanelFormulaBasicInfo_Load()
	{
		JTFFormulaAuthor.setText(formula.Author);
		JTFFormulaGoal.setText(formula.Goal);
		JTFFormulaName.setText(formula.Name);
		JTFFormulaOutline.setText(formula.Outline);
		JTFFormulaScale.setText(formula.Scale);   
		
	}
	
	/**
	 * 初始化预案逻辑结构
	 */
	public void PanelLogicalStructure_Load()
	{
		
		for(int i = 0 ; i <formula.getMemberList().size();i++)
		{		

			Graphics g=panelLogicalStructure.getGraphics();
			LogicalStructureManage.pomManage.pOMList.clear();
			MemberManage.MemberList.clear();
			for(int j=0;j<formula.getMemberList().size();j++)
			{
			    LogicalStructureManage.pomManage.pOMList.add(formula.getMemberList().get(j).pictureOfMember);
			}
			
			panelLogicalStructure.paint(g);
		}
	
	}
	/**
	 * 初始化模型树
	 */
	public void JTreeModel_Load() {
		root = new DefaultMutableTreeNode("模型库");

		DefaultMutableTreeNode virtualModel = new DefaultMutableTreeNode("虚拟模型");
		root.add(virtualModel);

		combineMemberNode = new DefaultMutableTreeNode(
				"组合成员");
		root.add(combineMemberNode);

		final DefaultMutableTreeNode eventGenerator = new DefaultMutableTreeNode(
				"事件发生器");
		root.add(eventGenerator);

		final DefaultMutableTreeNode controller = new DefaultMutableTreeNode("控制器");
		root.add(controller);

		final DefaultMutableTreeNode actuator = new DefaultMutableTreeNode("执行器");
		root.add(actuator);

		final DefaultMutableTreeNode controlledObject = new DefaultMutableTreeNode(
				"被控对象");
		root.add(controlledObject);

		final DefaultMutableTreeNode sensor = new DefaultMutableTreeNode("敏感器");
		root.add(sensor);
		
		modelList = ModelManage.getModelList();

		for (Model model : modelList) {
			if (String.valueOf(model.Model_category).equals("1")) // 控制器
			{
				DefaultMutableTreeNode controllerNode = new DefaultMutableTreeNode(model.Model_name+model.Muid);				
				controller.add(controllerNode);
				
			}

			if (String.valueOf(model.Model_category).equals("2")) // 执行器
			{
				DefaultMutableTreeNode actuatorNode = new DefaultMutableTreeNode(
						model.Model_name+model.Muid);
				actuator.add(actuatorNode);

			}

			if (String.valueOf(model.Model_category).equals("3")) // 被控制对象
			{

				DefaultMutableTreeNode controlledObjectNode = new DefaultMutableTreeNode(
						model.Model_name+model.Muid);
				controlledObject.add(controlledObjectNode);

			}

			if (String.valueOf(model.Model_category).equals("4")) // 敏感器
			{

				DefaultMutableTreeNode sensorNode = new DefaultMutableTreeNode(
						model.Model_name+model.Muid);
				sensor.add(sensorNode);

			}

			if (String.valueOf(model.Model_category).equals("8")) // 事件发生器
			{
				DefaultMutableTreeNode eventGeneratorNode = new DefaultMutableTreeNode(
						model.Model_name+model.Muid);
				eventGenerator.add(eventGeneratorNode);
			}
		}
		// end by lihao



DefaultTreeModel model = new DefaultTreeModel(root);
		treeModel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int mods = e.getModifiers();
				if((mods & InputEvent.BUTTON3_MASK) != 0){
					JPopupMenu pop = new JPopupMenu();
					JMenuItem DelInteritem = new JMenuItem("刷新");
					JMenuItem Deleteitem= new JMenuItem("删除");
					JMenuItem Choosepicture=new JMenuItem("选择");
					
					DelInteritem.addMouseListener(new MouseAdapter() {
						public void mouseReleased(MouseEvent e) {														
							root.removeAllChildren();
							try {
								ModelManage.setModelList(TrModelManage.DoGetModelsBasicInfo());
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							treeModel.updateUI();
							JTreeModel_Load();
							JTreeModel_UpData();
						}
					});
					
					Deleteitem.addMouseListener(new MouseAdapter() {
					    public void mouseReleased(MouseEvent e) {
						 DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeModel.getLastSelectedPathComponent();
						 String NodeName = node.toString();
						 String MUID=null;
						 for(Model model:modelList){
							String s=String.valueOf(model.Muid);  //int转化为String
							 if(NodeName.equals(model.Model_name+s)){
								 MUID=s;                          //获取要删除模型的MUID
								 if(model.Model_category==1){      //在界面上删除节点
									 controller.remove(node);
								 }
								 if(model.Model_category==2){
									 actuator.remove(node);
								 }
								 if(model.Model_category==3){
									 controlledObject.remove(node);
								 }
								 if(model.Model_category==4){
									 sensor.remove(node);
								 }
								 if(model.Model_category==8){
									 eventGenerator.remove(node);
								 }
								 treeModel.updateUI();
							 }	 
						 }		
						 trModelManage.DoDleteModelInfoNode(MUID);   //在数据库中删除节点
								}
							});
					//为模型添加图片
					Choosepicture.addMouseListener(new MouseAdapter() {
						public void mouseReleased(MouseEvent e) {
							String path = null;
							String MUID=null;
							
							JFileChooser chooser = new JFileChooser();
							chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
							chooser.setDialogType(JFileChooser.OPEN_DIALOG);
							int returnVal = chooser.showOpenDialog(null);							   
							if(returnVal == JFileChooser.APPROVE_OPTION){
							   path = chooser.getSelectedFile().getPath();
							}
				//有待改善			
							DefaultMutableTreeNode node = (DefaultMutableTreeNode)treeModel.getLastSelectedPathComponent();
							String NodeName=node.toString();							
							for(Model model:modelList){
								String s=String.valueOf(model.Muid);
								if(NodeName.equals(model.Model_name+s)){
									MUID=s;
									trModelManage.DoAddModelPicture(MUID,path);
								}
							}
						}
					});
					
					pop.add(DelInteritem);		
					pop.add(Deleteitem);
					pop.add(Choosepicture);
					pop.show(e.getComponent(), e.getX(), e.getY());
				}			
			}
		});
		treeModel.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				String nodeName = null;
				if (treeModel.getLastSelectedPathComponent()!= null)
	            {
					nodeName=treeModel.getLastSelectedPathComponent().toString();
	            }
				propretyChange.tableModelChange(e,nodeName);// 当点击树节点的时候，属性栏发生变换
			}
		});
		treeModel.setModel(model);

		for (int i = 0; i < treeModel.getRowCount(); i++) {
			treeModel.expandRow(i);
		}
	}
	
	/**
	 * 初始化方案树
	 */
	public void JTreeFormula_Load()
	{
		//设置根节点
		DefaultMutableTreeNode rootNode=new DefaultMutableTreeNode("新建预案");
		
		DefaultMutableTreeNode basicInfoNode=new DefaultMutableTreeNode("方案基本信息");
		rootNode.add(basicInfoNode);
		
		DefaultMutableTreeNode nameNode=new DefaultMutableTreeNode("方案名称");
		basicInfoNode.add(nameNode);
		
		DefaultMutableTreeNode goalNode=new DefaultMutableTreeNode("设计目的");
		basicInfoNode.add(goalNode);
		
		DefaultMutableTreeNode authorNode=new DefaultMutableTreeNode("方案作者");
		basicInfoNode.add(authorNode);
		
		DefaultMutableTreeNode scaleNode=new DefaultMutableTreeNode("方案规模");
		basicInfoNode.add(scaleNode);
		
		DefaultMutableTreeNode outlineNode=new DefaultMutableTreeNode("概述");
		basicInfoNode.add(outlineNode);
		
		//方案空间结构	
		DefaultMutableTreeNode spaceStructureNode=new DefaultMutableTreeNode("方案的空间结构");
		rootNode.add(spaceStructureNode);
		
		//方案逻辑结构	
		DefaultMutableTreeNode logicalStructureNode=new DefaultMutableTreeNode("方案的空间结构");
		rootNode.add(logicalStructureNode);
		
		//方案成员
		memberNode=new DefaultMutableTreeNode("方案成员");
		rootNode.add(memberNode);
		
		
		DefaultTreeModel model_formula=new DefaultTreeModel(rootNode);
		treeFormula.setModel(model_formula);
		
		for(int i=0; i<treeFormula.getRowCount(); i++)
	    {
			treeFormula.expandRow(i);
		}
	}
	
	/**
	 * 当逻辑界面上添加成员的时候，更新方案树上的成员
	 */
	public void JTreeFormula_UpData(){
		memberNode.removeAllChildren();
		for (Member memberTemp : formula.getMemberList())
        {
			DefaultMutableTreeNode newnode = new DefaultMutableTreeNode(memberTemp.Name);
			memberNode.add(newnode);
			DefaultTreeModel model = (DefaultTreeModel) treeFormula.getModel();
			model.nodeStructureChanged(memberNode);
			for(int i=0; i<treeFormula.getRowCount(); i++)
		    {
				treeFormula.expandRow(i);
			}
        }
	}
	/**
	 * 当逻辑界面上删除成员的时候，更新方案树上的成员
	 */
	public void JTreeFormula_RemoveData(){
		memberNode.removeAllChildren();
		for (Member memberTemp : formula.getMemberList())
        {
			DefaultMutableTreeNode newnode = new DefaultMutableTreeNode(memberTemp.Name);
			memberNode.add(newnode);
			DefaultTreeModel model = (DefaultTreeModel) treeFormula.getModel();
			model.nodeStructureChanged(memberNode);
			for(int i=0; i<treeFormula.getRowCount(); i++)
		    {
				treeFormula.expandRow(i);
			}
	 		treeFormula.updateUI();
        }
		JTreeModel_UpData();
	}
	
	/**
	 * 当新建组合成员时进行更新
	 */
	public void JTreeModel_UpData(){
		//清理组合成员库
		combineMemberNode.removeAllChildren();
		//加载组合成员
		trModelManage.DoGetCombineMemberInfo(combineMemberList);
		//可视化，在模型树中添加新的成员组合节点
		for(CombineMember conbineMember:combineMemberList){
			DefaultMutableTreeNode newnode = new DefaultMutableTreeNode(conbineMember.Name);
			combineMemberNode.add(newnode);
			DefaultTreeModel model = (DefaultTreeModel) treeModel.getModel();
			model.nodeStructureChanged(combineMemberNode);
			for(int i=0; i<treeModel.getRowCount(); i++)
		    {
				treeModel.expandRow(i);
			}
			treeModel.updateUI();
        
		}
	}
	/**
	 * 设置拖拽源
	 */
	public void DragAndDrop()
	{
		//设置拖拽源
        DragSource dragSource = DragSource.getDefaultDragSource();
        dragSource.createDefaultDragGestureRecognizer( treeModel, DnDConstants.ACTION_COPY_OR_MOVE, new DragAndDropDragGestureListener() );
        DropTarget dropTarget = new DropTarget( panelLogicalStructure, new DragAndDropDropTargetListener() );
	}
	
	//[end]
	
	
	/**
	 * 加载界面
	 */
	private void initialize() {
		setFocusable(true);
		setBounds(100, 100, 1000, 650);	
		setExtendedState(JFrame.MAXIMIZED_BOTH);				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e){
				System.out.println(e.getKeyChar());
			//new LogicalStructureManage().DoKeyDown(e);	
			}
		});
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				splitPane.setDividerLocation(0.86);	
				splitPane_2.setDividerLocation(0.65);
				
			}
		});
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(timeLab, BorderLayout.SOUTH);
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(new Color(240, 248, 255));
		getContentPane().add(tabbedPane, BorderLayout.NORTH);
		
		JPanel panelToolDeploy = new JPanel();
		panelToolDeploy.setBackground(new Color(240, 248, 255));
		tabbedPane.addTab("\u4EFF \u771F", null, panelToolDeploy, null);
		tabbedPane.setBackgroundAt(0, new Color(0, 153, 204));
		panelToolDeploy.setLayout(null);
		
		JPanel DeploypanelFormulaBase = new JPanel();
		DeploypanelFormulaBase.setBackground(new Color(240, 248, 255));
		DeploypanelFormulaBase.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u65B9\u6848\u57FA\u672C\u4FE1\u606F", TitledBorder.CENTER, TitledBorder.BOTTOM, null, null));
		DeploypanelFormulaBase.setBounds(10, 1, 153, 82);
		panelToolDeploy.add(DeploypanelFormulaBase);
		DeploypanelFormulaBase.setLayout(null);
		
		btnSaveFormula = new JButton("");
		btnSaveFormula.setBounds(10, 3, 69, 50);
		DeploypanelFormulaBase.add(btnSaveFormula);
		btnSaveFormula.setIcon(new ImageIcon("src\\psm\\Image\\onebit_11.png"));
    	btnSaveFormula.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) 
			{				
				btnSaveFormula_Action();
			}  	
    	});
		
		JLabel label_1 = new JLabel("\u4FDD\u5B58");
		label_1.setBounds(30, 53, 36, 15);
		DeploypanelFormulaBase.add(label_1);
		
		JButton btnOpenFormula = new JButton("");
		btnOpenFormula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnOpenFormula_Action();
			}
		});
		btnOpenFormula.setIcon(new ImageIcon("src\\psm\\Image\\Casehistory.png"));
		btnOpenFormula.setBounds(75, 3, 69, 50);
		DeploypanelFormulaBase.add(btnOpenFormula);
		
		
		JLabel label_2 = new JLabel("\u6253\u5F00");
		label_2.setBounds(96, 53, 36, 15);
		DeploypanelFormulaBase.add(label_2);
		
		JPanel DeploypanelLogStru = new JPanel();
		DeploypanelLogStru.setBackground(new Color(248, 248, 255));
		DeploypanelLogStru.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u903B\u8F91\u7ED3\u6784", TitledBorder.CENTER, TitledBorder.BOTTOM, null, null));
		DeploypanelLogStru.setLayout(null);
		DeploypanelLogStru.setBounds(161, 1, 416, 82);
		panelToolDeploy.add(DeploypanelLogStru);
		
		JButton button_1 = new JButton("");
		button_1.setIcon(new ImageIcon("src\\psm\\Image\\edit_blue.png"));
		button_1.setBounds(5, 3, 69, 50);
		DeploypanelLogStru.add(button_1);
		
		JLabel label_3 = new JLabel("\u8BBE\u7F6E\u6210\u5458");
		label_3.setBounds(16, 53, 59, 15);
		DeploypanelLogStru.add(label_3);
		
		JButton btnAddObj = new JButton("");

		btnAddObj.setIcon(new ImageIcon("src\\psm\\Image\\check_purple.png"));
		btnAddObj.setBounds(73, 3, 69, 50);
		DeploypanelLogStru.add(btnAddObj);
		
		JLabel label_4 = new JLabel("\u8BA2\u8D2D\u5BF9\u8C61");
		label_4.setBounds(85, 53, 59, 15);
		DeploypanelLogStru.add(label_4);
		
		JButton btnDelObj = new JButton("");
		btnDelObj.setIcon(new ImageIcon("src\\psm\\Image\\delete_blue.png"));
		btnDelObj.setBounds(140, 3, 69, 50);
		DeploypanelLogStru.add(btnDelObj);
		
		JButton btnAddInter = new JButton("");			
		btnAddInter.setIcon(new ImageIcon("src\\psm\\Image\\12.png"));
		btnAddInter.setBounds(207, 3, 69, 50);
		DeploypanelLogStru.add(btnAddInter);
		
		JButton btnDelInter = new JButton("");
		btnDelInter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDelInter.setIcon(new ImageIcon("src\\psm\\Image\\delete_gray.png"));
		btnDelInter.setBounds(273, 3, 69, 50);
		DeploypanelLogStru.add(btnDelInter);
		
		JButton btnMemberCombine = new JButton("");
		btnMemberCombine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectMemberNum=frmMainManage.OnBtnMemberCombine();
				if(selectMemberNum>1){
					FrmDeployCombineMember frmDeployCombineMember=new FrmDeployCombineMember();
					frmDeployCombineMember.frame.setVisible(true);
					
				}
				else if(selectMemberNum==1){
					JOptionPane.showMessageDialog(new JPanel(), "选中的成员只有一个，不能进行成员组合，请继续选中成员！",
							"提示", JOptionPane.YES_NO_CANCEL_OPTION);
				}else{
					JOptionPane.showMessageDialog(new JPanel(), "组合成员失败，请检查原因！",
							"提示", JOptionPane.YES_NO_CANCEL_OPTION);
				}
				
				
			}
		});
		btnMemberCombine.setIcon(new ImageIcon("src\\psm\\Image\\13.png"));
		btnMemberCombine.setBounds(340, 3, 69, 50);
		DeploypanelLogStru.add(btnMemberCombine);
		
		JLabel label_7 = new JLabel("\u53D6\u6D88\u5BF9\u8C61");
		label_7.setBounds(154, 53, 59, 15);
		DeploypanelLogStru.add(label_7);
		
		JLabel label_8 = new JLabel("\u8BA2\u8D2D\u4EA4\u4E92");
		label_8.setBounds(217, 53, 59, 15);
		DeploypanelLogStru.add(label_8);
		
		JLabel label_9 = new JLabel("\u53D6\u6D88\u4EA4\u4E92");
		label_9.setBounds(283, 53, 59, 15);
		DeploypanelLogStru.add(label_9);
		
		JLabel label_10 = new JLabel("\u6210\u5458\u7EC4\u5408");
		label_10.setBounds(350, 53, 55, 15);
		DeploypanelLogStru.add(label_10);
		
		JPanel DeploypanelCheckPub = new JPanel();
		DeploypanelCheckPub.setBackground(new Color(248, 248, 255));
		DeploypanelCheckPub.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u65B9\u6848\u6821\u9A8C\u4E0E\u53D1\u5E03", TitledBorder.CENTER, TitledBorder.BOTTOM, null, null));
		DeploypanelCheckPub.setLayout(null);
		DeploypanelCheckPub.setBounds(575, 1, 221, 82);
		panelToolDeploy.add(DeploypanelCheckPub);
		
		JButton formulaCheck = new JButton("");
		formulaCheck.setIcon(new ImageIcon("src\\psm\\Image\\15.png"));
		formulaCheck.setBounds(5, 3, 69, 50);
		DeploypanelCheckPub.add(formulaCheck);
		
		JLabel label_5 = new JLabel("\u65B9\u6848\u6821\u9A8C");
		label_5.setBounds(20, 53, 59, 15);
		DeploypanelCheckPub.add(label_5);
		
		JButton btnPublish = new JButton("");
		btnPublish.setIcon(new ImageIcon("src\\psm\\Image\\fabu.png"));
		btnPublish.setBounds(74, 3, 69, 50);
		DeploypanelCheckPub.add(btnPublish);
		btnPublish.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) 
			{
				btnPublish_Action();
			}
		});
		
		JLabel label_6 = new JLabel("\u65B9\u6848\u53D1\u5E03");
		label_6.setBounds(84, 53, 59, 15);
		DeploypanelCheckPub.add(label_6);
		
		JButton buttonupload = new JButton("");
		buttonupload.setIcon(new ImageIcon(FrmMain.class.getResource("/psm/Image/shangchuan.png")));
		buttonupload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnUploadScheme();
			}
		});
		buttonupload.setBounds(142, 3, 69, 50);
		DeploypanelCheckPub.add(buttonupload);
		
		JLabel label7 = new JLabel("\u65B9\u6848\u4E0A\u4F20");
		label7.setBounds(153, 53, 59, 15);
		DeploypanelCheckPub.add(label7);
		
		JPanel panelToolEdit = new JPanel();
		panelToolEdit.setBackground(new Color(240, 248, 255));
		tabbedPane.addTab("\u7F16 \u8F91", null, panelToolEdit, null);
		panelToolEdit.setLayout(null);
		
		JPanel EditpanelClipboard = new JPanel();
		EditpanelClipboard.setBackground(new Color(240, 248, 255));
		EditpanelClipboard.setBorder(new TitledBorder(null, "\u526A\u8D34\u677F", TitledBorder.CENTER, TitledBorder.BOTTOM, null, null));
		EditpanelClipboard.setLayout(null);
		EditpanelClipboard.setBounds(10, 1, 122, 82);
		panelToolEdit.add(EditpanelClipboard);
		
		JButton button_9 = new JButton("");
		button_9.setIcon(new ImageIcon(FrmMain.class.getResource("/psm/Image/17.png")));
		button_9.setBounds(10, 3, 69, 50);
		EditpanelClipboard.add(button_9);
		
		JButton button_10 = new JButton("");
		button_10.setIcon(new ImageIcon(FrmMain.class.getResource("/psm/Image/CutHS.png")));
		button_10.setBounds(80, 3, 36, 25);
		EditpanelClipboard.add(button_10);
		
		JButton button_11 = new JButton("");
		button_11.setIcon(new ImageIcon(FrmMain.class.getResource("/psm/Image/CopyHS.png")));
		button_11.setBounds(80, 27, 36, 25);
		EditpanelClipboard.add(button_11);
		
		JLabel label_11 = new JLabel("\u7C98\u8D34");
		label_11.setBounds(30, 53, 44, 15);
		EditpanelClipboard.add(label_11);
		
		JLabel label_12 = new JLabel("\u590D\u5236");
		label_12.setBounds(73, 53, 54, 15);
		EditpanelClipboard.add(label_12);
		
		JPanel panelToolView = new JPanel();
		panelToolView.setBackground(new Color(240, 248, 255));
		tabbedPane.addTab("\u89C6 \u56FE", null, panelToolView, null);
		panelToolView.setLayout(null);
		
		JPanel ViewpanelModelManage = new JPanel();
		ViewpanelModelManage.setBackground(new Color(240, 248, 255));
		ViewpanelModelManage.setBorder(new TitledBorder(null, "\u6A21\u578B\u7BA1\u7406\u5668", TitledBorder.CENTER, TitledBorder.BOTTOM, null, null));
		ViewpanelModelManage.setLayout(null);
		ViewpanelModelManage.setBounds(10, 1, 107, 82);
		panelToolView.add(ViewpanelModelManage);
		
		JButton button_12 = new JButton("");
		button_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cBUpDown.getSelectedItem().equals("模型下载")){
					FrmDownModel window = null;
					try {
						window = new FrmDownModel();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					window.frame.setVisible(true);
				}
				if(cBUpDown.getSelectedItem().equals("模型上传")){
					FrmUpModel window = null;
					try {
						window = new FrmUpModel();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					window.strat();
				}
			}
		});
		button_12.setIcon(new ImageIcon(FrmMain.class.getResource("/psm/Image/2.png")));
		button_12.setBounds(10, 3, 80, 45);
		ViewpanelModelManage.add(button_12);
		
		cBUpDown = new JComboBox();
		cBUpDown.setModel(new DefaultComboBoxModel(new String[] {"\u6A21\u578B\u4E0A\u4F20", "\u6A21\u578B\u4E0B\u8F7D"}));
		cBUpDown.setBounds(10, 47, 80, 19);
		ViewpanelModelManage.add(cBUpDown);
		
		JPanel panelToolTools = new JPanel();
		panelToolTools.setBackground(new Color(240, 248, 255));
		tabbedPane.addTab("\u5DE5 \u5177", null, panelToolTools, null);
		panelToolTools.setLayout(null);
		
		JPanel ToolpanelStyle = new JPanel();
		ToolpanelStyle.setBackground(new Color(240, 248, 255));
		ToolpanelStyle.setBorder(new TitledBorder(null, "\u754C\u9762\u98CE\u683C", TitledBorder.CENTER, TitledBorder.BOTTOM, null, null));
		ToolpanelStyle.setLayout(null);
		ToolpanelStyle.setBounds(203, 1, 103, 82);
		panelToolTools.add(ToolpanelStyle);
		
		JButton button_13 = new JButton("");
		button_13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				color=JColorChooser.showDialog(null, "选择线的颜色！", Color.BLUE);
				//Line.lineColor=color;
				
			}
		});
		
		button_13.setIcon(new ImageIcon(FrmMain.class.getResource("/psm/Image/changestyle.png")));
		button_13.setBounds(7, 3, 80, 45);
		ToolpanelStyle.add(button_13);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(7, 47, 80, 19);
		ToolpanelStyle.add(comboBox_1);
		
		JPanel ToolpanelSet = new JPanel();
		ToolpanelSet.setBackground(new Color(240, 248, 255));
		ToolpanelSet.setBorder(new TitledBorder(null, "\u8BBE\u7F6E", TitledBorder.CENTER, TitledBorder.BOTTOM, null, null));
		ToolpanelSet.setLayout(null);
		ToolpanelSet.setBounds(110, 1, 96, 82);
		panelToolTools.add(ToolpanelSet);
		
		JButton button_14 = new JButton("");
		button_14.setIcon(new ImageIcon(FrmMain.class.getResource("/psm/Image/peizhi.png")));
		button_14.setBounds(7, 3, 80, 45);
		ToolpanelSet.add(button_14);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"\u8BBE\u7F6E\u9884\u6848", "\u8BBE\u7F6E\u6A21\u578B\u5E93"}));
		comboBox_2.setBounds(7, 47, 80, 19);
		ToolpanelSet.add(comboBox_2);
		
		JPanel ToolpanelProduce = new JPanel();
		ToolpanelProduce.setBackground(new Color(240, 248, 255));
		ToolpanelProduce.setBorder(new TitledBorder(null, "\u751F\u6210", TitledBorder.CENTER, TitledBorder.BOTTOM, null, null));
		ToolpanelProduce.setLayout(null);
		ToolpanelProduce.setBounds(10, 1, 103, 82);
		panelToolTools.add(ToolpanelProduce);
		
		JButton button_15 = new JButton("");
		button_15.setIcon(new ImageIcon(FrmMain.class.getResource("/psm/Image/shangchuan.png")));
		button_15.setBounds(10, 3, 80, 45);
		ToolpanelProduce.add(button_15);
		
		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setModel(new DefaultComboBoxModel(new String[] {"\u751F\u6210FOM", "\u751F\u6210\u9884\u6848"}));
		comboBox_3.setBounds(10, 47, 80, 19);
		ToolpanelProduce.add(comboBox_3);
		
		JToolBar panelToolHelp = new JToolBar();
		
		panelToolHelp.setFloatable(false);
		
		tabbedPane.addTab("\u5E2E \u52A9", null, panelToolHelp, null);
		
		JPanel HelppanelHelp = new JPanel();
		HelppanelHelp.setBackground(new Color(240, 248, 255));
		panelToolHelp.add(HelppanelHelp);
		HelppanelHelp.setLayout(null);
		
		JPanel panel_17 = new JPanel();
		panel_17.setBackground(new Color(240, 248, 255));
		panel_17.setBorder(new TitledBorder(null, "\u5E2E\u52A9", TitledBorder.CENTER, TitledBorder.BOTTOM, null, null));
		panel_17.setLayout(null);
		panel_17.setBounds(10, 1, 103, 82);
		HelppanelHelp.add(panel_17);
		
		JButton button_16 = new JButton("");
		button_16.setIcon(new ImageIcon(FrmMain.class.getResource("/psm/Image/onebit_37.png")));
		button_16.setBounds(10, 3, 80, 45);
		panel_17.add(button_16);
		
		JComboBox comboBox_4 = new JComboBox();
		comboBox_4.setModel(new DefaultComboBoxModel(new String[] {"\u67E5\u770B\u5E2E\u52A9", "\u5173\u4E8ESSM"}));
		comboBox_4.setBounds(10, 47, 80, 19);
		panel_17.add(comboBox_4);
		
		
		splitPane.setOneTouchExpandable(true);
		getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setOneTouchExpandable(true);
		splitPane.setLeftComponent(splitPane_1);
		
		JPanel treeFormulaPanel = new JPanel();
		splitPane_1.setLeftComponent(treeFormulaPanel);
		treeFormulaPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel FormuLable = new JLabel("\u65B9\u6848\u8D44\u6E90\u7BA1\u7406\u5668");
		FormuLable.setBackground(new Color(135, 206, 250));
		treeFormulaPanel.add(FormuLable, BorderLayout.NORTH);
		
		
		treeFormulaPanel.add(treeFormula, BorderLayout.CENTER);
		
		JTabbedPane tabbedPaneFormula = new JTabbedPane(JTabbedPane.TOP);
		splitPane_1.setRightComponent(tabbedPaneFormula);
		panelFormula.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int w=panelFormula.getWidth();
				int h=panelFormula.getHeight();
				panelFormulaBasicInfo.setLocation((w-574)/2, (h-369)/2);
			}
		});
		
		
		panelFormula.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelFormula.setBackground(new Color(240, 248, 255));
		tabbedPaneFormula.addTab("\u65B9\u6848\u57FA\u672C\u4FE1\u606F", null, panelFormula, null);
		panelFormulaBasicInfo.setBounds(30, 28, 574, 369);
		panelFormula.setLayout(null);
		
		//
		//方案基本信息
		//
		
		panelFormulaBasicInfo.setBackground(new Color(240, 248, 255));
		panelFormulaBasicInfo.setBorder(new TitledBorder(null, "\u65B9\u6848\u57FA\u672C\u4FE1\u606F", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panelFormula.add(panelFormulaBasicInfo);
		panelFormulaBasicInfo.setLayout(null);
		
		
		JLabel labFormulaName = new JLabel("\u65B9\u6848\u540D\u79F0\uFF1A");
		labFormulaName.setBounds(21, 40, 77, 15);
		panelFormulaBasicInfo.add(labFormulaName);
		
		JLabel labFormulaAuthor = new JLabel("\u65B9\u6848\u4F5C\u8005\uFF1A");
		labFormulaAuthor.setBounds(21, 84, 77, 15);
		panelFormulaBasicInfo.add(labFormulaAuthor);
		
		JLabel labFormulaGoal = new JLabel("\u65B9\u6848\u76EE\u7684:");
		labFormulaGoal.setBounds(21, 128, 77, 15);
		panelFormulaBasicInfo.add(labFormulaGoal);
		
		JTFFormulaName= new JTextField();
		JLabel labFormulaOutline = new JLabel("\u65B9\u6848\u6982\u8FF0\uFF1A");
		labFormulaOutline.setBounds(21, 185, 77, 15);
		panelFormulaBasicInfo.add(labFormulaOutline);
		JTFFormulaName.setBounds(92, 37, 171, 21);
		panelFormulaBasicInfo.add(JTFFormulaName);
		JTFFormulaName.setColumns(10);
		JTFFormulaName.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent e) {
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				panelFormulaName_Action();
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				panelFormulaName_Action();
			}			
		});

		
		JTFFormulaAuthor = new JTextField();
		JTFFormulaAuthor.setBounds(92, 81, 171, 21);
		JTFFormulaAuthor.setColumns(10);
		JTFFormulaAuthor.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent arg0) {			
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				panelFormulaAuthor_Action();			
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				panelFormulaAuthor_Action();			
			}			
		});
		panelFormulaBasicInfo.add(JTFFormulaAuthor);
				
		JTFFormulaGoal = new JTextField();
		JTFFormulaGoal.setBounds(92, 125, 171, 21);
		JTFFormulaGoal.setColumns(10);
		JTFFormulaGoal.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent arg0) {			
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				panelFormulaGoal_Action();			
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				panelFormulaGoal_Action();			
			}			
		});
		panelFormulaBasicInfo.add(JTFFormulaGoal);
		
		JTFFormulaOutline = new JTextArea();
		JTFFormulaOutline.setLineWrap(true);
		JTFFormulaOutline.setBounds(92, 185, 456, 163);
		JTFFormulaOutline.setBorder(BorderFactory.createLineBorder(Color.gray,1));
		JTFFormulaOutline.setColumns(10);
		JTFFormulaOutline.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent arg0) {			
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				panelFormulaOutline_Action();			
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				panelFormulaOutline_Action();			
			}			
		});
		panelFormulaBasicInfo.add(JTFFormulaOutline);
		
		JLabel labFormulaScale = new JLabel("\u65B9\u6848\u89C4\u6A21\uFF1A");
		labFormulaScale.setBounds(300, 40, 67, 15);
		panelFormulaBasicInfo.add(labFormulaScale);
		
		JTFFormulaScale = new JTextArea();
		JTFFormulaScale.setFont(new Font("Monospaced", Font.PLAIN, 13));
		JTFFormulaScale.setLineWrap(true);
		JTFFormulaScale.setBorder(BorderFactory.createLineBorder(Color.gray,1));
		JTFFormulaScale.setBounds(300, 65, 248, 81);
		JTFFormulaScale.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent arg0) {			
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				panelFormulaScale_Action();			
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				panelFormulaScale_Action();			
			}			
		});
		panelFormulaBasicInfo.add(JTFFormulaScale);
		JTFFormulaScale.setColumns(10);
	
		
		tabbedPaneFormula.addTab("\u65B9\u6848\u903B\u8F91\u7ED3\u6784", null, panelLogicalStructure, null);
		panelLogicalStructure.setLayout(null);
		panelLogicalStructure.setName("WK");
		panelLogicalStructure.setDoubleBuffered(true);
		splitPane_1.setDividerLocation(160);
		
		JPanel treeModelPanel = new JPanel();
		splitPane.setRightComponent(treeModelPanel);
		treeModelPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel Modellabel = new JLabel("\u6A21\u578B\u8D44\u6E90\u7BA1\u7406\u5668");
		treeModelPanel.add(Modellabel, BorderLayout.NORTH);
		
		
		splitPane_2.setDividerLocation(300);
		splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		treeModelPanel.add(splitPane_2, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane_2.setRightComponent(scrollPane);
		
		DefaultTableModel propertyModel = new DefaultTableModel();
		propertyTable=new JTable(propertyModel){
		public boolean isCellEditable(int row, int column) {
			return false;
		}// 表格不允许被编辑
	};
		JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.TOP);
		scrollPane.setViewportView(tabbedPane_2);		
		tabbedPane_2.addTab("\u5C5E \u6027", null, propertyTable, null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane_2.setLeftComponent(scrollPane_1);
		
		
		scrollPane_1.setViewportView(treeModel);
		splitPane.setDividerLocation(800);
	}
}






