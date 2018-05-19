package psm.Models.DataModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Formula implements Serializable 
 {
	//[start] Properties
	public String Author; // ��������
	public String Goal;// ����Ŀ��
	public String Id;
	public String Name;// ��������
	public String Outline;// ��������
	public String Scale;// ������ģ
	
	
	
	//[start] Fields
    private  List<Member> MemberList;
   // private List<Line>  LineList ; 
    
    public List<MemberObject> MemberObjectList; 
    public  List<MemberInteraction> MemberInteractionList;
    //[end]
    /**
     * ���ó�Ա����
     */
    public void setMemberList(List<Member> MemberList_)
    {
    	MemberList=MemberList_;
    }  
    
   
    public List<Member> getMemberList()
    {
    	return MemberList;
    }
    
    /**
     * ��Ա����������
     * @param MemberObjectList_
     */
    public void setMemberObjectList(List<MemberObject> MemberObjectList_)
    {
        MemberObjectList=MemberObjectList_;
    }
    public List<MemberObject> getMemberObjectList()
    {
    	return MemberObjectList;
    }
    
    /**
     * ��Ա����������
     * @param MemberInteractionList_
     */
    public void setMemberInteractionList(List<MemberInteraction> MemberInteractionList_)
    {
    	MemberInteractionList=MemberInteractionList_;
    }
    public List<MemberInteraction> getMemberInteractionList()
    {
    	return MemberInteractionList;
    }
	
	//[end]
	
	public Formula()
	{		
		MemberList=new ArrayList<Member>();
		MemberObjectList=new ArrayList<MemberObject>();
		MemberInteractionList=new ArrayList<MemberInteraction>();
	}

}
