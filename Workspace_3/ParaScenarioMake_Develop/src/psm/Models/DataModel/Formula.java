package psm.Models.DataModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Formula implements Serializable 
 {
	//[start] Properties
	public String Author; // 方案作者
	public String Goal;// 方案目的
	public String Id;
	public String Name;// 方案名称
	public String Outline;// 方案概述
	public String Scale;// 方案规模
	
	
	
	//[start] Fields
    private  List<Member> MemberList;
   // private List<Line>  LineList ; 
    
    public List<MemberObject> MemberObjectList; 
    public  List<MemberInteraction> MemberInteractionList;
    //[end]
    /**
     * 设置成员属性
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
     * 成员对象类属性
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
     * 成员交互类属性
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
