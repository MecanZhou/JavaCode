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
		// ����������� �����л�
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bis);
		newMemberRelation = (MemberRelation) ois.readObject();
		oos.close();
		ois.close();

        return newMemberRelation;        
    }
	//��¡��Ա����
	public static MemberObject Clone(MemberObject memberObject) throws IOException, ClassNotFoundException
    {
		MemberObject newMemberObject = new MemberObject();
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(memberObject);
		// ����������� �����л�
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bis);
		newMemberObject = (MemberObject) ois.readObject();
		oos.close();
		ois.close();

        return newMemberObject;        
    }
	public static MemberRelation CreateMemberRelation()
    {
        //�Ժ����ڴ�Ϊ���½���ϵ������ҵ�����
        return new MemberRelation();
    }
	public List<MemberInteraction> MemberInteractionList= new ArrayList<MemberInteraction>();
	
	public List<MemberObject> MemberObjecList = new ArrayList<MemberObject>();
	
	public static List<String> LocationList=new ArrayList<String>();//��Ա����λ����Ϣ��
	
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
