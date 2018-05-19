package psm.Models.BusinessModel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import psm.Component.Delegator;
import psm.Models.DataModel.CombineMember;
import psm.Models.DataModel.Member;
import psm.Models.DataModel.Model;

public class MemberManage implements InterfaceMemberManage 
{
	 static class MemberListChangedEventHandler extends Delegator {
	}

	public MemberListChangedEventHandler MemberListChanged;
    //[start] Fields
	public static List<Member> MemberList=new ArrayList<Member>();
	//[end]
	
	//[start] Properties
	public void setMemberList(List<Member> MemberList_)
	{
		MemberList=MemberList_;
	}
	public List<Member> getMemberList()
	{
		return MemberList;
	}
	//[end]
	
	//[start] Constructors
	//��Ա������Ψһ��Formula�У�MemberList
	public MemberManage()
	{
		setMemberList(FormulaManage.getFormula().getMemberList());
	}
	//[end]
	
	/**
	 * ����һ����Ա
	 * @param ��ʼ����Ա��ģ��
	 * @return ���ɵ�ģ��
	 */
	public Member CreateMember(Model model)
	{
        Member member = new Member(model);
        member.IsCombineMember=false;
        member.Id = String.valueOf(MemberList.size()+ 1);
        member.UniqueId=String.valueOf(Math.round(Math.random()*10000000));
        member.Name = "M" + member.Id  + "-" + member.Model.Model_name;
        MemberList.add(member);
        //���ó�ԱIDΨһ�ԣ������Ա��ID��ͬ�Ļ������Զ��ۼƼ�1
        for(int i=0;i<MemberList.size();i++){
        	for(int j=MemberList.size()-1;j>-1;j--){
        		if(MemberList.get(i).Id.equals(MemberList.get(j).Id)&&i!=j){
        			MemberList.get(j).Id=String.valueOf(Integer.valueOf(MemberList.get(j).Id)+1);
        			MemberList.get(j).Name = "M" + member.Id  + "-" + member.Model.Model_name;
        		}
        	}        		        	
        }
        //֪ͨ��Ա�������ı�
        if (MemberListChanged != null)
        {
            this.OnMemberListChanged();
        }
        return member;
	}

	public void CreateMember(CombineMember combineMember)
    {
//        Member combineMember = new Member(model);
		combineMember.IsCombineMember=true;
        combineMember.Id = String.valueOf(MemberList.size() + 1);
        combineMember.Name = "CM" + combineMember.Id +"-"+ combineMember.Name;
        MemberList.add(combineMember);
        //���ó�ԱIDΨһ�ԣ������Ա��ID��ͬ�Ļ������Զ��ۼƼ�1
        for(int i=0;i<MemberList.size();i++){
        	for(int j=MemberList.size()-1;j>-1;j--){
        		if(MemberList.get(i).Id.equals(MemberList.get(j).Id)&&i!=j){
        			MemberList.get(j).Id=String.valueOf(Integer.valueOf(MemberList.get(j).Id)+1);
        			MemberList.get(j).Name = "CM" + combineMember.Id +"-"+ combineMember.Name;
        		}
        	}        		        	
        }
      
        //֪ͨ��Ա�������ı�
        if (MemberListChanged != null)
        {
            this.OnMemberListChanged();
        }
    }
	/**
	 * �ṩһ����¡��Ա�ķ���
	 * @param member
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Member Clone(Member member)throws IOException, ClassNotFoundException{
		Member newMember = new Member();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(member);
		// ����������� �����л�
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bis);
		newMember = (Member) ois.readObject();
		oos.close();
		ois.close();
        return newMember;
	}

	/**
	 * ������Ա�����仯�¼�
	 */
    public void OnMemberListChanged()
    {
        if(MemberListChanged!=null)
        {
        	//MemberListChangedEventHandler.MemberListChanged();
        }
    }
	
}
