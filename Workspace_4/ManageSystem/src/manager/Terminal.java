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
	 * ���ⴰ�ڴ���
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
		
		/****************************************һ1��splitPane������ƿ�ʼ************************************************/
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setDividerLocation(15);
		splitPane.setEnabled(false);
		splitPane.setDividerSize(0);
		frame.getContentPane().add(splitPane);
		
		/***********************************1.1��panel1������ƿ�ʼ*******************************************/
		
		JPanel panel1 = new JPanel();
		splitPane.setRightComponent(panel1);
		panel1.setLayout(new BorderLayout(0, 0));
		
		/******************************1.1.1��splitPane1������ƿ�ʼ**************************************/
		
		JSplitPane splitPane1 = new JSplitPane();
		splitPane1.setDividerSize(0);
		panel1.add(splitPane1, BorderLayout.CENTER);
		splitPane1.setDividerLocation(240);
		
		/*************************1.1.1.1��splitPane_1������ƿ�ʼ*********************************/
		
		JSplitPane splitPane_1 = new JSplitPane();
		_splitpane1 = splitPane_1;
		splitPane_1.setDividerLocation(650);
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.setEnabled(false);
		splitPane_1.setDividerSize(0);
		splitPane1.setLeftComponent(splitPane_1);
		
		/********************1.1.1.1.1��panel_1������ƿ�ʼ****************************/
		
		JPanel panel_1 = new JPanel();
		splitPane_1.setRightComponent(panel_1);;
		panel_1.setBounds(598, 450, 196, 86);
		panel_1.setBorder(BorderFactory.createTitledBorder("�������"));
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
		
		Label label = new Label("��ʼ");
		panel_1.add(label, "4, 4, fill, top");
		Label label_1 = new Label("��ͣ/����");
		panel_1.add(label_1, "6, 4, 3, 1, fill, top");
		Label label_2 = new Label("ֹͣ");
		panel_1.add(label_2, "2, 4, fill, top");
		Label label_3 = new Label("ˢ��");
		panel_1.add(label_3, "10, 4, fill, top");
		
		/********************1.1.1.1.1��panel_1������ƿ�ʼ****************************/
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 10, 167, 425);
		splitPane_1.setLeftComponent(scrollPane);
		scrollPane.setBorder(BorderFactory.createTitledBorder("����ѡ��"));
		
		/*************************1.1.1.1��splitPane_1������ƽ���*********************************/
		
		/*************************1.1.1.2��splitPane_2������ƿ�ʼ*********************************/
		
		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setDividerSize(0);
		_splitpane2 = splitPane_2;
		splitPane_2.setDividerLocation(700);
		splitPane_2.setEnabled(false);
		splitPane_2.setDividerSize(0);
		splitPane1.setRightComponent(splitPane_2);
		
		/********************1.1.1.2.1��splitPane_2_1������ƿ�ʼ****************************/
		
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
		tabbedPane.addTab("��Ա��Ϣ", null, panel_2, null);
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
		tabbedPane.addTab("���л���Ϣ", null, panel_3, null);
		panel_3.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_2 = new JScrollPane();
		panel_3.add(scrollPane_2);
		_runMathineInfo = scrollPane_2;
		
		JPanel panel_4 = new JPanel();
		tabbedPane.addTab("��Ա������Ϣ", null, panel_4, null);
		_viuwPanel = panel_4;
		JScrollPane scrollPane_3 = new JScrollPane();
		panel_4.add(scrollPane_3);
		_viewInfo = scrollPane_3;
		
		JPanel panel_5 = new JPanel();
		tabbedPane.addTab("��Ա������Ϣ", null, panel_5, null);
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
		lblNewLabel_12.setFont(new Font("΢���ź�", Font.PLAIN, 12));
		lblNewLabel_12.setText("��ʾ�� ���IPѡ��򣬻����ѡ��������,ѡ�ж�ӦIP����");
		panel_5.add(lblNewLabel_12);
		
		JScrollPane scrollPane_5 = new JScrollPane();
		scrollPane_5.setBounds(182, 445, 409, 96);
		splitPane_2_1.setRightComponent(scrollPane_5);
		JTextArea textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		scrollPane_5.setViewportView(textArea);
		_textAreaHandler = textArea;
		
		/********************1.1.1.2.1��splitPane_2_1������ƽ���****************************/
		
		/********************1.1.1.2.2��splitPane_2_2������ƿ�ʼ****************************/

		JSplitPane splitPane_2_2 = new JSplitPane();
		_splitpane4 = splitPane_2_2;
		splitPane_2_2.setDividerLocation(450);
		splitPane_2_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_2.setRightComponent(splitPane_2_2);
		
		/*****************1.1.1.2.2.1��splitPane_3������ƿ�ʼ***********************/
		
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
		panel_6.setBorder(BorderFactory.createTitledBorder("�����ٶȵ���"));
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
		panel_7.setBorder(BorderFactory.createTitledBorder("�汾��Ϣ"));

		JLabel label_6 = new JLabel("�汾��RTI1.3��");
		label_6.setFont(new Font("����", Font.PLAIN, 14));
		label_6.setBounds(10, 37, 142, 36);
		panel_7.add(label_6);
		JLabel label_7 = new JLabel("<html>\u7248\u6743\u5F52\u5C5E\uFF1A<br/>\u6C5F\u6C49\u5927\u5B66\u6570\u8BA1\u5B66\u9662\u4EFF\u771F\u5B9E\u9A8C\u5BA4</html>");
		label_7.setFont(new Font("΢���ź�", Font.PLAIN, 14));
		label_7.setBounds(10, 90, 195, 53);
		panel_7.add(label_7);
		
		/*****************1.1.1.2.2.1��splitPane_3������ƽ���***********************/
		
		/*****************1.1.1.2.2.2��panel_8������ƿ�ʼ***************************/
		
		JPanel panel_8 = new JPanel();
		panel_8.setLayout(null);
		splitPane_2_2.setLeftComponent(panel_8);
		panel_8.setBounds(632, 10, 162, 279);
		panel_8.setBorder(BorderFactory.createTitledBorder("��������"));

		JLabel lblid = new JLabel("����ID");
		lblid.setBounds(10, 50, 93, 20);
		panel_8.add(lblid);

		JLabel lblNewLabel = new JLabel("��������");
		lblNewLabel.setBounds(10, 100, 93, 15);
		panel_8.add(lblNewLabel);

		JLabel label_4 = new JLabel("������������");
		label_4.setBounds(10, 150, 93, 20);
		panel_8.add(label_4);

		JLabel lblNewLabel_1 = new JLabel("��������");
		lblNewLabel_1.setBounds(10, 193, 93, 20);
		panel_8.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("����·��");
		lblNewLabel_2.setBounds(10, 250, 93, 20);
		panel_8.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("������Ӧ��");
		lblNewLabel_3.setBounds(10, 300, 93, 20);
		panel_8.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("�������д���");
		lblNewLabel_4.setBounds(10, 350, 93, 20);
		panel_8.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("���沽��");
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
		
		/*****************1.1.1.2.2.2��panel_8������ƿ�ʼ***************************/
		
		/********************1.1.1.2.2��splitPane_2_2������ƽ���****************************/
		
		/*************************1.1.1.2��splitPane_2������ƽ���*********************************/
		
		/******************************1.1.1��splitPane1������ƽ���**************************************/
		
		/***********************************1.1��panel1������ƽ���*******************************************/
		
		JPanel panel2 = new JPanel();
		splitPane.setLeftComponent(panel2);
		
		/****************************************һ1��splitPane������ƽ���************************************************/
		
		/****************************************һ2��MenuBar������ƿ�ʼ**************************************************/
		
		JMenu mnNewMenu = new JMenu("�ļ�");
		menuBar.add(mnNewMenu);
		JMenuItem menuItem = new JMenuItem("��");
		mnNewMenu.add(menuItem);
		JMenuItem menuItem_1 = new JMenuItem("�ر�");
		mnNewMenu.add(menuItem_1);
		JMenuItem menuItem_2 = new JMenuItem("����");
		mnNewMenu.add(menuItem_2);
		JMenuItem menuItem_9 = new JMenuItem("�˳�����");
		mnNewMenu.add(menuItem_9);
		
		JMenu mnNewMenu_1 = new JMenu("�������");
		menuBar.add(mnNewMenu_1);
		JMenuItem menuItem_3 = new JMenuItem("׼������");
		mnNewMenu_1.add(menuItem_3);
		JMenuItem menuItem_4 = new JMenuItem("��������");
		mnNewMenu_1.add(menuItem_4);
		mntmNewMenuItem1 = new JMenuItem("�޸ķ������");
	    mntmNewMenuItem1.setEnabled(false);
	    mnNewMenu_1.add(mntmNewMenuItem1);
	    
	    JMenu mnNewMenu_3 = new JMenu("����");
		menuBar.add(mnNewMenu_3);
		JMenuItem menuItem_8 = new JMenuItem("����");
		mnNewMenu_3.add(menuItem_8);
		
		JMenu mnNewMenu_4 = new JMenu("����");
		menuBar.add(mnNewMenu_4);
		mntmNewMenuItem2 = new JMenuItem("����");
		mnNewMenu_4.add(mntmNewMenuItem2);
		
		/****************************************һ2��MenuBar������ƽ���***************************************************/
		
	}
	
	public Terminal() {
		initialize();
	}
	
	public static void main(String[] args) {
		
		Terminal window = new Terminal();
		window.frame.setVisible(true);
		
	}

}
