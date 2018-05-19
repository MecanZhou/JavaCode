package psm.Controls;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import psm.Models.BusinessModel.CombineMemberManage;
import psm.Models.BusinessModel.DragAndDropDropTargetListener;
import psm.Models.BusinessModel.FileManage;
import psm.Models.BusinessModel.FormulaManage;
import psm.Models.BusinessModel.InterfaceFileManage;
import psm.Models.BusinessModel.InterfaceFormulaManage;
import psm.Models.BusinessModel.LogicalStructureManage;
import psm.Models.BusinessModel.PictureOfMemberManage;
import psm.Models.DataModel.CombineMember;
import psm.Models.DataModel.Formula;
import psm.Models.DataModel.PictureOfMember;
import psm.Views.FrmMain;


public class FrmMainManage implements InterfaceFrmMainManage {
	public Formula formula_;	
	InterfaceFileManage faceFileManage;
	LogicalStructureManage logicalStructureManage=new LogicalStructureManage();
	
	public ArrayList<CombineMember> combineMemberList;
	
	public ArrayList<CombineMember> getCombineMemberList() {
		return combineMemberList;
	}

	public void setCombineMemberList(ArrayList<CombineMember> combineMemberList) {
		this.combineMemberList = combineMemberList;
	}

	public FrmMainManage(){
		 formula_ = FormulaManage.formula;
		 combineMemberList=CombineMemberManage.getCombineMemberList();
		 
	}
	
	/**
	 * ����Ԥ����Ϣ
	 * @throws IOException 
	 */
	@SuppressWarnings("finally")
	public int DoSaveFormula(JFrame frame) throws IOException {
		try {
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogType(JFileChooser.SAVE_DIALOG);//����Ϊ���洰��
			FileNameExtensionFilter fnef = new FileNameExtensionFilter(".sce",
					"Ԥ���ļ�");
			chooser.addChoosableFileFilter(fnef);
			int option = chooser.showSaveDialog(frame);
			if (option == JFileChooser.APPROVE_OPTION)//�û������ȷ������ť
			{
				faceFileManage = new FileManage();
				faceFileManage.DoSaveAction(formula_, chooser.getSelectedFile()
						.getAbsolutePath());
				System.out.println(formula_.getMemberList().size());
			}
			return 1;
		} finally {
			return 0;
		}
	}
	/**
	 * ��Ԥ����Ϣ
	 */
	public int DoOpenFormula(JFrame frame) throws IOException, ClassNotFoundException{
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);//����Ϊ���洰��
		int option = chooser.showOpenDialog(frame);
		if (option == JFileChooser.APPROVE_OPTION)//�û������ȷ������ť
		{
			faceFileManage = new FileManage();
			Formula formulaTemp=faceFileManage.DoOpenAction(chooser.getSelectedFile().getAbsolutePath());
			
			if(formulaTemp!=null){
				FormulaManage.formula = formulaTemp;
				//ManageContainer.InitContainer();
				for(int i=0;i<FormulaManage.formula.getMemberList().size();i++){
					System.out.println("��Ա���֣�"+FormulaManage.formula.getMemberList().get(i).pictureOfMember.imageLabel.getLocation());
					
					LogicalStructureManage.pomManage.GetPort(FormulaManage.formula.getMemberList().get(i).pictureOfMember);
					LogicalStructureManage.pomManage.pOMList.add(FormulaManage.formula.getMemberList().get(i).pictureOfMember);
					System.out.println("pom�Ĵ�С"+LogicalStructureManage.pomManage.pOMList.size());
					//DragAndDropDropTargetListener._jp_panel.add(FormulaManage.formula.getMemberList().get(i).pictureOfMember.imageLabel);
				}
                return 1;
			}
			else{
				return 0;
			}
		}
		else if(option==JFileChooser.CANCEL_OPTION){
			return 2;
		}
		else{
			return 0;
		}
		
	}
	
	public int DoPublishFormula()
	{
		faceFileManage=new FileManage();
		faceFileManage.CreateFomXml();
		faceFileManage.CreateSomXml();
		
		return 1;
	}
    /**
     *�������Ա��ϵ�ʱ���ж�ѡ�г�Ա�ĸ������Ա���û���ʾ 
     * @return ѡ�еĳ�Ա����
     */
	public int OnBtnMemberCombine(){
		int selectMemberNum=0;
		for(PictureOfMember pom:LogicalStructureManage.pomManage.pOMList){
			
			if(pom.logicalIsActive){
				selectMemberNum++;
			}
		}
		return selectMemberNum;
	}

	@Override
	public int DoUploadScheme() {
		// TODO Auto-generated method stub
		faceFileManage=new FileManage();
		faceFileManage.UploadScheme(formula_.Name+"Fom.xml");    
		faceFileManage.UploadScheme(formula_.Name+"Som.xml");
		faceFileManage.UploadScheme(formula_.Name+"FED.fed");
		return 1;
	}

	//public
	
}
