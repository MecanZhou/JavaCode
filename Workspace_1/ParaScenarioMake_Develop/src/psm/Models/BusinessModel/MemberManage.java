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
	//成员都放在唯一的Formula中，MemberList
	public MemberManage()
	{
		setMemberList(FormulaManage.getFormula().getMemberList());
	}
	//[end]
	
	/**
	 * 生成一个成员
	 * @param 初始化成员的模型
	 * @return 生成的模型
	 */
	public Member CreateMember(Model model)
	{
        Member member = new Member(model);
        member.IsCombineMember=false;
        member.Id = String.valueOf(MemberList.size()+ 1);
        member.UniqueId=String.valueOf(Math.round(Math.random()*10000000));
        member.Name = "M" + member.Id  + "-" + member.Model.Model_name;
        MemberList.add(member);
        //设置成员ID唯一性，如果成员的ID相同的话，会自动累计加1
        for(int i=0;i<MemberList.size();i++){
        	for(int j=MemberList.size()-1;j>-1;j--){
        		if(MemberList.get(i).Id.equals(MemberList.get(j).Id)&&i!=j){
        			MemberList.get(j).Id=String.valueOf(Integer.valueOf(MemberList.get(j).Id)+1);
        			MemberList.get(j).Name = "M" + member.Id  + "-" + member.Model.Model_name;
        		}
        	}        		        	
        }
        //通知成员数发生改变
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
        //设置成员ID唯一性，如果成员的ID相同的话，会自动累计加1
        for(int i=0;i<MemberList.size();i++){
        	for(int j=MemberList.size()-1;j>-1;j--){
        		if(MemberList.get(i).Id.equals(MemberList.get(j).Id)&&i!=j){
        			MemberList.get(j).Id=String.valueOf(Integer.valueOf(MemberList.get(j).Id)+1);
        			MemberList.get(j).Name = "CM" + combineMember.Id +"-"+ combineMember.Name;
        		}
        	}        		        	
        }
      
        //通知成员数发生改变
        if (MemberListChanged != null)
        {
            this.OnMemberListChanged();
        }
    }
	/**
	 * 提供一个克隆成员的方法
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
		// 从流里读出来 反序列化
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bis);
		newMember = (Member) ois.readObject();
		oos.close();
		ois.close();
        return newMember;
	}

	/**
	 * 引发成员数量变化事件
	 */
    public void OnMemberListChanged()
    {
        if(MemberListChanged!=null)
        {
        	//MemberListChangedEventHandler.MemberListChanged();
        }
    }
	
}
