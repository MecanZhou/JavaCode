package psm.Views;

import java.awt.EventQueue;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JCheckBox;

import psm.Models.BusinessModel.CombineMemberManage;
import psm.Models.BusinessModel.FrmParaChooseManage;
import psm.Models.BusinessModel.LogicalStructureManage;
import psm.Models.DataModel.CombineMember;
import psm.Models.DataModel.Line;
import psm.Models.DataModel.PictureOfMember;

import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;
import javax.swing.JSplitPane;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class FrmParaChoose {

	public JFrame frame;
	FrmParaChooseManage frmParaChooseManage;
	FrmMain frmMain;
	JPanel outpanel;
	JPanel inpanel;
	JSplitPane splitPane;
	JCheckBox checkBox_out ;
	JCheckBox checkBox_in;

	boolean checkout=false;
	
	LogicalStructureManage l=new LogicalStructureManage();
    Point getxy;
    public void getPointXy(MouseEvent e){
		getxy=new Point();
		getxy=l.getPoint(e);
	}
	/**
	 * Create the application.
	 */
	public FrmParaChoose() {
		initialize();
		frmParaChooseManage=new FrmParaChooseManage();
		FrmParaChoose_Load();
		
	}
	
	public void FrmParaChoose_Load(){
		Line line = frmParaChooseManage.DoGetSelectLine();
		if (line != null) {
//			cBInput.setSelected(line.lineAttribute.isInput);
//			cBOutput.setSelected(line.lineAttribute.isOutput);
//			cBInit.setSelected(line.lineAttribute.isInit);
//			cBModel.setSelected(line.lineAttribute.isModel);
		}
		//如果公布方为组合成员
		for(PictureOfMember pom:LogicalStructureManage.pomManage.pOMList){
			for(int m=4;m<8;m++){
				//则将组合成员中的outputmember显示出来
				if(pom.LogicalRect[m].contains(line.StartPoint.getX()+2,line.StartPoint.getY()+2)){
					for(CombineMember combineMember:CombineMemberManage.getCombineMemberList()){
						String pomName=null;
						String[] tmp=pom.Name.split("-"); 	
						for(int i=0;i<tmp.length;i++){
							pomName=tmp[1];
						}
						if(combineMember.Name.equals(pomName)){
							for(int i=0;i<combineMember.getOutputRelationList().size();i++){
								checkBox_out = new JCheckBox(combineMember.getOutputRelationList().get(i).Name);								
								checkBox_out.setBounds(20, 20+i*20, 103, 23);
								checkBox_out.setText(combineMember.getOutputRelationList().get(i).Name);
								checkBox_out.setSelected(combineMember.getOutputRelationList().get(i).IsPublish);
								outpanel.add(checkBox_out);
							}
							
						}else{
							splitPane.setDividerLocation(0);
						}
							
					}
				}
			
				//将组合成员中的inputmember显示出来
				if(pom.LogicalRect[m].contains(line.EndPoint.getX()+2,line.EndPoint.getY()+2)){
					for(final CombineMember combineMember:CombineMemberManage.getCombineMemberList()){
						String pomName=null;
						String[] tmp=pom.Name.split("-"); 	
						for(int i=0;i<tmp.length;i++){
							pomName=tmp[1];
						}
						if(combineMember.Name.equals(pomName)){
							for(int i=0;i<combineMember.getInputRelationList().size();i++){
								checkBox_in = new JCheckBox(combineMember.getInputRelationList().get(i).Name);
								checkBox_in.addItemListener(new ItemListener() {
									public void itemStateChanged(ItemEvent e) {
										if(checkBox_in.isSelected()){
											checkout=true;

											System.out.println("---"+checkout);
										}
									
									}
								});
								checkBox_in.setBounds(20, 20+i*20, 103, 23);
								checkBox_in.setText(combineMember.getInputRelationList().get(i).Name);
								checkBox_in.setSelected(combineMember.getInputRelationList().get(i).IsPublish);
								
								combineMember.getInputRelationList().get(i).IsPublish=checkout;
								inpanel.add(checkBox_in);
							}
						}else{
							splitPane.setDividerLocation(340);
						}
					}
				}
			
			}
		}
	
		
	
	}
	
	public void FrmParaChoose_Closing(){	
		Line line = frmParaChooseManage.DoGetSelectLine();
		if (line != null) {
			
//			if (cBInput.isSelected()) {
//				line.lineAttribute.isInput = true;
//			} else {
//				line.lineAttribute.isInput = false;
//			}
//			if (cBOutput.isSelected()) {
//				line.lineAttribute.isOutput = true;
//			} else {
//				line.lineAttribute.isOutput = false;
//			}
//			if (cBInit.isSelected()) {
//				line.lineAttribute.isInit = true;
//			} else {
//				line.lineAttribute.isInit = false;
//			}
//			if (cBModel.isSelected()) {
//				line.lineAttribute.isModel = true;
//			} else {
//				line.lineAttribute.isModel = false;
//			}
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				FrmParaChoose_Closing();
			}
		});
		frame.setBounds(100, 100, 168, 341);
		frame.setLocationRelativeTo(null);
		frame.validate();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		splitPane = new JSplitPane();
		splitPane.setDividerLocation(155);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		outpanel = new JPanel();
		outpanel.setBorder(new TitledBorder(null, "\u9009\u62E9\u8F93\u51FA\u6210\u5458", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		outpanel.setLayout(null);
		splitPane.setLeftComponent(outpanel);
		
		inpanel = new JPanel();
		inpanel.setBorder(new TitledBorder(null, "\u9009\u62E9\u8F93\u5165\u6210\u5458", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		inpanel.setLayout(null);
		splitPane.setRightComponent(inpanel);
	}
}
