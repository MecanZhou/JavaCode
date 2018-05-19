import hla.rti.ConcurrentAccessAttempted;
import hla.rti.FederateNotExecutionMember;
import hla.rti.InteractionClassNotDefined;
import hla.rti.InteractionClassNotPublished;
import hla.rti.InteractionParameterNotDefined;
import hla.rti.InvalidFederationTime;
import hla.rti.RTIinternalError;
import hla.rti.RestoreInProgress;
import hla.rti.SaveInProgress;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.mysql.jdbc.PreparedStatement;
import java.awt.event.MouseAdapter;


public class ControlTerminal implements Runnable{

	public static String scheme_id;
	public static boolean modifyFlag=false;
	public static JMenuItem mntmNewMenuItem_1;
	
	FrameResponse response=new FrameResponse();
	
	static String str;
	private static JFrame frame;
	public static JTextArea _textAreaHandler;
	public static DefaultMutableTreeNode _nodeHandler;// 根节点句柄
	public static JTree _treeHandler;
	public static JPanel _panelHandler;
	public static JPanel _viuwPanel;
	public static JScrollPane _scrollpaneHandler;
	public static JTabbedPane _tablepanelHandler;
	public static JScrollPane _tableDistribute;
	public static JScrollPane _runMathineInfo;
	public static JScrollPane _viewInfo;
	public static JTable _tableHandler;
	public static JTable _MemberDestribute;
	public static JMenuItem _scheme1;
	public static JMenuItem _scheme2;
	public static JMenuItem _scheme3;
	public static ArrayList<JMenuItem> _scheme = new ArrayList<JMenuItem>();
	public static JMenuItem _startRTI;
	public static JMenuItem _endRTI;
	public static JMenu _RTIControl;
	public static JMenu _schemeMenu;
	public static JSplitPane _splitpane1;
	
	public static JSplitPane _splitpane2;
	public static JSplitPane _splitpane3;
	public static JSplitPane _splitpane4;
	public static JSplitPane _splitpane5;
	public static JSlider _slider;
	// public static String text;
	public static JLabel _schemename;
	public static JLabel _schemeID;
	public static ResultSet rs3;
//	public static ResultSetTableModel model;
	public static JComboBox<String> tableNames = new JComboBox<String>();
	public static DefaultMutableTreeNode ChoseNode = new DefaultMutableTreeNode();// 选择中的方案
	public static ArrayList<String> schemeList = new ArrayList<String>();
	public static DefaultMutableTreeNode TreeNode[] = new DefaultMutableTreeNode[100];// 树中所有的节点
	public static int CountofTreeNode = 0;// 统计节点的数目
	public static ArrayList<DefaultMutableTreeNode> treeNodeList = new ArrayList<DefaultMutableTreeNode>();// 树中每个方案的节点
	static Statement st;
	public static int TimePercentage;
	public static int TimeScale=1000;
	
	private static JTable table;
	private static JTable table_1;
	private static JTable runMathine;
	public static int MemberNum = 0;
	public static JFreeChart jf = null;
	public static ChartFrame cf = null;
	public static ChartPanel cp = null;
	public static ArrayList<DefaultMutableTreeNode> actionNode = new ArrayList<DefaultMutableTreeNode>();

	// HlaDataView view;
	//CTLinkRTI rti = new CTLinkRTI();

	/********* Flag *********/

	public static boolean RTIflag = true;
	public static boolean IsDistributeIP = false;	//Ip是否已分配
	public static boolean startflag = false;
	public static boolean pauAndcon = false;
	public static boolean isContinue = false;
	public static boolean endflag = false;
	public static boolean refreshflag = false;
	public static boolean multicastflag = false;
	public static boolean treenodeflag = false;
	public static boolean delayupdateflag=false;
	private JTextField timeScaleText;
	
	CTLinkRTI rti = new CTLinkRTI();
	
