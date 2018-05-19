package psm.Models.DataModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import psm.Models.BusinessModel.FormulaManage;

public class CombineMember extends Member implements Serializable{
	/**
	 * 组合成员的定义是在组合成员中，若成员的与外部成员没有数据流通，则将其定义为内部成员
	 * 如果成员与外部成员有数据流向，则该成员被划分为边界成员
	 * 在边界成员中再进行分类，若与外部成员有输入关系的成员，这被定义为输入成员，加入到输入成员列表中；若与外部呈有输出关系的成员，则为输出成员，加入到输出成员列表中
	 * 
	 */
	public ArrayList<Member> memberList=new ArrayList<Member>();
	private static List<MemberObject> memberObjectList;
	private List<MemberInteraction> memberInteractionList;
    public List<MemberInteraction> getMemberInteractionList() {
		return memberInteractionList;
	}
	public void setMemberInteractionList(
			List<MemberInteraction> memberInteractionList) {
		this.memberInteractionList = memberInteractionList;
	}

	private ArrayList<Member> borderMemberList;              //边界成员列表
    private ArrayList<Member> inputRelationList;             //输入成员列表  
    private ArrayList<Member> outputRelationList;            //输出成员列表
    public Member inputMember;                               //输入成员 
    public Member outputMember;                              //输出成员  
    public String outLine;                                   //成员组合描述
    
    
    
	public String getOutLine() {
		return outLine;
	}
	public void setOutLine(String outLine) {
		this.outLine = outLine;
	}
	public ArrayList<Member> getMemberList() {
		return memberList;
	}
	public void setMemberList(ArrayList<Member> memberList) {
		this.memberList = memberList;
	}
	public List<MemberObject> getMemberObjectList() {
		return memberObjectList;
	}
	public void setMemberObjectList(List<MemberObject> memberObjectList) {
		this.memberObjectList = memberObjectList;
	}
	public ArrayList<Member> getBorderMemberList() {
		return borderMemberList;
	}
	public void setBorderMemberList(ArrayList<Member> borderMemberList) {
		this.borderMemberList = borderMemberList;
	}
	public ArrayList<Member> getInputRelationList() {
		return inputRelationList;
	}
	public void setInputRelationList(ArrayList<Member> inputRelationList) {
		this.inputRelationList = inputRelationList;
	}
	public ArrayList<Member> getOutputRelationList() {
		return outputRelationList;
	}
	public void setOutputRelationList(ArrayList<Member> outputRelationList) {
		this.outputRelationList = outputRelationList;
	}
	public Member getInputMember() {
		return inputMember;
	}
	public void setInputMember(Member inputMember) {
		this.inputMember = inputMember;
	}
	public Member getOutputMember() {
		return outputMember;
	}
	public void setOutputMember(Member outputMember) {
		this.outputMember = outputMember;
	}
	
	 public CombineMember()
     {
         memberList = new ArrayList<Member>();
         memberObjectList = new ArrayList<MemberObject>();
         memberInteractionList=new ArrayList<MemberInteraction>();
         borderMemberList = new ArrayList<Member>();
         inputRelationList = new ArrayList<Member>();
         outputRelationList = new ArrayList<Member>();
         this.IsCombineMember = true;
     }
	
	
	
    
    
	

}
