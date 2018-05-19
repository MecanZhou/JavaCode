package psm.Views;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTree;
import javax.swing.border.TitledBorder;

public class FrmShowCombineMemberStruct {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmShowCombineMemberStruct window = new FrmShowCombineMemberStruct();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FrmShowCombineMemberStruct() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(240, 248, 255));
		frame.setBounds(100, 100, 608, 391);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u53D1\u5E03\u8005\uFF1A");
		lblNewLabel.setBounds(39, 29, 54, 15);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u8BA2\u8D2D\u8005\uFF1A");
		lblNewLabel_1.setBounds(301, 29, 54, 15);
		frame.getContentPane().add(lblNewLabel_1);
		
		JTextArea textPublish = new JTextArea();
		textPublish.setBorder(BorderFactory.createLineBorder(Color.gray,1));
		textPublish.setBounds(84, 25, 115, 24);
		frame.getContentPane().add(textPublish);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(240, 248, 255));
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(22, 59, 547, 251);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JTree tree = new JTree();
		tree.setBounds(10, 10, 260, 231);
		panel.add(tree);
		
		JTree tree_1 = new JTree();
		tree_1.setBounds(275, 10, 260, 231);
		panel.add(tree_1);
		
		JButton btnSure = new JButton("\u786E\u5B9A");
		btnSure.setBounds(409, 320, 75, 23);
		frame.getContentPane().add(btnSure);
		
		JButton btnCancel = new JButton("\u53D6\u6D88");
		btnCancel.setBounds(494, 320, 75, 23);
		frame.getContentPane().add(btnCancel);
		
		JTextArea textOrder = new JTextArea();
		textOrder.setBorder(BorderFactory.createLineBorder(Color.gray,1));
		textOrder.setBounds(348, 26, 115, 24);
		frame.getContentPane().add(textOrder);
	}
}