	public static void main(String[] args) {
		ControlTerminal ct = new ControlTerminal();
		new Thread(ct).start();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ControlTerminal window = new ControlTerminal();
					
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ControlTerminal() {
		initialize();
	}

	private void initialize() {
		frame=new JFrame();
		frame.setSize(1200, 800);
		frame.setBounds(0, 0, 1200, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("管控程序");
		frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent arg0) {
				_splitpane1.setDividerLocation(0.85);
				_splitpane2.setDividerLocation(0.73);
				_splitpane3.setDividerLocation(0.85);
				_splitpane4.setDividerLocation(0.625);
				_splitpane5.setDividerLocation(0.4);
			}
		});
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

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
		menuItem_9.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				// int i=JOptionPane.showConfirmDialog(null,
				// "是否退出系统","退出确认对话框",JOptionPane.YES_NO_CANCEL_OPTION);
				int i = JOptionPane.showConfirmDialog(null, "是否退出系统",
						"退出确认对话框", JOptionPane.YES_NO_CANCEL_OPTION, 3);
				if (i == 0)
					frame.dispose();
			}
		});// 对事件9进行事件处理，
		JMenu mnNewMenu_1 = new JMenu("仿真管理");
		menuBar.add(mnNewMenu_1);
		_RTIControl = mnNewMenu_1;
		JMenuItem menuItem_3 = new JMenuItem("准备仿真");
		mnNewMenu_1.add(menuItem_3);
		
		menuItem_3.addActionListener(new ActionListener() {
 
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (RTIflag) {

					///IsDistributeIP=true;	//IP分配以确定
					rti.rtiflag = true;
					startflag = true;
					RTIflag = false;

					System.out.println(_schemename.getText());
					HlaDataView.jfreechartflag = true;
					// test();
					rti.Startrun(_schemename.getText(), MemberNum);

					/*
					 * 向运行端成员发送多播
					 */
					MulticastSocket ms;
					InetAddress group;
					String str1 = null;
					try {
						group = InetAddress.getByName("228.5.6.7");
						try {
							ms = new MulticastSocket(6789);
							ms.joinGroup(group);// 加入多播组
							// String str="1";
							str = "172.16.73.60";
							// String str="172.16.73.162 172.16.73.126";

							DatagramPacket hi = new DatagramPacket(str.getBytes(), str.length(), group, 6789);
							
							System.out.println("发送多播......");
							ms.send(hi);
							System.out.println("发送多播确认......111");
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}// 指定端口后
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}//
					_textAreaHandler.append("可以点击“开始”按钮开始进行仿真\n");
				}
			}
		});
		_startRTI = menuItem_3;
		JMenuItem menuItem_4 = new JMenuItem("结束仿真");
		mnNewMenu_1.add(menuItem_4);
		
	   mntmNewMenuItem_1 = new JMenuItem("修改仿真参数");
	    mntmNewMenuItem_1.setEnabled(false);
	    
		
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					response.createTable(scheme_id);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
//				// TODO Auto-generated method stub
//				form=new ModifyParaForm();
//				try {
//					System.out.println(scheme_id+"   00000000000000000000000000000");
//					form.createTable(scheme_id);
//				} catch (ClassNotFoundException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				} catch (SQLException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_1);
		menuItem_4.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (endflag) {
					_textAreaHandler.append("停止仿真，退出并销毁联邦\n");
					rti.resignFederation();
					endflag = false;
					startflag = false;
					refreshflag = false;
					pauAndcon = false;
					isContinue = false;
					RTIflag = true;
				} else {
					_textAreaHandler.append("当前没有正在进行的仿真\n");
				}
			}
		});

//		JMenu mnNewMenu_2 = new JMenu("方案管理");
//		menuBar.add(mnNewMenu_2);
//		_schemeMenu = mnNewMenu_2;

		JMenu mnNewMenu_3 = new JMenu("工具");
		menuBar.add(mnNewMenu_3);

		JMenuItem menuItem_8 = new JMenuItem("\u641C\u7D22");
		mnNewMenu_3.add(menuItem_8);

		JMenu mnNewMenu_4 = new JMenu("帮助");
		menuBar.add(mnNewMenu_4);
		JMenuItem mntmNewMenuItem = new JMenuItem("关于");
		mnNewMenu_4.add(mntmNewMenuItem);
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				// JOptionPane.showMessageDialog(frame,
				// "版权为江汉大学数计学院\n计算机仿真小组所有");
				JOptionPane.showMessageDialog(frame, "版权为江汉大学数计学院\n计算机仿真小组所有",
						"关于", 2);
			}
		});

		TreeNode[CountofTreeNode] = new DefaultMutableTreeNode("方案选择");
		_nodeHandler = TreeNode[CountofTreeNode];
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane_6 = new JSplitPane();
		splitPane_6.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_6.setDividerLocation(15);
		splitPane_6.setEnabled(false);
		splitPane_6.setDividerSize(0);
		
