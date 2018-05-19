package psm.Models.BusinessModel;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import psm.Controls.ManageContainer;
import psm.Models.DataModel.CombineMember;
import psm.Models.DataModel.Line;
import psm.Models.DataModel.Member;
import psm.Models.DataModel.MemberObject;
import psm.Models.DataModel.PictureOfMember;
import psm.Models.DataModel.ModelClass.Model_para_info;

public class FrmDeployCombineMemberManage {
	MemberManage memberManage=new MemberManage();
	RelationManage relationManage=new RelationManage();
	LogicalStructureManage logicalStructureManage=new LogicalStructureManage();
	CombineMember combineTempMember=new CombineMember();
	public int endX=0;
	public int endY=0;
	public int startX=0;
	public int startY=0;
	private int checkcount=0;

	public FrmDeployCombineMemberManage(){
		 memberManage = (MemberManage)ManageContainer.GetObject("MemberManage");
		 relationManage=(RelationManage)ManageContainer.GetObject("RelationManage");
	}
	/**
	 * ������ģ�������Ƿ����
	 * @param combineMemberName ����
	 * @return �Ƿ����
	 */
	private int CheckName(String combineMemberName) {
		int i = 0;
		for(CombineMember combineMemberTemp:CombineMemberManage.getCombineMemberList()){
			
			if(combineMemberTemp.Name.equals(combineMemberName)){
				
				i++;
			}
		}
		return i;

	}
	/**
	 * ����Ա��ϵ�ʱ���Ƿ��ж�����ϵ
	 * @return
	 */
	private int CheckLine(){
		int i = 0,w=0;
		List<PictureOfMember> tempPomList = new ArrayList<PictureOfMember>();
		//��ȡѡ�еĳ�Ա��
		for (PictureOfMember pom : LogicalStructureManage.pomManage.pOMList) {
			if (pom.logicalIsActive) {
				tempPomList.add(pom);
			}
		}
		List<Line> tempLineList = new ArrayList<Line>();
		//��ȡѡ�е���
		for (Line line : LogicalStructureManage.lineManage.logicalLineList) {
			if (line.IsActived) {
				tempLineList.add(line);
			}
		}
		//�ж�ѡ�е� ���Ƿ��Ǳ��������ߣ����û�ж��������ܽ��г�Ա���
		for (int j = 0; j < tempLineList.size(); j++){
			if(tempLineList.get(j).Point2==null){
				i=1;
			}
		}
		//�ж��ĸ���Ա��û����������Ա֮���н���
		for (int k = 0; k < tempPomList.size(); k++) {
			for (int m = 4; m < 8; m++) {
				for (int j = 0; j < tempLineList.size(); j++) {
					if (tempPomList.get(k).LogicalRect[m].contains(tempLineList.get(j).StartPoint.getX()+2,tempLineList.get(j).StartPoint.getY()+2)
							||tempPomList.get(k).LogicalRect[m].contains(tempLineList.get(j).EndPoint.getX()+2,tempLineList.get(j).EndPoint.getY()+2)) {
						w++;
					}
				}
			}
		}
		if(w<tempPomList.size()){
			i=1;
		}
		
		return i;
	}
	/**
	 *����Ƿ��Ƕ���������������� 
	 * @return
	 */
	
