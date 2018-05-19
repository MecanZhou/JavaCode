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
        	//这里目前的解决方案是通过获取选中的图片名是否与选中的成员名一致来进行判断，而原来的方法是图片LogicalIsActive属性来进行判断
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