//		SplitPaneUI ui=splitPane_6.getUI();
//		BasicSplitPaneUI ui2=(BasicSplitPaneUI) ui;
//		ui2.getDivider().setBorder(null);
//		((BasicSplitPaneUI)splitPane_6.getUI()).getDivider().setBorder(null);
//		splitPane_6.setBorder(null);
//		splitPane_6.getUI().getDividerLocation(splitPane_6).g
		frame.getContentPane().add(splitPane_6);

		JPanel panel = new JPanel();
		splitPane_6.setRightComponent(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerSize(0);
		panel.add(splitPane, BorderLayout.CENTER);
		splitPane.setDividerLocation(240);

		JSplitPane splitPane_1 = new JSplitPane();
		_splitpane1 = splitPane_1;
		splitPane_1.setDividerLocation(650);
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.setEnabled(false);
		splitPane_1.setDividerSize(0);
		splitPane.setLeftComponent(splitPane_1);
		

		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setDividerSize(0);
		_splitpane2 = splitPane_2;
		splitPane_2.setDividerLocation(700);
		splitPane_2.setEnabled(false);
		splitPane_2.setDividerSize(0);
		splitPane.setRightComponent(splitPane_2);

		JSplitPane splitPane_3 = new JSplitPane();
		_splitpane3 = splitPane_3;
		splitPane_3.setDividerLocation(600);
		splitPane_3.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_2.setLeftComponent(splitPane_3);

		JSplitPane splitPane_4 = new JSplitPane();
		_splitpane4 = splitPane_4;
		splitPane_4.setDividerLocation(450);
		splitPane_4.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_2.setRightComponent(splitPane_4);

		JSplitPane splitPane_5 = new JSplitPane();
		splitPane_5.setEnabled(false);
		splitPane_5.setDividerSize(0);
		_splitpane5 = splitPane_5;
		splitPane_5.setDividerLocation(150);
		splitPane_5.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_4.setRightComponent(splitPane_5);

		// JPanel panel=new JPanel();
		// panel.setLayout(null);
		// frame.getContentPane().add(panel);
		JPanel panel1 = new JPanel();
		splitPane_1.setRightComponent(panel1);
		// panel.add(panel1);
		panel1.setBounds(598, 450, 196, 86);
		panel1.setBorder(BorderFactory.createTitledBorder("控制面板"));
		panel1.setLayout(new FormLayout(
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

		// *******************控制面板的设计*******************************************
		JButton btnNewButton = new JButton(new ImageIcon("be.png"));
		// btnNewButton.setIcon(new ImageIcon("src\\image\\start.gif"));
		panel1.add(btnNewButton, "4, 2, fill, fill");
		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (startflag) {
					_textAreaHandler.append("仿真开始进行\n");
									
					startflag = false;
					endflag = true;
					pauAndcon = true;
					refreshflag = true;
					try {
						rti.start();
					} catch (InteractionClassNotDefined e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InteractionClassNotPublished e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InteractionParameterNotDefined e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (FederateNotExecutionMember e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SaveInProgress e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (RestoreInProgress e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (RTIinternalError e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ConcurrentAccessAttempted e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					_textAreaHandler.append("仿真已经开始！或没有点击开始仿真按钮!\n");
				}
			}
		});

		JButton btnNewButton_1 = new JButton(new ImageIcon("en.png"));
		panel1.add(btnNewButton_1, "2, 2, fill, fill");
		btnNewButton_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (endflag) {
					_textAreaHandler.append("停止仿真，退出并销毁联邦\n");

					rti.resignFederation();
					endflag = false;
					startflag = false;
					refreshflag = false;
					pauAndcon = false;
					isContinue = false;
					RTIflag = true;
				} else {
					_textAreaHandler.append("当前没有正在进行的仿真\n");
				}
			}
		});
		JButton btnNewButton_2 = new JButton();
		btnNewButton_2.setIcon(new ImageIcon("pa.png"));
		panel1.add(btnNewButton_2, "6, 2, fill, fill");
		btnNewButton_2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (pauAndcon) {
					if (isContinue) {

						isContinue = false;
						delayupdateflag=false;
						try {
							rti.restart();
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvalidFederationTime e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						_textAreaHandler.append("仿真继续\n");

					} else {
						_textAreaHandler.append("仿真暂停\n");
						isContinue = true;
						delayupdateflag=true;
						try {
							rti.pause();
						} catch (InvalidFederationTime e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}
			}
		});

		JButton btnNewButton_3 = new JButton();
		btnNewButton_3.setIcon(new ImageIcon("re.png"));
		panel1.add(btnNewButton_3, "8, 2, 3, 1, fill, fill");
		btnNewButton_3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (refreshflag) {

					_textAreaHandler.append("更新仿真数据\n");
					try {
						rti.refresh();
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		});
		Label label = new Label("开始");
		panel1.add(label, "4, 4, fill, top");
		Label label_1 = new Label("暂停/继续");
		panel1.add(label_1, "6, 4, 3, 1, fill, top");
		Label label_2 = new Label("停止");
		panel1.add(label_2, "2, 4, fill, top");
		Label label_3 = new Label("刷新");
		panel1.add(label_3, "10, 4, fill, top");
		JPanel panel3 = new JPanel();
		panel3.setLayout(null);
		splitPane_4.setLeftComponent(panel3);
		// panel.add(panel3);
		panel3.setBounds(632, 10, 162, 279);

		// ***************************方案选择的设计***************************
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 10, 167, 425);
		splitPane_1.setLeftComponent(scrollPane);
		// panel.add(scrollPane);
		scrollPane.setBorder(BorderFactory.createTitledBorder("方案选择"));
		// treeNodeList.add(TreeNode[CountofTreeNode]);
		JTree tree_1 = new JTree(TreeNode[CountofTreeNode]);
		scrollPane.setViewportView(tree_1);
		_treeHandler = tree_1;
		// CountofTreeNode=CountofTreeNode+1;
		tree_1.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);

		// ********************属性的设计************************
		panel3.setBorder(BorderFactory.createTitledBorder("方案属性"));

		JLabel lblid = new JLabel("方案ID");
		lblid.setBounds(10, 50, 93, 20);
		panel3.add(lblid);

		JLabel lblNewLabel = new JLabel("方案名称");
		lblNewLabel.setBounds(10, 100, 93, 15);
		panel3.add(lblNewLabel);

		JLabel label_4 = new JLabel("方案生产日期");
		label_4.setBounds(10, 150, 93, 20);
		panel3.add(label_4);

		JLabel lblNewLabel_1 = new JLabel("方案作者");
		lblNewLabel_1.setBounds(10, 193, 93, 20);
		panel3.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("方案路径");
		lblNewLabel_2.setBounds(10, 250, 93, 20);
		panel3.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("方案供应商");
		lblNewLabel_3.setBounds(10, 300, 93, 20);
		panel3.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("方案运行次数");
		lblNewLabel_4.setBounds(10, 350, 93, 20);
		panel3.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("仿真步长");
		lblNewLabel_5.setBounds(10, 400, 93, 20);
		panel3.add(lblNewLabel_5);

		JLabel label_5 = new JLabel("");
		label_5.setOpaque(true);
		label_5.setBackground(Color.WHITE);
		label_5.setBounds(129, 50, 100, 30);
		panel3.add(label_5);
		_schemeID = label_5;

		JLabel lblNewLabel_6 = new JLabel("");
		lblNewLabel_6.setOpaque(true);
		lblNewLabel_6.setBackground(Color.WHITE);
		lblNewLabel_6.setBounds(129, 100, 100, 30);
		panel3.add(lblNewLabel_6);
		_schemename = lblNewLabel_6;
		JLabel lblNewLabel_7 = new JLabel("");
		lblNewLabel_7.setOpaque(true);
		lblNewLabel_7.setBackground(Color.WHITE);
		lblNewLabel_7.setBounds(129, 200, 100, 30);
		panel3.add(lblNewLabel_7);

		JLabel lblNewLabel_8 = new JLabel("");
		lblNewLabel_8.setOpaque(true);
		lblNewLabel_8.setBackground(Color.WHITE);
		lblNewLabel_8.setBounds(129, 150, 100, 30);
		panel3.add(lblNewLabel_8);

		JLabel lblNewLabel_9 = new JLabel("");
		lblNewLabel_9.setOpaque(true);
		lblNewLabel_9.setBackground(Color.WHITE);
		lblNewLabel_9.setBounds(129, 250, 100, 30);
		panel3.add(lblNewLabel_9);

		JLabel lblNewLabel_10 = new JLabel("");
		lblNewLabel_10.setOpaque(true);
		lblNewLabel_10.setBackground(Color.WHITE);
		lblNewLabel_10.setBounds(129, 300, 100, 30);
		panel3.add(lblNewLabel_10);

		JLabel lblNewLabel_11 = new JLabel("");
		lblNewLabel_11.setOpaque(true);
		lblNewLabel_11.setBackground(Color.WHITE);
		lblNewLabel_11.setBounds(129, 350, 100, 30);
		panel3.add(lblNewLabel_11);

		JSpinner spinner = new JSpinner();
		spinner.setBounds(129, 397, 100, 30);
		panel3.add(spinner);

		JPanel panel_1 = new JPanel();
		// panel_1.setLayout(null);
		panel_1.setBounds(632, 299, 162, 110);
		splitPane_5.setLeftComponent(panel_1);
		// panel.add(panel_1);

		// panel_1.setBorder(BorderFactory.createRaisedBevelBorder());
		panel_1.setBorder(BorderFactory.createTitledBorder("仿真速度调整"));
		panel_1.setLayout(null);

		JSlider slider = new JSlider(0,10,0);
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setBounds(10, 21, 228, 48);
		
		_slider=slider;
		panel_1.add(slider);
		slider.setMajorTickSpacing(2);
		slider.setMinorTickSpacing(1);
		
		timeScaleText = new JTextField();
		timeScaleText.setText("1000");
		timeScaleText.setBounds(71, 82, 54, 21);
		panel_1.add(timeScaleText);
		timeScaleText.setColumns(10);
		
		JLabel label_8 = new JLabel("\u5355\u4F4D\u523B\u5EA6\uFF1A");
		label_8.setBounds(10, 85, 66, 15);
		panel_1.add(label_8);
		
		JLabel label_9 = new JLabel("\u6BEB\u79D2");
		label_9.setBounds(131, 85, 37, 15);
		panel_1.add(label_9);
		_slider.addChangeListener(new ChangeListener() {
			
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				if(delayupdateflag)
				{
					System.out.println("Slider changed~~~!!!!"+_slider.getValue());
					TimePercentage=_slider.getValue();
					TimeScale=Integer.valueOf(timeScaleText.getText().trim());
					rti.DelayUpdate(TimePercentage,TimeScale);
				}

//				CTLinkRTI.TimePercentage=TimePercentage;
//				System.out.println("-------------------CTLinkRTI.TimePercentage: "+CTLinkRTI.TimePercentage);
				
				
			}
		});
		

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(177, 4, 445, 425);
		splitPane_3.setLeftComponent(tabbedPane);
		// panel.add(tabbedPane);
		_tablepanelHandler = tabbedPane;

		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("成员信息", null, panel_3, null);
		_panelHandler = panel_3;
		panel_3.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_2 = new JScrollPane();
		_scrollpaneHandler = scrollPane_2;

		panel_3.add(scrollPane_2);
		// Object[]
		// Title={"SCHEME_ID","SCHEME_MODEL_ID","MUID","MODEL_NAME","COM_NAME"};
		// Object[][] test={new
		// Object[]{"1","2","3","4","5"},{"11","22","33","44","55"}};
		// JTable a=new JTable(test, Title);
		// // _scrollpaneHandler.add(a);
		// panel_3.add(a);

		JPanel panel_4 = new JPanel();

		tabbedPane.addTab("运行机信息", null, panel_4, null);
		panel_4.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane_4 = new JScrollPane();
		panel_4.add(scrollPane_4);
		_runMathineInfo = scrollPane_4;

		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("成员参数信息", null, panel_2, null);
		_viuwPanel = panel_2;
		JScrollPane scrollPane_5 = new JScrollPane();
		panel_2.add(scrollPane_5);
		_viewInfo = scrollPane_5;
		// scrollPane_5.add(cf);

		JPanel panel_6 = new JPanel();
		tabbedPane.addTab("成员分配信息", null, panel_6, null);
		// System.out.println();
		JScrollPane scrollPane_3 = new JScrollPane();
		// scrollPane_3.setLayout(null);
		_tableDistribute = scrollPane_3;
		SpringLayout sl_panel_6 = new SpringLayout();
		sl_panel_6.putConstraint(SpringLayout.NORTH, scrollPane_3, 10, SpringLayout.NORTH, panel_6);
		sl_panel_6.putConstraint(SpringLayout.WEST, scrollPane_3, 0, SpringLayout.WEST, panel_6);
		sl_panel_6.putConstraint(SpringLayout.SOUTH, scrollPane_3, -66, SpringLayout.SOUTH, panel_6);
		sl_panel_6.putConstraint(SpringLayout.EAST, scrollPane_3, 0,SpringLayout.EAST, panel_6);
		
		panel_6.setLayout(sl_panel_6);
		panel_6.add(scrollPane_3);

		JLabel lblNewLabel_12 = new JLabel("New label");
		sl_panel_6.putConstraint(SpringLayout.EAST, lblNewLabel_12, -93, SpringLayout.EAST, panel_6);
		sl_panel_6.putConstraint(SpringLayout.SOUTH, lblNewLabel_12, -26, SpringLayout.SOUTH, panel_6);
		
		lblNewLabel_12.setText("提示： 点击IP选择框，会出现选择下拉框,选中对应IP即可");
		panel_6.add(lblNewLabel_12);
		
		/*JButton button_IP = new JButton("\u5206\u914DIP");
		sl_panel_6.putConstraint(SpringLayout.NORTH, button_IP, -4, SpringLayout.NORTH, lblNewLabel_12);
		sl_panel_6.putConstraint(SpringLayout.WEST, button_IP, 29, SpringLayout.WEST, panel_6);
		button_IP.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			if(!IsDistributeIP)	//IP分配一旦确定，就不能再进行分配更改
			{
					Connection con1 =CTReadDB.getConnection();
				int column=table.getColumnCount();	//取得表格的列数
				
				try {
					String sql="truncate table Model_Destribute"; //"delete * from Model_Destribute";//删除表中所有数据行
					st=(Statement) con1.createStatement();
					st.executeUpdate(sql);
					for(int row=0;row<table.getRowCount();row++)
					{
						sql="insert into Model_Destribute values('"+table.getModel().getValueAt(row, column-5)+"','"+table.getModel().getValueAt(row, column-4)+"','"+table.getModel().getValueAt(row, column-3)+"','"+table.getModel().getValueAt(row, column-2).toString()+"','"
								+table.getModel().getValueAt(row, column-1).toString()+"');";
						st=(Statement)con1.createStatement();
						st.executeUpdate(sql);
					}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		});*/
		//button_IP.setIcon(new ImageIcon("Distribution_16.png"));
		//panel_6.add(button_IP);

		JPanel panel_5 = new JPanel();
		panel_5.setLayout(null);
		panel_5.setBounds(10, 429, 153, 90);
		splitPane_5.setRightComponent(panel_5);
		// panel.add(panel_5);
		panel_5.setBorder(BorderFactory.createTitledBorder("版本信息"));

		JLabel label_6 = new JLabel("版本：RTI1.3版");
		label_6.setBounds(10, 20, 142, 36);
		panel_5.add(label_6);
		JLabel label_7 = new JLabel("<html>版版权归属：<br/>江汉大学数计学院仿真实验室</html>");
		label_7.setBounds(10, 56, 142, 53);
		panel_5.add(label_7);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(182, 445, 409, 96);
		splitPane_3.setRightComponent(scrollPane_1);
		// panel.add(scrollPane_1);

		JTextArea textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		scrollPane_1.setViewportView(textArea);

		_textAreaHandler = textArea;

		JPanel panel_7 = new JPanel();
		splitPane_6.setLeftComponent(panel_7);
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void run() {
		// Connection con=getConnection();
		getMathineInfo();	//// 获取机器信息，放到运行机信息的pannel中
		schemeTransfer();  // 从数据库中获取方案信息
		PrinttoTable();// 将方案的成员信息显示在成员信息栏
		ChoseScheme();
		//createSchemeMenu();
		// test();
	}

	public ArrayList<DefaultMutableTreeNode> MemberChart(String nodename) {
		// Connection con=CTReadDB.getConnection();
		ArrayList<DefaultMutableTreeNode> a = new ArrayList<DefaultMutableTreeNode>();
		
		for (int i = 0; i < treeNodeList.size(); i++) {
			if (nodename.equals(treeNodeList.get(i).toString())) {

				DefaultMutableTreeNode node = treeNodeList.get(i);
				for (int j = 0; j < treeNodeList.get(i).getChildCount(); j++) {
					node = node.getNextNode();
					a.add(node);
				}
			}
		}

		return a;
	}

	public void DownloadScheme() {
	}// 下载方案

