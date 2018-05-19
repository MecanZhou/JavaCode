package psm.Models.BusinessModel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import psm.Models.DataModel.MemberInteraction;
import psm.Models.DataModel.MemberObject;
import psm.Models.DataModel.MemberRelation;

public class RelationManage {
	public static MemberRelation Clone(MemberRelation memberRelation) throws IOException, ClassNotFoundException
    {
        MemberRelation newMemberRelation = new MemberRelation();
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(memberRelation);
		// 从流里读出来 反序列化
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bis);
		newMemberRelation = (MemberRelation) ois.readObject();
		oos.close();
		ois.close();

        return newMemberRelation;        
    }
	//克隆成员对象
	public static MemberObject Clone(MemberObject memberObject) throws IOException, ClassNotFoundException
    {
		MemberObject newMemberObject = new MemberObject();
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(memberObject);
		// 从流里读出来 反序列化
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bis);
		newMemberObject = (MemberObject) ois.readObject();
		oos.close();
		ois.close();

        return newMemberObject;        
    }
	public static MemberRelation CreateMemberRelation()
    {
        //以后在在此为“新建关系”附加业务规则
        return new MemberRelation();
    }
	public List<MemberInteraction> MemberInteractionList= new ArrayList<MemberInteraction>();
	
	public List<MemberObject> MemberObjecList = new ArrayList<MemberObject>();
	
	public static List<String> LocationList=new ArrayList<String>();//成员参数位置信息表
	
	public static List<String> GetLocationList()
    {
        return LocationList;
    }
	public RelationManage()
    {
		MemberObjecList = FormulaManage.formula.MemberObjectList;
		MemberInteractionList=FormulaManage.formula.MemberInteractionList;
    
    }
	
	public List<MemberInteraction> getMemberInteractionList()
    {
    	return MemberInteractionList;
    }
    public List<MemberObject> getMemberObjecList(){
		return MemberObjecList;
	}
	public void setMemberInteractionList(List<MemberInteraction> MemberInteractionList_)
    {
    	MemberInteractionList=MemberInteractionList_;
    }  
	public void setMemberObjecList(List<MemberObject> memberObjecList) {
		MemberObjecList = memberObjecList;
	}
	
	public void setMemberRelationList(List<MemberObject> MemberObjecList_){
		MemberObjecList=MemberObjecList_;
	}

}
