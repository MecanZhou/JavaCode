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
	 * 检查组合模型名称是否存在
	 * @param combineMemberName 命名
	 * @return 是否存在
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
	 * 检查成员组合的时候是否有订购关系
	 * @return
	 */
	private int CheckLine(){
		int i = 0,w=0;
		List<PictureOfMember> tempPomList = new ArrayList<PictureOfMember>();
		//获取选中的成员数
		for (PictureOfMember pom : LogicalStructureManage.pomManage.pOMList) {
			if (pom.logicalIsActive) {
				tempPomList.add(pom);
			}
		}
		List<Line> tempLineList = new ArrayList<Line>();
		//获取选中的线
		for (Line line : LogicalStructureManage.lineManage.logicalLineList) {
			if (line.IsActived) {
				tempLineList.add(line);
			}
		}
		//判断选中的 线是否都是被订购的线，如果没有订购，则不能进行成员组合
		for (int j = 0; j < tempLineList.size(); j++){
			if(tempLineList.get(j).Point2==null){
				i=1;
			}
		}
		//判断哪个成员并没有与其他成员之间有交互
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
	 *检查是否是对整个方案进行组合 
	 * @return
	 */
	
	private int CheckHaveFedTimee(){
		int w=0;
		List<PictureOfMember> tempPomList = new ArrayList<PictureOfMember>();
		//获取选中的成员数
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
		//获取选中的成员数
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
	 * 保存组合成员的信息
	 * @param combineMember
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings("static-access")
	private void SaveCombineMemberInfo(String combineMemberName) throws ClassNotFoundException, IOException{
		List<PictureOfMember> tempPomList = new ArrayList<PictureOfMember>();     //记录选中的成员
		List<Line> tempLineList = new ArrayList<Line>();                          //记录未被选中的线
		List<Line> tempEndLineList = new ArrayList<Line>();                       //记录EndPoint与组合成员相连的线
		List<Line> tempStartLineList = new ArrayList<Line>();                     //记录StartPoint与组合成员相连的线
		
		
		//获取选中的成员
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
	
		//获取没有选中的线
		for (Line line : LogicalStructureManage.lineManage.logicalLineList) {
					if (!line.IsActived) {
						tempLineList.add(line);
					}
				}	
		
		//获取组合成员中的输入成员和输出成员
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
								System.out.println("输入成员个数："+combineTempMember.getInputRelationList().size());
							}
						}							
					}
					if(tempPomList.get(j).LogicalRect[m].contains(tempLineList.get(i).StartPoint.getX()+2,tempLineList.get(i).StartPoint.getY()+2)){
						tempStartLineList.add(tempLineList.get(i));
						for(Member member:memberManage.MemberList){
							if(tempPomList.get(j).equals(member.pictureOfMember)){
								combineTempMember.outputMember=member;
								combineTempMember.getOutputRelationList().add(combineTempMember.outputMember);
								System.out.println("输出成员个数"+combineTempMember.getOutputRelationList().size())
								;
							}
						}							
					}
				}			
			}		
		}
		//获取组合成员的input_para_info
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
		//获取组合成员的output_para_info
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
								
		
		//记录组合成员显示的位置point
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
		//遍历组合成员中的所有成员
		for(int a=0;a<combineMember.getMemberList().size();a++){
			//第二次遍历组合成员中的所有成员
			for(int b=0;b<combineMember.getMemberList().size();b++){
				//判断第一次遍历的成员中是否为公布者
				if(combineMember.getMemberList().get(a).getSendToId().size()>0){
					//遍历该公布者公布的所有成员
					for(int c=0;c<combineMember.getMemberList().get(a).getSendToId().size();c++){

						String unId=null;
						String[] tmp=combineMember.getMemberList().get(a).getSendToId().get(c).split("-"); 	
						for(int i=0;i<tmp.length;i++){
							unId=tmp[1];
						}
						
						MemberObject memberObject=new MemberObject();
						//订购公布者的成员是否匹配第二次循环的成员
						if(unId.equals(combineMember.getMemberList().get(b).UniqueId)){
							//获取关系的公布方信息
							memberObject.producer=combineMember.getMemberList().get(a);
							memberObject.producerName=combineMember.getMemberList().get(a).Name;
							memberObject.producerId=Integer.valueOf(combineMember.getMemberList().get(a).Id);
						    //获取订购方的信息
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
	 *检查组合成员是否有参数 
	 */
	public int CheckCombinMemberHavaPara(int i){
		checkcount=i;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
		return checkcount;
	}
	/**
	 * 当组合模型配置界面需要保存的时候调用
	 * @param combineMember 需要处理的组合模型
	 * @param logicalStructureManage 逻辑结构管理模块
	 * @param combineMemberName 组合成员的名称
	 * @param combinMemberOutline 组合成员的概述
	 * @return 执行的结果（返回：2【出现重名】，0【正常执行】，1【异常】）
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public int DoSave_Click(CombineMember combineMember,
			LogicalStructureManage logicalStructureManage,
			String combineMemberName, String combinMemberOutline) throws ClassNotFoundException, IOException {
		try {
			if (CheckName(combineMemberName) > 0)// 检查是否重名
			{
				return 2;
			}
			if(CheckLine()>0){                   //检查是否有订购关系
				return 3;
			}
			if(CheckFormula()>0){                //检查是否是对整个方案进行组合
				return 4;
			}
			if(CheckHaveFedTimee()>0){
				return 6;
			}
			// 记录组合成员需要的基本信息
			SaveCombineMemberInfo(combineMemberName);
			
			//检查组合成员是否有没有输入输出端口，避免这样的组合成员出现
			if(CheckCombinMemberHavaPara(checkcount)==1){   
				return 5;
			}
			
			combineMember=combineTempMember;

			
			// 删除工作区中需要组合的基本成员
			logicalStructureManage.DoDel_Click();
			
			// 对组合成员进行处理。包括命名、写介绍、指定图标、重写model
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
			
			// 添加新建的组合成员
			 memberManage.MemberList.add(combineMember);
			 LogicalStructureManage.pomManage.GetPort(combineMember.pictureOfMember);
			 LogicalStructureManage.pomManage.pOMList.add(combineMember.pictureOfMember);
			 DragAndDropDropTargetListener._jp_panel.add(combineMember.pictureOfMember.imageLabel);
			 						 			 
			// 将组合成员信息记录到xml文件
			CombineMember combineMemberTemp = CombineMemberManage.Clone(combineMember);
			SaveRelationToFormula(combineMemberTemp);//添加线
			combineMemberTemp.Name = combineMemberName;
			CombineMemberManage.SaveCombineMember(combineMemberTemp);
			return 0;
		} 
		catch(Exception e) {
			return 1;
		}
	}

}