//	public void createSchemeMenu() {
//		for (int i = 0; i < _scheme.size(); i++) {
//			// System.out.println(_scheme.get(i).getText());
//			final String text = _scheme.get(i).getText();
//			_schemeMenu.add(_scheme.get(i));
//			JMenuItem item = _scheme.get(i);
//
//			_scheme.get(i).addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					Object[] option = { "确定", "取消" };
//					int response = JOptionPane.showOptionDialog(null, "是否选择方案"
//							+ text, "提示", JOptionPane.YES_NO_OPTION,
//							JOptionPane.WARNING_MESSAGE, null, option,
//							option[0]);
//					if (response == 0) {
//						actionNode.clear();
//						try {
//							CTLinkFTP ctLinkFTP = new CTLinkFTP();
//							ctLinkFTP.getConnection();
//						} catch (IOException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}
//
//						try {
//							CTCreateFed createfed = new CTCreateFed(text);
//						} catch (Exception ie) {
//							// TODO: handle exception
//						}
//						_textAreaHandler.append("已经选择了方案" + text + "\n");
//						treenodeflag = true;
//						HlaDataView.jfreechartflag = false;
//						actionNode = MemberChart(text);
//						_schemename.setText(text);
//						printSchemeID(text);
//						try {
//							distributeMember();
//						} catch (Exception ie) {
//							// TODO: handle exception
//						}
//
//					}
//					if (response == 1) {
//						// 连接FTP服务器
//
//					}
//				}
//			});
//		}
//	}

	public void ChoseScheme()// 选择方案功能
	{
		String chosenscheme = null;
		_treeHandler.addMouseListener(new MouseListener()// 双击方案事件
				{
					public void mouseClicked(MouseEvent arg0) {
						// TODO Auto-generated method stub
						DefaultMutableTreeNode node = (DefaultMutableTreeNode) _treeHandler
								.getLastSelectedPathComponent();
						if (treenodeflag) {
							String nodename = _schemename.getText();
							System.out.println("nodename: "+nodename);
							HlaDataView.series.clear();
							if (arg0.getClickCount() == 1) {
								//
								System.out.println(_treeHandler
										.getLastSelectedPathComponent()
										.toString());
								for (int t = 0; t < actionNode.size(); t++) {
									if (actionNode
											.get(t)
											.equals((DefaultMutableTreeNode) _treeHandler
													.getLastSelectedPathComponent())) {
										System.out.println("jfreechart flag ="
												+ HlaDataView.jfreechartflag);
										if (HlaDataView.jfreechartflag) {
											// _viewInfo.removeAll();
											CTLinkRTI.chartname = actionNode
													.get(t).toString();
											XYSeries s1 = new XYSeries("X");
											XYSeries s2 = new XYSeries("Y");
											XYSeries s3 = new XYSeries("Z");
											// HlaDataView.series.add(s);
											HlaDataView.series.add(s1);
											HlaDataView.series.add(s2);
											HlaDataView.series.add(s3);
											HlaDataView.nodeName = actionNode
													.get(t).toString();
											System.out.println("生成chart......");
											jf = new HlaDataView().Jfreechart;
											cp = new ChartPanel(jf);
											// _viewInfo.add(cf);
											_viewInfo.add(cp);
											// cf=new ChartFrame("仿真记录", jf);
											// cf.pack();
											// cf.setMaximizedBounds(null);
											// cf.setma
											cp.setVisible(true);
											_viewInfo.setViewportView(cp);
											System.out
													.println("执行成功chart......");
											// XYSeries s=new XYSeries(X)
											// HlaDataView.series.add(e)
										}
									}
								}
								// System.out.println("OK");

							}
						}

						if (arg0.getClickCount() == 2) {
							 String node0=node.toString().split("_")[0];
							System.out.println("node: "+node);
							System.out.println("treeNodeList: "+treeNodeList);
							for (int i = 0; i < treeNodeList.size(); i++) {
								if (node == treeNodeList.get(i)) {
									System.out.println("MMMMMMMMMMMMMMMM");
									Object[] option = { "确定", "取消" };
									int response = JOptionPane
											.showOptionDialog(
													null,
													"是否选择方案" + node,
													"提示",
													JOptionPane.YES_NO_OPTION,
													JOptionPane.WARNING_MESSAGE,
													null, option, option[0]);
									if (response == 0) {
										actionNode.clear();
										ChoseNode = node;
										modifyFlag=true;
										while(modifyFlag){
											mntmNewMenuItem_1.setEnabled(true);
											modifyFlag=false;
										}
										System.out.println("ChoseNode3333333333333333333333333333    :"+ChoseNode);
										//89
										try {
											CTLinkFTP ctLinkFTP = new CTLinkFTP();
											ctLinkFTP.getConnection();
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
										try {
											// ctLinkFTP = new CTLinkFTP();
											// String
											// FOMpath=ctLinkFTP.FOMDownload("FOM.xml");
											// String
											// FEDpath=ctLinkFTP.FEDDownload("chat.fed");
										} catch (Exception e) {
											// TODO: handle exception
										}
										try {
											CTCreateFed createfed = new CTCreateFed(node0);
										} catch (Exception e) {
											// TODO: handle exception
										}
										_textAreaHandler.append("已经选择了方案"
												+ node + "\n");
										treenodeflag = true;
										HlaDataView.jfreechartflag = false;
										actionNode = MemberChart(node0);
										_schemename.setText(node0);
										printSchemeID(node0);
										try {
											distributeMember();
										} catch (SQLException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

									}
									if (response == 1) {
										// 连接FTP服务器
System.out.println("ChoseNode4444444444444444444444444444444444444444444 :"+ChoseNode);
									}
								}
							}
						}
					}

					public void mouseEntered(java.awt.event.MouseEvent arg0) {
						// TODO Auto-generated method stub

					}

					public void mouseExited(java.awt.event.MouseEvent arg0) {
						// TODO Auto-generated method stub

					}

					public void mousePressed(java.awt.event.MouseEvent arg0) {
						// TODO Auto-generated method stub

					}

					public void mouseReleased(java.awt.event.MouseEvent arg0) {
						// TODO Auto-generated method stub

					}
				});
	}

	public void printSchemeID(String name)// 将已经选择的了的方案的ID显示到属性栏中
	{
		Connection con = CTReadDB.getConnection();
		try {
			String sql = "select SCHEME_ID from scheme_desc_info where SCHEME_NAME= "
					+ "'" + name + "'";
			System.out.println("sql   "+sql);
			st = (Statement) con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				System.out.println("rs.getString(1)   "+rs.getString(1));
				_schemeID.setText(rs.getString(1));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void PrinttoTable()// 将方案的成员信息显示在成员信息栏
	{
		_treeHandler.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent arg0) {
				// TODO Auto-generated method stub
				//n是鼠标选取的树节点名称
				DefaultMutableTreeNode n = (DefaultMutableTreeNode) _treeHandler
						.getLastSelectedPathComponent();
				int ifRoot=n.toString().indexOf("_");
				//ifRoot是判断鼠标选择的树节点为根节点还是子节点，如果是子节点 则进入while循环
				while(ifRoot!=-1){
				String taskID=n.toString().split("_")[1];//taskID是方案ID
				for (int i = 0; i < treeNodeList.size(); i++) {
					if (n == treeNodeList.get(i)) {
						JTable table;
						Object[] columObjects = { "SCHEME_ID",
								"SCHEME_MODEL_ID", "MUID", "MODEL_NAME",
								"COM_NAME" };
						Object[][] tabledata;
						Object[][] Data;
						int a = 0;
						Connection con = CTReadDB.getConnection();//con为连接数据库对象
							int num = 0;
								boolean flag = false;//判断表格中的数据是否被修改
								final int scheme;
								String sql = "select * from scheme_model_info where SCHEME_ID = "+ taskID;
								scheme_id=taskID;
								System.out.println("scheme_id   "+scheme_id);
								try {
								st = (Statement) con.createStatement();
								ResultSet rs2 = st.executeQuery(sql);
								while (rs2.next()) {//a是表格的行数，按照数据的条数定义表格的行数
									a++;
								}
								scheme = a;//赋值操作
								tabledata = new Object[scheme][5];//创建表格
								Data = tabledata;
								st = (Statement) con.createStatement();
								rs2 = st.executeQuery(sql);//执行sql语句，返回rs2对象
								while (rs2.next()) {//往表格中填入数据
									tabledata[num] = new Object[] {
											rs2.getString(1), rs2.getString(2),
											rs2.getString(3), rs2.getString(4),
											rs2.getString(5) };
									num++;
									flag = true;
								}
								if (flag) //flag为TRUE表示数据有修改，就修改至JTable
								{
									table = new JTable(Data, columObjects);
									table.getTableHeader()
											.setReorderingAllowed(false);
									_scrollpaneHandler.add(table);
									_scrollpaneHandler.setViewportView(table);
									// scrollPane.add(table);
									table.setEnabled(false);
									table.repaint();
									_scrollpaneHandler.repaint();
									// System.out.println("OK");
									_panelHandler.repaint();
								}
							
							_tablepanelHandler.repaint();
//						} catch (Exception e) {
//							// TODO: handle exception
//						}
								} catch (Exception e) {
									// TODO: handle exception
								}
								} else {
						_scrollpaneHandler.repaint();
						_panelHandler.repaint();
					}
				}
				System.out.println("66666");
                    break;
				}
			}});}
	

	public void distributeMember() throws SQLException// 显示成员分配信息，在表格中修改进行分配
	{
		Connection con = CTReadDB.getConnection();
		String scheme = _schemename.getText();
		String schemeid = _schemeID.getText();
		// System.out.println(scheme+schemeid);
		// System.out.println(_schemename.getText()+_schemeID.getText());

		String sql = null;
		try {
			sql = "create table Model_Destribute"
					+ "(SCHEME_ID varchar(10),SCHEME_NAME varchar(50),Model_ID varchar(10),Model_Name varchar(255),"
					+ "IP varchar(20) )";
			System.out.println("1");
			st=con.createStatement();
			System.out.println("2");
			st.executeQuery(sql);
			System.out.println("创建表Model_Destribute成功");
		} catch (Exception e) {
			// TODO: handle exception
			 System.out.println("表格已存在,可以直接");
		}
		sql = "delete from Model_Destribute";
		System.out.println("3");
		st = (Statement) con.createStatement();
		st.executeUpdate(sql);
		sql = "select MODEL_NAME,MUID from scheme_model_info where SCHEME_ID="
				+ _schemeID.getText();
		System.out.println("????????????   _schemeID   "+_schemeID.getText());
		System.out.println("4");
		st = (Statement) con.createStatement();
		System.out.println(sql);
		ResultSet rs2 = st.executeQuery(sql);
		System.out.println("5");
		ArrayList<String> nameList = new ArrayList<String>();
		ArrayList<String> modelList = new ArrayList<String>();
		while (rs2.next()) {
			nameList.add(rs2.getString(1));
			modelList.add(rs2.getString(2));
		}

		int i = 0;
		while (rs2.next()) {
			i++;
		}
		int number = 0;
		sql = "select MODEL_NAME,SCHEME_MODEL_ID from scheme_model_info where SCHEME_ID="
				+ schemeid;
		System.out.println("5");
		st = (Statement) con.createStatement();
		rs2 = st.executeQuery(sql);
		// int i=0;
		while (rs2.next()) {
			i++;
		}
		final int num = i;
		MemberNum = num;
		Object[][] tabledata = new Object[num][2];
		st = (Statement) con.createStatement();
		rs2 = st.executeQuery(sql);

		while (rs2.next()) {
			tabledata[number] = new Object[] { _schemeID.getText(),
					_schemename.getText(), rs2.getString(2), rs2.getString(1),
					"" };
			number++;
		}
		Object[] columObjects = { "SCHEME_ID", "SCHEME_NAME", "MODEL_ID",
				"MODEL_NAME", "IP" };
		table = new JTable(tabledata, columObjects);
		// ///
		sql = "select SERVER_IP from server_state ";
		System.out.println("6");
		st = (Statement) con.createStatement();
		System.out.println("00000");
		rs2 = st.executeQuery(sql);
		System.out.println("11111");
		JComboBox<String> cBox = new JComboBox<String>();
		cBox.addItem("");
		while (rs2.next()) {
			cBox.addItem(rs2.getString(1));
		}
		table.getColumnModel().getColumn(4)
				.setCellEditor(new DefaultCellEditor(cBox));
		
		table.getModel().addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent evt) {
				// TODO Auto-generated method stub
				System.out.println("AAAAAAAAAAAA!!!!!!!!!!!");
				Connection con = CTReadDB.getConnection();
				System.out.println("BBBBBBBBBBBBBB!!!!!!!!!");
				int row = evt.getFirstRow();
				int column = evt.getColumn();
				try {
					System.out.println("10");
//					String sql = "delete  from Model_Destribute where MODEL_NAME = '"
//							+ table.getModel().getValueAt(row, column - 1)
//									.toString() + "'";
					System.out.println("table.getModel().getRowCount()"+table.getModel().getRowCount());
					System.out.println("column:"+column);

					if(column==4){
					String sql = "insert into Model_Destribute values('"
							+ table.getModel().getValueAt(row, column - 4)
							+ "','"
							+ table.getModel().getValueAt(row, column - 3)
							+ "','"
							+ table.getModel().getValueAt(row, column - 2)
							+ "','"
							+ table.getModel().getValueAt(row, column - 1)
									.toString()
							+ "','"
							+ table.getModel().getValueAt(row, column)
									.toString() + "')";
					st = (Statement) con.createStatement();			
					
					st.executeUpdate(sql);
					}
					con.close();
				} catch (SQLException e) {
					System.out.println("插入数据￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥"+e.toString());
					e.printStackTrace();
				}
				// _textAreaHandler.append(table.getModel().getValueAt(row,
				// column).toString()+"\n");
				// _textAreaHandler.append(table.getModel().getValueAt(row,
				// column-1).toString()+"\n");
			}
		});
	
		_tableDistribute.add(table);
		_MemberDestribute = table;
		// table.layout()
		_tableDistribute.setViewportView(table);
		table.repaint();
		_tableDistribute.repaint();
		//

	}

	public void schemeTransfer()// 从数据库中获取方案信息,记录在树的节点中
	{
		Connection con = CTReadDB.getConnection();
		try {
			String sql = "select SCHEME_ID from scheme_desc_info";
			st = (Statement) con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			ArrayList<String> idArrayList = new ArrayList<String>();
			while (rs.next()) {
				idArrayList.add(rs.getString(1));
			//	System.out.println(idArrayList.toString());//////////////////////////
			}// 获取所有方案的id
			for (int i = 0; i < idArrayList.size(); i++)// 获取每个方案方案名
			{
				sql = "select SCHEME_NAME from scheme_desc_info where SCHEME_ID ="
						+ idArrayList.get(i);
				st = (Statement) con.createStatement();
				ResultSet rs2 = st.executeQuery(sql);
//				String schemeID=null;
				String name = null;// 获取方案名称的中间变量
				
//				ArrayList<String> taskID=new ArrayList<String>();
				
				while (rs2.next()) {
//					schemeID=rs2.getString(0);
					name = rs2.getString(1)+"_"+idArrayList.get(i);
					JMenuItem item = new JMenuItem(rs2.getString(1));
					_scheme.add(item);
				//	System.out.println(i);
					TreeNode[CountofTreeNode] = new DefaultMutableTreeNode(name);
				//	System.out.println(name);
				//	System.out.println(i);
					_nodeHandler.add(TreeNode[CountofTreeNode]);
					
					treeNodeList.add(TreeNode[CountofTreeNode]);// 记录方案的节点信息
					int nodenum = CountofTreeNode;// 记录方案节点在节点数组中的序号；
					CountofTreeNode++;
					
					// ******上面是找到了方案名称，将方案名称加入到树中，下面将方案成员找出并加入树中
					sql = "select MODEL_NAME from scheme_model_info where SCHEME_ID ="
							+ idArrayList.get(i);
					st = (Statement) con.createStatement();
					ResultSet rs3 = st.executeQuery(sql);
					String membername = null;
					
					while (rs3.next()) {
						membername = rs3.getString(1);
						TreeNode[CountofTreeNode] = new DefaultMutableTreeNode(
								membername);
						TreeNode[nodenum].add(TreeNode[CountofTreeNode]);
						//nodenum++;
						//System.out.println(membername);
					}

				}
				_treeHandler.repaint();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	// *************************************************************************************************************************
	public void getMathineInfo() { // 获取机器信息，放到运行机信息的pannel中
		Object[] columObjects = { "SCHEME_IP", "CPU_UR", "MEMORY_UR",
				"NETWORK_USAGE", "LOAD_STATE" };
		Object[][] tabledata;
		// tabledata=new Object[20][];
		Object[][] Data;
		int a = 0;
		Connection con = CTReadDB.getConnection();
		String sql = "select * from server_state";
		try {

			Statement st1 = (Statement) con.createStatement();
			ResultSet rs2 = st1.executeQuery(sql);
			while (rs2.next()) {
				a++;
			}
			tabledata = new Object[a][5];
			st1 = (Statement) con.createStatement();
			rs2 = st1.executeQuery(sql);
			int num = 0;
			while (rs2.next()) {
				// System.out.println("OK");
				tabledata[num] = new Object[] { rs2.getString(1),
						rs2.getString(2), rs2.getString(3), rs2.getString(4),
						rs2.getString(5) };
				num++;
			}

			runMathine = new JTable(tabledata, columObjects);
			runMathine.getTableHeader().setReorderingAllowed(false);
			_runMathineInfo.add(runMathine);
			_runMathineInfo.setViewportView(runMathine);
			table.setEnabled(false);
			table.repaint();
			runMathine.repaint();
			_runMathineInfo.repaint();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

//	public void test() {
//		for (int i = 0; i < HlaDataView.series.size(); i++) {
//			System.out.println(HlaDataView.series.get(i).getKey().toString());
//		}
//		System.out.println("HlaDataView" + HlaDataView.series.size());
//		for (int i = 0; i < 100; i++) {
//			System.out.println(i);
//			for (int j = 0; j < HlaDataView.series.size(); j++) {
//				if (i % 2 == 0) {
//					HlaDataView.series.get(j).add(i, 5 + 0.02 * i + j);
//					System.out.println(HlaDataView.series.get(j).toString());
//				} else {
//					HlaDataView.series.get(j).add(i, 10 - 0.02 * (i + j));
//
//				}
//				// chart
//				try {
//					Thread.sleep(200);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//	}
}

