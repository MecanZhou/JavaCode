package manager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SpringLayout;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class Terminal {
	
	public JFrame frame;
	public static JMenuItem mntmNewMenuItem1;
	public static JMenuItem mntmNewMenuItem2;
	public static JTabbedPane _tablepanelHandler;
	public static JPanel _panelHandler;
	public static JScrollPane _scrollpaneHandler;
	public static JScrollPane _runMathineInfo;
	public static JPanel _viuwPanel;
	public static JScrollPane _viewInfo;
	public static JScrollPane _tableDistribute;
	public static JTextArea _textAreaHandler;
	public static JSlider _slider;
	public static JLabel _schemename;
	public static JLabel _schemeID;
	private JTextField timeScaleText;
	public static JSplitPane _splitpane1;
	public static JSplitPane _splitpane2;
	public static JSplitPane _splitpane3;
	public static JSplitPane _splitpane4;
	public static JSplitPane _splitpane5;
	public static JTree _treeHandler;
	public static DefaultMutableTreeNode TreeNode[] = new DefaultMutableTreeNode[100];
	public static int CountofTreeNode = 0;
	
	/**
	 * 主题窗口创建
	 */
	public void initialize() {
		
		frame=new JFrame();
		frame.setSize(1200,850);
		frame.setBounds(100, 0, 1200, 850);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("ManageSystem");
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent arg0) {
				_splitpane1.setDividerLocation(0.85);
				_splitpane2.setDividerLocation(0.73);
				_splitpane3.setDividerLocation(0.85);
				_splitpane4.setDividerLocation(0.625);
				_splitpane5.setDividerLocation(0.4);
			}
		});
		
		/****************************************一1、splitPane界面设计开始************************************************/
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setDividerLocation(15);
		splitPane.setEnabled(false);
		splitPane.setDividerSize(0);
		frame.getContentPane().add(splitPane);
		
		/***********************************1.1、panel1界面设计开始*******************************************/
		
		JPanel panel1 = new JPanel();
		splitPane.setRightComponent(panel1);
		panel1.setLayout(new BorderLayout(0, 0));
		
		/******************************1.1.1、splitPane1界面设计开始**************************************/
		
		JSplitPane splitPane1 = new JSplitPane();
		splitPane1.setDividerSize(0);
		panel1.add(splitPane1, BorderLayout.CENTER);
		splitPane1.setDividerLocation(240);
		
		/*************************1.1.1.1、splitPane_1界面设计开始*********************************/
		
		JSplitPane splitPane_1 = new JSplitPane();
		_splitpane1 = splitPane_1;
		splitPane_1.setDividerLocation(650);
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.setEnabled(false);
		splitPane_1.setDividerSize(0);
		splitPane1.setLeftComponent(splitPane_1);
		
		/********************1.1.1.1.1、panel_1界面设计开始****************************/
		
		JPanel panel_1 = new JPanel();
		splitPane_1.setRightComponent(panel_1);;
		panel_1.setBounds(598, 450, 196, 86);
		panel_1.setBorder(BorderFactory.createTitledBorder("控制面板"));
		panel_1.setLayout(new FormLayout(
		new ColumnSpec[] { FormFactory.UNRELATED_GAP_COLSPEC,
			ColumnSpec.decode("40px"),
			FormFactory.UNRELATED_GAP_COLSPEC,
			ColumnSpec.decode("40px"),
			FormFactory.RELATED_GAP_COLSPEC,
			ColumnSpec.decode("44px"),
			FormFactory.UNRELATED_GAP_COLSPEC,
			ColumnSpec.decode("1px"),
			FormFactory.UNRELATED_GAP_COLSPEC,
			ColumnSpec.decode("40px"), }, new RowSpec[] {
			RowSpec.decode("25px"), RowSpec.decode("23px"),
			FormFactory.RELATED_GAP_ROWSPEC,
			RowSpec.decode("23px"), }));
		
		JButton btnNewButton = new JButton(new ImageIcon("be.png"));
		panel_1.add(btnNewButton, "4, 2, fill, fill");
		
		JButton btnNewButton_1 = new JButton(new ImageIcon("en.png"));
		panel_1.add(btnNewButton_1, "2, 2, fill, fill");
		
		JButton btnNewButton_2 = new JButton();
		btnNewButton_2.setIcon(new ImageIcon("pa.png"));
		panel_1.add(btnNewButton_2, "6, 2, fill, fill");
		
		JButton btnNewButton_3 = new JButton();
		btnNewButton_3.setIcon(new ImageIcon("re.png"));
		panel_1.add(btnNewButton_3, "8, 2, 3, 1, fill, fill");
		
		Label label = new Label("开始");
		panel_1.add(label, "4, 4, fill, top");
		Label label_1 = new Label("暂停/继续");
		panel_1.add(label_1, "6, 4, 3, 1, fill, top");
		Label label_2 = new Label("停止");
		panel_1.add(label_2, "2, 4, fill, top");
		Label label_3 = new Label("刷新");
		panel_1.add(label_3, "10, 4, fill, top");
		
		/********************1.1.1.1.1、panel_1界面设计开始****************************/
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 10, 167, 425);
		splitPane_1.setLeftComponent(scrollPane);
		scrollPane.setBorder(BorderFactory.createTitledBorder("方案选择"));
		
		/*************************1.1.1.1、splitPane_1界面设计结束*********************************/
		
		/*************************1.1.1.2、splitPane_2界面设计开始*********************************/
		
		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setDividerSize(0);
		_splitpane2 = splitPane_2;
		splitPane_2.setDividerLocation(700);
		splitPane_2.setEnabled(false);
		splitPane_2.setDividerSize(0);
		splitPane1.setRightComponent(splitPane_2);
		
		/********************1.1.1.2.1、splitPane_2_1界面设计开始****************************/
		
		JSplitPane splitPane_2_1 = new JSplitPane();
		_splitpane3 = splitPane_2_1;
		splitPane_2_1.setDividerLocation(600);
		splitPane_2_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_2.setLeftComponent(splitPane_2_1);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(177, 4, 445, 425);
		splitPane_2_1.setLeftComponent(tabbedPane);
		_tablepanelHandler = tabbedPane;
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("成员信息", null, panel_2, null);
		_panelHandler = panel_2;
		panel_2.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_1 = new JScrollPane();
		_scrollpaneHandler = scrollPane_1;
		panel_2.add(scrollPane_1);
		JTree tree_1 = new JTree(TreeNode[CountofTreeNode]);
		scrollPane_1.setRowHeaderView(tree_1);
		_treeHandler = tree_1;
		tree_1.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("运行机信息", null, panel_3, null);
		panel_3.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_2 = new JScrollPane();
		panel_3.add(scrollPane_2);
		_runMathineInfo = scrollPane_2;
		
		JPanel panel_4 = new JPanel();
		tabbedPane.addTab("成员参数信息", null, panel_4, null);
		_viuwPanel = panel_4;
		JScrollPane scrollPane_3 = new JScrollPane();
		panel_4.add(scrollPane_3);
		_viewInfo = scrollPane_3;
		
		JPanel panel_5 = new JPanel();
		tabbedPane.addTab("成员分配信息", null, panel_5, null);
		JScrollPane scrollPane_4 = new JScrollPane();
		_tableDistribute = scrollPane_4;
		SpringLayout sl_panel_5 = new SpringLayout();
		sl_panel_5.putConstraint(SpringLayout.NORTH, scrollPane_4, 10, SpringLayout.NORTH, panel_5);
		sl_panel_5.putConstraint(SpringLayout.WEST, scrollPane_4, 0, SpringLayout.WEST, panel_5);
		sl_panel_5.putConstraint(SpringLayout.SOUTH, scrollPane_4, -66, SpringLayout.SOUTH, panel_5);
		sl_panel_5.putConstraint(SpringLayout.EAST, scrollPane_4, 0,SpringLayout.EAST, panel_5);
		panel_5.setLayout(sl_panel_5);
		panel_5.add(scrollPane_4);
		JLabel lblNewLabel_12 = new JLabel("New label");
		sl_panel_5.putConstraint(SpringLayout.NORTH, lblNewLabel_12, 23, SpringLayout.SOUTH, scrollPane_4);
		sl_panel_5.putConstraint(SpringLayout.EAST, lblNewLabel_12, -177, SpringLayout.EAST, panel_5);
		lblNewLabel_12.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		lblNewLabel_12.setText("提示： 点击IP选择框，会出现选择下拉框,选中对应IP即可");
		panel_5.add(lblNewLabel_12);
		
		JScrollPane scrollPane_5 = new JScrollPane();
		scrollPane_5.setBounds(182, 445, 409, 96);
		splitPane_2_1.setRightComponent(scrollPane_5);
		JTextArea textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		scrollPane_5.setViewportView(textArea);
		_textAreaHandler = textArea;
		
		/********************1.1.1.2.1、splitPane_2_1界面设计结束****************************/
		
		/********************1.1.1.2.2、splitPane_2_2界面设计开始****************************/

		JSplitPane splitPane_2_2 = new JSplitPane();
		_splitpane4 = splitPane_2_2;
		splitPane_2_2.setDividerLocation(450);
		splitPane_2_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_2.setRightComponent(splitPane_2_2);
		
		/*****************1.1.1.2.2.1、splitPane_3界面设计开始***********************/
		
		JSplitPane splitPane_3 = new JSplitPane();
		splitPane_3.setEnabled(false);
		splitPane_3.setDividerSize(0);
		_splitpane5 = splitPane_3;
		splitPane_3.setDividerLocation(150);
		splitPane_3.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_2_2.setRightComponent(splitPane_3);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBounds(632, 299, 162, 110);
		splitPane_3.setLeftComponent(panel_6);
		panel_6.setBorder(BorderFactory.createTitledBorder("仿真速度调整"));
		panel_6.setLayout(null);
		
		JSlider slider = new JSlider(0,10,0);
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setBounds(10, 21, 228, 48);
		_slider=slider;
		panel_6.add(slider);
		slider.setMajorTickSpacing(2);
		slider.setMinorTickSpacing(1);
		
		timeScaleText = new JTextField();
		timeScaleText.setText("1000");
		timeScaleText.setBounds(71, 82, 54, 21);
		panel_6.add(timeScaleText);
		timeScaleText.setColumns(10);
		
		JLabel label_8 = new JLabel("\u5355\u4F4D\u523B\u5EA6\uFF1A");
		label_8.setBounds(10, 85, 66, 15);
		panel_6.add(label_8);
		JLabel label_9 = new JLabel("\u6BEB\u79D2");
		label_9.setBounds(131, 85, 37, 15);
		panel_6.add(label_9);
		
		JPanel panel_7 = new JPanel();
		panel_7.setLayout(null);
		panel_7.setBounds(10, 429, 153, 90);
		splitPane_3.setRightComponent(panel_7);
		panel_7.setBorder(BorderFactory.createTitledBorder("版本信息"));

		JLabel label_6 = new JLabel("版本：RTI1.3版");
		label_6.setFont(new Font("宋体", Font.PLAIN, 14));
		label_6.setBounds(10, 37, 142, 36);
		panel_7.add(label_6);
		JLabel label_7 = new JLabel("<html>\u7248\u6743\u5F52\u5C5E\uFF1A<br/>\u6C5F\u6C49\u5927\u5B66\u6570\u8BA1\u5B66\u9662\u4EFF\u771F\u5B9E\u9A8C\u5BA4</html>");
		label_7.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		label_7.setBounds(10, 90, 195, 53);
		panel_7.add(label_7);
		
		/*****************1.1.1.2.2.1、splitPane_3界面设计结束***********************/
		
		/*****************1.1.1.2.2.2、panel_8界面设计开始***************************/
		
		JPanel panel_8 = new JPanel();
		panel_8.setLayout(null);
		splitPane_2_2.setLeftComponent(panel_8);
		panel_8.setBounds(632, 10, 162, 279);
		panel_8.setBorder(BorderFactory.createTitledBorder("方案属性"));

		JLabel lblid = new JLabel("方案ID");
		lblid.setBounds(10, 50, 93, 20);
		panel_8.add(lblid);

		JLabel lblNewLabel = new JLabel("方案名称");
		lblNewLabel.setBounds(10, 100, 93, 15);
		panel_8.add(lblNewLabel);

		JLabel label_4 = new JLabel("方案生产日期");
		label_4.setBounds(10, 150, 93, 20);
		panel_8.add(label_4);

		JLabel lblNewLabel_1 = new JLabel("方案作者");
		lblNewLabel_1.setBounds(10, 193, 93, 20);
		panel_8.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("方案路径");
		lblNewLabel_2.setBounds(10, 250, 93, 20);
		panel_8.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("方案供应商");
		lblNewLabel_3.setBounds(10, 300, 93, 20);
		panel_8.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("方案运行次数");
		lblNewLabel_4.setBounds(10, 350, 93, 20);
		panel_8.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("仿真步长");
		lblNewLabel_5.setBounds(10, 400, 93, 20);
		panel_8.add(lblNewLabel_5);

		JLabel label_5 = new JLabel("");
		label_5.setOpaque(true);
		label_5.setBackground(Color.WHITE);
		label_5.setBounds(129, 50, 100, 30);
		panel_8.add(label_5);
		_schemeID = label_5;

		JLabel lblNewLabel_6 = new JLabel("");
		lblNewLabel_6.setOpaque(true);
		lblNewLabel_6.setBackground(Color.WHITE);
		lblNewLabel_6.setBounds(129, 100, 100, 30);
		panel_8.add(lblNewLabel_6);
		_schemename = lblNewLabel_6;
		JLabel lblNewLabel_7 = new JLabel("");
		lblNewLabel_7.setOpaque(true);
		lblNewLabel_7.setBackground(Color.WHITE);
		lblNewLabel_7.setBounds(129, 200, 100, 30);
		panel_8.add(lblNewLabel_7);

		JLabel lblNewLabel_8 = new JLabel("");
		lblNewLabel_8.setOpaque(true);
		lblNewLabel_8.setBackground(Color.WHITE);
		lblNewLabel_8.setBounds(129, 150, 100, 30);
		panel_8.add(lblNewLabel_8);

		JLabel lblNewLabel_9 = new JLabel("");
		lblNewLabel_9.setOpaque(true);
		lblNewLabel_9.setBackground(Color.WHITE);
		lblNewLabel_9.setBounds(129, 250, 100, 30);
		panel_8.add(lblNewLabel_9);

		JLabel lblNewLabel_10 = new JLabel("");
		lblNewLabel_10.setOpaque(true);
		lblNewLabel_10.setBackground(Color.WHITE);
		lblNewLabel_10.setBounds(129, 300, 100, 30);
		panel_8.add(lblNewLabel_10);

		JLabel lblNewLabel_11 = new JLabel("");
		lblNewLabel_11.setOpaque(true);
		lblNewLabel_11.setBackground(Color.WHITE);
		lblNewLabel_11.setBounds(129, 350, 100, 30);
		panel_8.add(lblNewLabel_11);

		JSpinner spinner = new JSpinner();
		spinner.setBounds(129, 397, 100, 30);
		panel_8.add(spinner);
		
		/*****************1.1.1.2.2.2、panel_8界面设计开始***************************/
		
		/********************1.1.1.2.2、splitPane_2_2界面设计结束****************************/
		
		/*************************1.1.1.2、splitPane_2界面设计结束*********************************/
		
		/******************************1.1.1、splitPane1界面设计结束**************************************/
		
		/***********************************1.1、panel1界面设计结束*******************************************/
		
		JPanel panel2 = new JPanel();
		splitPane.setLeftComponent(panel2);
		
		/****************************************一1、splitPane界面设计结束************************************************/
		
		/****************************************一2、MenuBar界面设计开始**************************************************/
		
		JMenu mnNewMenu = new JMenu("文件");
		menuBar.add(mnNewMenu);
		JMenuItem menuItem = new JMenuItem("打开");
		mnNewMenu.add(menuItem);
		JMenuItem menuItem_1 = new JMenuItem("关闭");
		mnNewMenu.add(menuItem_1);
		JMenuItem menuItem_2 = new JMenuItem("保存");
		mnNewMenu.add(menuItem_2);
		JMenuItem menuItem_9 = new JMenuItem("退出程序");
		mnNewMenu.add(menuItem_9);
		
		JMenu mnNewMenu_1 = new JMenu("仿真管理");
		menuBar.add(mnNewMenu_1);
		JMenuItem menuItem_3 = new JMenuItem("准备仿真");
		mnNewMenu_1.add(menuItem_3);
		JMenuItem menuItem_4 = new JMenuItem("结束仿真");
		mnNewMenu_1.add(menuItem_4);
		mntmNewMenuItem1 = new JMenuItem("修改仿真参数");
	    mntmNewMenuItem1.setEnabled(false);
	    mnNewMenu_1.add(mntmNewMenuItem1);
	    
	    JMenu mnNewMenu_3 = new JMenu("工具");
		menuBar.add(mnNewMenu_3);
		JMenuItem menuItem_8 = new JMenuItem("搜索");
		mnNewMenu_3.add(menuItem_8);
		
		JMenu mnNewMenu_4 = new JMenu("帮助");
		menuBar.add(mnNewMenu_4);
		mntmNewMenuItem2 = new JMenuItem("关于");
		mnNewMenu_4.add(mntmNewMenuItem2);
		
		/****************************************一2、MenuBar界面设计结束***************************************************/
		
	}
	
	public Terminal() {
		initialize();
	}
	
	public static void main(String[] args) {
		
		Terminal window = new Terminal();
		window.frame.setVisible(true);
		
	}

}