	private int CheckHaveFedTimee(){
		int w=0;
		List<PictureOfMember> tempPomList = new ArrayList<PictureOfMember>();
		//��ȡѡ�еĳ�Ա��
		for (PictureOfMember pom : LogicalStructureManage.pomManage.pOMList) {
			if (pom.logicalIsActive) {
				tempPomList.add(pom);
			}
		}
		for(int j=0;j<tempPomList.size();j++){
			String[] temp=tempPomList.get(j).Name.split("-");
			String pomName=temp[1];
			if(pomName.equals("FedrationTimer")){
				w++;
			}
		}
		return w;
	}
	private int CheckFormula(){
		int f=0;
		List<PictureOfMember> tempPomList = new ArrayList<PictureOfMember>();
		//��ȡѡ�еĳ�Ա��
		for (PictureOfMember pom : LogicalStructureManage.pomManage.pOMList) {
			if (pom.logicalIsActive) {
				tempPomList.add(pom);
			}
		}
		if(LogicalStructureManage.pomManage.pOMList.size()==tempPomList.size()){
			f=1;
		}
		return f;
	}
	/**
	 * ������ϳ�Ա����Ϣ
	 * @param combineMember
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings("static-access")
	private void SaveCombineMemberInfo(String combineMemberName) throws ClassNotFoundException, IOException{
		List<PictureOfMember> tempPomList = new ArrayList<PictureOfMember>();     //��¼ѡ�еĳ�Ա
		List<Line> tempLineList = new ArrayList<Line>();                          //��¼δ��ѡ�е���
		List<Line> tempEndLineList = new ArrayList<Line>();                       //��¼EndPoint����ϳ�Ա��������
		List<Line> tempStartLineList = new ArrayList<Line>();                     //��¼StartPoint����ϳ�Ա��������
		
		
		//��ȡѡ�еĳ�Ա
		for(PictureOfMember pom:LogicalStructureManage.pomManage.pOMList){
			if(pom.logicalIsActive){
				tempPomList.add(pom);
				for(Member member:memberManage.MemberList){
					if(pom.equals(member.pictureOfMember)){	
						combineTempMember.memberList.add(member);
					}
				}					
			}			
		}
	
		//��ȡû��ѡ�е���
		for (Line line : LogicalStructureManage.lineManage.logicalLineList) {
					if (!line.IsActived) {
						tempLineList.add(line);
					}
				}	
		
		//��ȡ��ϳ�Ա�е������Ա�������Ա
		for(int i=0;i<tempLineList.size();i++){
			for(int j=0;j<tempPomList.size();j++){
				for (int m = 4; m < 8; m++) {
					if(tempPomList.get(j).LogicalRect[m].contains(tempLineList.get(i).EndPoint.getX()+2,tempLineList.get(i).EndPoint.getY()+2)){
						tempEndLineList.add(tempLineList.get(i));
						for(Member member:memberManage.MemberList){
							if(tempPomList.get(j).equals(member.pictureOfMember)){
								combineTempMember.inputMember=member;
								combineTempMember.Model.Publish_time=member.Model.Publish_time;
								combineTempMember.getInputRelationList().add(combineTempMember.inputMember);
								System.out.println("�����Ա������"+combineTempMember.getInputRelationList().size());
							}
						}							
					}
					if(tempPomList.get(j).LogicalRect[m].contains(tempLineList.get(i).StartPoint.getX()+2,tempLineList.get(i).StartPoint.getY()+2)){
						tempStartLineList.add(tempLineList.get(i));
						for(Member member:memberManage.MemberList){
							if(tempPomList.get(j).equals(member.pictureOfMember)){
								combineTempMember.outputMember=member;
								combineTempMember.getOutputRelationList().add(combineTempMember.outputMember);
								System.out.println("�����Ա����"+combineTempMember.getOutputRelationList().size())
								;
							}
						}							
					}
				}			
			}		
		}
		//��ȡ��ϳ�Ա��input_para_info
		for(Member inputMember:combineTempMember.getInputRelationList()){
			if(inputMember.Model.Model_para_infoList.size()>0){
				for (int z=0;z<inputMember.Model.Model_para_infoList.size();z++) {
					Model_para_info inputpara =new Model_para_info();
					if(String.valueOf(inputMember.Model.Model_para_infoList.get(z).Para_type).equals("input_para")){
						inputpara=inputMember.Model.Model_para_infoList.get(z);
						combineTempMember.Model.Model_para_infoList.add(inputpara);
						}
					}
				}
			}
		//��ȡ��ϳ�Ա��output_para_info
		for(Member outputMember:combineTempMember.getOutputRelationList()){
			if(outputMember.Model.Model_para_infoList.size()>0){
				for (int z=0;z<outputMember.Model.Model_para_infoList.size();z++) {
					Model_para_info outputpara =new Model_para_info();
					if(String.valueOf(outputMember.Model.Model_para_infoList.get(z).Para_type).equals("output_para")){
						outputpara=outputMember.Model.Model_para_infoList.get(z);
						combineTempMember.Model.Model_para_infoList.add(outputpara);
						}
					}
				}
			}
								
		
		//��¼��ϳ�Ա��ʾ��λ��point
		if(combineTempMember.getInputRelationList().size()>0&&combineTempMember.getOutputRelationList().size()>0){
			endX=tempEndLineList.get(0).EndPoint.x;
			endY=tempEndLineList.get(0).EndPoint.y;					
			for(int i=0;i<tempEndLineList.size();i++){
				tempEndLineList.get(i).EndPoint=tempEndLineList.get(0).EndPoint;
			}
			for(int j=0;j<tempStartLineList.size();j++){
				tempStartLineList.get(j).StartPoint=new Point(endX+70,endY);
			}		
		}
		if(combineTempMember.getInputRelationList().size()>0&&combineTempMember.getOutputRelationList().size()==0){
			endX=tempEndLineList.get(0).EndPoint.x;
			endY=tempEndLineList.get(0).EndPoint.y;
			for(int i=0;i<tempEndLineList.size();i++){
				tempEndLineList.get(i).EndPoint=tempEndLineList.get(0).EndPoint;
			}
		}
		if(combineTempMember.getInputRelationList().size()==0&&combineTempMember.getOutputRelationList().size()>0){
			startX=tempStartLineList.get(0).StartPoint.x;
			startY=tempStartLineList.get(0).StartPoint.y;
			for(int j=0;j<tempStartLineList.size();j++){
				tempStartLineList.get(j).StartPoint=tempStartLineList.get(0).StartPoint;
			}
		}
		if(combineTempMember.getInputRelationList().size()==0&&combineTempMember.getOutputRelationList().size()==0){
			CheckCombinMemberHavaPara(1);
		}
	}

	private void SaveRelationToFormula(CombineMember combineMember){
		//������ϳ�Ա�е����г�Ա
		for(int a=0;a<combineMember.getMemberList().size();a++){
			//�ڶ��α�����ϳ�Ա�е����г�Ա
			for(int b=0;b<combineMember.getMemberList().size();b++){
				//�жϵ�һ�α����ĳ�Ա���Ƿ�Ϊ������
				if(combineMember.getMemberList().get(a).getSendToId().size()>0){
					//�����ù����߹��������г�Ա
					for(int c=0;c<combineMember.getMemberList().get(a).getSendToId().size();c++){

						String unId=null;
						String[] tmp=combineMember.getMemberList().get(a).getSendToId().get(c).split("-"); 	
						for(int i=0;i<tmp.length;i++){
							unId=tmp[1];
						}
						
						MemberObject memberObject=new MemberObject();
						//���������ߵĳ�Ա�Ƿ�ƥ��ڶ���ѭ���ĳ�Ա
						if(unId.equals(combineMember.getMemberList().get(b).UniqueId)){
							//��ȡ��ϵ�Ĺ�������Ϣ
							memberObject.producer=combineMember.getMemberList().get(a);
							memberObject.producerName=combineMember.getMemberList().get(a).Name;
							memberObject.producerId=Integer.valueOf(combineMember.getMemberList().get(a).Id);
						    //��ȡ����������Ϣ
							memberObject.consumer=combineMember.getMemberList().get(b);
							memberObject.consumerName=combineMember.getMemberList().get(b).Name;
							memberObject.consumerId=Integer.valueOf(combineMember.getMemberList().get(b).Id);
							
							combineMember.getMemberObjectList().add(memberObject);
					}
				}
				
			}
			}
		}

	}
	
	/**
	 *�����ϳ�Ա�Ƿ��в��� 
	 */
	public int CheckCombinMemberHavaPara(int i){
		checkcount=i;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
		return checkcount;
	}
	/**
	 * �����ģ�����ý�����Ҫ�����ʱ�����
	 * @param combineMember ��Ҫ��������ģ��
	 * @param logicalStructureManage �߼��ṹ����ģ��
	 * @param combineMemberName ��ϳ�Ա������
	 * @param combinMemberOutline ��ϳ�Ա�ĸ���
	 * @return ִ�еĽ�������أ�2��������������0������ִ�С���1���쳣����
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public int DoSave_Click(CombineMember combineMember,
			LogicalStructureManage logicalStructureManage,
			String combineMemberName, String combinMemberOutline) throws ClassNotFoundException, IOException {
		try {
			if (CheckName(combineMemberName) > 0)// ����Ƿ�����
			{
				return 2;
			}
			if(CheckLine()>0){                   //����Ƿ��ж�����ϵ
				return 3;
			}
			if(CheckFormula()>0){                //����Ƿ��Ƕ����������������
				return 4;
			}
			if(CheckHaveFedTimee()>0){
				return 6;
			}
			// ��¼��ϳ�Ա��Ҫ�Ļ�����Ϣ
			SaveCombineMemberInfo(combineMemberName);
			
			//�����ϳ�Ա�Ƿ���û����������˿ڣ�������������ϳ�Ա����
			if(CheckCombinMemberHavaPara(checkcount)==1){   
				return 5;
			}
			
			combineMember=combineTempMember;

			
			// ɾ������������Ҫ��ϵĻ�����Ա
			logicalStructureManage.DoDel_Click();
			
			// ����ϳ�Ա���д�������������д���ܡ�ָ��ͼ�ꡢ��дmodel
			combineMember.Id =String.valueOf(memberManage.MemberList.size() +1);
			combineMember.Name = "CM" + combineMember.Id + "-"+ combineMemberName;
			combineMember.outLine = combinMemberOutline;
			combineMember.pictureOfMember.Name = combineMember.Name;
			combineMember.pictureOfMember.imageLabel = new JLabel();
			combineMember.pictureOfMember.imageLabel.setIcon(new ImageIcon("src\\psm\\Image\\Control.png"));
			if(endX!=0){
			combineMember.pictureOfMember.imageLabel.setBounds(endX, endY-35,70, 70);
			}else{
				combineMember.pictureOfMember.imageLabel.setBounds(startX-70, startY-35,70, 70);
			}
			
			// ����½�����ϳ�Ա
			 memberManage.MemberList.add(combineMember);
			 LogicalStructureManage.pomManage.GetPort(combineMember.pictureOfMember);
			 LogicalStructureManage.pomManage.pOMList.add(combineMember.pictureOfMember);
			 DragAndDropDropTargetListener._jp_panel.add(combineMember.pictureOfMember.imageLabel);
			 						 			 
			// ����ϳ�Ա��Ϣ��¼��xml�ļ�
			CombineMember combineMemberTemp = CombineMemberManage.Clone(combineMember);
			SaveRelationToFormula(combineMemberTemp);//�����
			combineMemberTemp.Name = combineMemberName;
			CombineMemberManage.SaveCombineMember(combineMemberTemp);
			return 0;
		} 
		catch(Exception e) {
			return 1;
		}
	}

}
