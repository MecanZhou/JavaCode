package psm.Controls;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import psm.Models.BusinessModel.CombineMemberManage;
import psm.Models.BusinessModel.ModelManage;
import psm.Models.DB.ModelService;
import psm.Models.DataModel.CombineMember;
import psm.Models.DataModel.Model;

public class TrModelManage {
	
	 public static List<Model> DoGetModelsBasicInfo() throws SQLException
     {
         ModelManage modelManage = new ModelManage();
		 return modelManage.GetModelsAllInfo();
     }	
	 
	 /**
		 *  查询系统存在的组合成员基本信息
		 * @param combineMemberList
		 * @throws ClassNotFoundException
		 * @throws IOException
		 */
		 public void DoGetCombineMemberInfo(ArrayList<CombineMember> combineMemberList)
	     {
	         try {
				CombineMemberManage.GetCombineMemberInfo(combineMemberList);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}           
	     }
		 
		 public void DoDleteModelInfoNode(String MUID){
			 try {
				ModelService.DleteModelInfoNode(MUID);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		 }
		 public void DoAddModelPicture(String MUID,String Path){

		 }


}
