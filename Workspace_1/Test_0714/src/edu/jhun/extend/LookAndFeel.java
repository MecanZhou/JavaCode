package edu.jhun.extend;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class LookAndFeel extends JFrame{
	
	//javaSwing观感切换测试程序
	private JButton btnTest;
	private JTextField jtfTest;
	private JComboBox cmbTest;
	private JScrollPane jsclPane;
	private JTextArea jTextArea;
	
	private JRadioButton jradioWin;
	private JRadioButton jradioMotif;
	private JRadioButton jradioNimbus;
	private JRadioButton jradioMetal;
	
	private JPanel jPanelControls;
	private JPanel jPanelRadio;
	private ButtonGroup bGroup;
	
	private static final String METAL = "javax.swing.plaf.metal.MetalLookAndFeel";
	private static final String	MOTIF = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
	private static final String	NIMBUS = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
	private static final String WINDOWS = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	
	public LookAndFeel() throws HeadlessException {
		
		btnTest = new JButton("测试");
		cmbTest = new JComboBox(new String[]{"1","2"});
		jtfTest = new JTextField("The TextField for Testing!");
		
		jTextArea= new JTextArea();
		jTextArea.setRows(100);
		jTextArea.setColumns(100);
		jsclPane= new JScrollPane(jTextArea);
		
		jradioMetal = new JRadioButton("Default",true);
		jradioWin = new JRadioButton("Windows");
		jradioMotif = new JRadioButton("Motif");
		jradioNimbus = new JRadioButton("Nimbus");
		
		bGroup = new ButtonGroup();
		jPanelRadio = new JPanel();
		addRadio(jradioMetal, LookAndFeel.METAL, this);
		addRadio(jradioMotif, LookAndFeel.MOTIF, this);
		addRadio(jradioNimbus, LookAndFeel.NIMBUS, this);
		addRadio(jradioWin, LookAndFeel.WINDOWS, this);
		
		jPanelControls = new JPanel();
		jPanelControls.add(btnTest);
		jPanelControls.add(jtfTest);
		jPanelControls.add(cmbTest);
		
		this.add(jPanelControls, BorderLayout.NORTH);
		this.add(jsclPane);
		this.add(jPanelRadio,BorderLayout.SOUTH);
		
		Toolkit kit= Toolkit.getDefaultToolkit();
		Dimension screenSize=kit.getScreenSize();
		int screenWidth=screenSize.width;
		int screenHeight= screenSize.height;
		Image img=kit.getImage("Imgs/qqBrowser.png");
		this.setTitle("LookAndFeel");
		this.setIconImage(img);		
		this.setSize(screenWidth*3/4, screenHeight*3/4);
		this.setLocationRelativeTo(null);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void addRadio(JRadioButton jrb, final String Inf, final Component cmp){
		//添加JRadioButton相关操作
		bGroup.add(jrb);
		jPanelRadio.add(jrb);
		jrb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					UIManager.setLookAndFeel(Inf);
					SwingUtilities.updateComponentTreeUI(cmp);
				} catch (ClassNotFoundException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				} catch (UnsupportedLookAndFeelException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}
		});
	}

	public static void main(String[] args) {
		
		new LookAndFeel();

	}
}
