package psm.Models.BusinessModel;

import psm.Models.DataModel.Member;
import psm.Models.DataModel.PictureOfMember;

public class FrmDeployModelManage {
	
	 PictureOfMember selectPom=LogicalStructureManage.temPOM;
	 
	 public Member DoGetSelectMember()
     {
         Member memberTemp = new Member();
         for(Member member : FormulaManage.getFormula().getMemberList())
         {
        	//����Ŀǰ�Ľ��������ͨ����ȡѡ�е�ͼƬ���Ƿ���ѡ�еĳ�Ա��һ���������жϣ���ԭ���ķ�����ͼƬLogicalIsActive�����������ж�
             //if (member.pictureOfMember.logicalIsActive)
        	 if(member.Name.equals(selectPom.Name))
             {
                 memberTemp = member;
                 break;
             }
             else
                 memberTemp = null;
         }
         return memberTemp; 
         
     }

}
