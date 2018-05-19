package psm.Models.DataModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import psm.Models.BusinessModel.FormulaManage;

public class CombineMember extends Member implements Serializable{
	/**
	 * ��ϳ�Ա�Ķ���������ϳ�Ա�У�����Ա�����ⲿ��Աû��������ͨ�����䶨��Ϊ�ڲ���Ա
	 * �����Ա���ⲿ��Ա������������ó�Ա������Ϊ�߽��Ա
	 * �ڱ߽��Ա���ٽ��з��࣬�����ⲿ��Ա�������ϵ�ĳ�Ա���ⱻ����Ϊ�����Ա�����뵽�����Ա�б��У������ⲿ���������ϵ�ĳ�Ա����Ϊ�����Ա�����뵽�����Ա�б���
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

	private ArrayList<Member> borderMemberList;              //�߽��Ա�б�
    private ArrayList<Member> inputRelationList;             //�����Ա�б�  
    private ArrayList<Member> outputRelationList;            //�����Ա�б�
    public Member inputMember;                               //�����Ա 
    public Member outputMember;                              //�����Ա  
    public String outLine;                                   //��Ա�������
    
    
    
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
