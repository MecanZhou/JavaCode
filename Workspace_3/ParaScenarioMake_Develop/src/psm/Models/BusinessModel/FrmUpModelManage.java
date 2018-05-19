package psm.Models.BusinessModel;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;



import psm.Models.DB.DBHelper;

public class FrmUpModelManage {
	 private int max=0;
	 public int recordSchemeModelInfoID=0;
	
	/**
	 * 上传模型类型
	 * @param str
	 * @throws SQLException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public void upModelCat(String str) throws SQLException, ParserConfigurationException, SAXException, IOException{
		DBHelper db = new DBHelper("bxfz");//连接数据库
		 for (int i = 0; i < XmlStorageHelper.GetNodeInfoByNodeName(str, "Cat_id").size(); i++)
         {
			 List<String> NodeList = new ArrayList<String>();
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Cat_id").get(i));
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Cat_title").get(i));
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Cat_desc").get(i));
             String insert = "insert into MODEL_CAT(CAT_ID,CAT_TITLE,CAT_DESC)values('" + NodeList.get(0) + "','" + 
             NodeList.get(1) + "','" + NodeList.get(2) + "')";
             db.ExecuteQuery(insert);
         }
		 db.DBClose("bxfz");//关闭数据库
	}
	/**
	 * 上传模型参数信息
	 * @param str
	 * @throws SQLException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	 public void upModelParaInfo(String str) throws SQLException, ParserConfigurationException, SAXException, IOException
     {
		 DBHelper db = new DBHelper("bxfz");
         for (int i = 0; i < XmlStorageHelper.GetNodeInfoByNodeName(str, "Mpid").size(); i++)
         {
             List<String> NodeList = new ArrayList<String>();
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Mpid").get(i));
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Muid").get(i));
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Para_type").get(i));
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Para_defa_value").get(i));
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Para_label").get(i));
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Para_physicsunit").get(i));
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Basic_type").get(i));
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Para_length").get(i));
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Para_desc").get(i));
             String inserts="insert into Model_para_info values('"+NodeList.get(0).toString()+"','"+NodeList.get(1).toString()+"','1','"+NodeList.get(2).toString()+"','"+NodeList.get(3).toString()+"','"+NodeList.get(4).toString()+"','"+NodeList.get(5).toString()+"','"+NodeList.get(6).toString()+"','"+NodeList.get(7).toString()+"','"+NodeList.get(8).toString()+"')";
             db.ExecuteInsert(inserts);
         }
         db.DBClose("bxfz");
     }
	/**
	 * 上传模型交互信息
	 * @param str
	 * @throws SQLException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
     public void upModelInterInfo(String str) throws SQLException, ParserConfigurationException, SAXException, IOException
     {
    	 DBHelper db = new DBHelper("bxfz");
         for (int i = 0; i < XmlStorageHelper.GetNodeInfoByNodeName(str, "Inid").size(); i++)
         {
        	 List<String> NodeList = new ArrayList<String>();
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Inid").get(i));
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Muid").get(i));
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Sent_interactions").get(i));
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Accepted_interactions").get(i));
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Interaction_key").get(i));
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Interaction_paras").get(i));
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Interaction_desc").get(i));
             String inserts="insert into Model_interaction_info values('"+NodeList.get(0).toString()+"','"+NodeList.get(1).toString()+"','"+NodeList.get(4).toString()+"','"+NodeList.get(5).toString()+"','"+NodeList.get(6).toString()+"','"+NodeList.get(2).toString()+"','"+NodeList.get(3).toString()+"')";
             db.ExecuteInsert(inserts);
         }
         db.DBClose("bxfz");
     }

     /**
      * 上传模型描述参数
      * @param str
     * @throws SQLException 
     * @throws IOException 
     * @throws SAXException 
     * @throws ParserConfigurationException 
      */
//     public void upModelDescPara(String str) throws ParserConfigurationException, SAXException, IOException, SQLException
//     {
//    	 DBHelper db = new DBHelper("modeldb");
//         for (int i = 0; i < XmlStorageHelper.GetNodeInfoByNodeName(str, "Dpid").size(); i++)
//         {
//        	 List<String> NodeList = new ArrayList<String>();
//             boolean isInid = true;
//             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Dpid").get(i));
//             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Muid").get(i));
//             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Para_name").get(i));
//             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Para_value").get(i));
//             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Para_intro").get(i));
//             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Para_unit").get(i));
//             String inserts = "insert into Model_desc_para values('"+NodeList.get(0).toString()+"','"+NodeList.get(3).toString()+"','1','"+NodeList.get(4).toString()+"','"+NodeList.get(1).toString()+"','"+NodeList.get(2).toString()+"')";
//             db.ExecuteInsert(inserts);
//         }
//         db.DBClose("modeldb");
//     }     
     
     /**
      * 上传模型信息
     * @throws IOException 
     * @throws SAXException 
     * @throws ParserConfigurationException 
     * @throws SQLException 
      */
     public void upModelInfo(String str) 
     {
         try
         {
        	 List<String> NodeList = new ArrayList<String>();
             int have_src, have_doc, is_verified;
             if (XmlStorageHelper.GetNodeInfoByNodeName(str, "Hava_src").get(0) == "true")
                 have_src = 1;
             else
                 have_src = 0;
             if (XmlStorageHelper.GetNodeInfoByNodeName(str, "Hava_doc").get(0) == "true")
                 have_doc = 1;
             else
                 have_doc = 0;
             if (XmlStorageHelper.GetNodeInfoByNodeName(str, "Is_verified").get(0) == "true")
                 is_verified = 1;
             else
                 is_verified = 0;
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Muid").get(0));
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Model_version").get(0));
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Model_name").get(0));
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Publish_time").get(0).substring(0, 10));
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Modified_time").get(0).substring(0, 10));
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Development_languages").get(0));
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Development_tools").get(0));
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Model_intro").get(0));
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Model_sponsor").get(0));
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Target_access").get(0));
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Model_target").get(0));
             String inserts="insert into Model_info values('"+NodeList.get(0).toString()+"','"+NodeList.get(1).toString()+"','"+NodeList.get(2).toString()+"','"+NodeList.get(3).toString()+"','"+NodeList.get(4).toString()+"','"+NodeList.get(8).toString()+"','1','','','"+NodeList.get(7).toString()+"','1','1','1','1','"+NodeList.get(5).toString()+"','"+NodeList.get(6).toString()+"','1','"+ have_src+"','"+ have_src+"','"+is_verified+"','"+NodeList.get(10).toString()+"','','','"+NodeList.get(9).toString()+"')";
             DBHelper db = new DBHelper("bxfz");            
             db.ExecuteInsert(inserts);
             db.DBClose("bxfz");
         }
         catch (Exception e)
         {
            System.out.print("上传模型信息操作失败");
         }
     }

     public void upSchemeModelInfo(String str) throws ParserConfigurationException, SAXException, IOException, SQLException{
    	 DBHelper db = new DBHelper("bxfz");
         for (int i = 0; i < XmlStorageHelper.GetNodeInfoByNodeName(str, "ModelExeConfig").size(); i++)
         {       	
             List<String> NodeList = new ArrayList<String>();
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "ScenarioID").get(0));
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "MemberID").get(i));
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Muid").get(i));
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "ModelName").get(i));
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "MemberDLL").get(i));
             String inserts="insert into Scheme_model_info values('"+NodeList.get(0).toString()+"','"+NodeList.get(1).toString()+"','"+NodeList.get(2).toString()+"','"+NodeList.get(3).toString()+"','"+NodeList.get(4).toString()+"')";
             db.ExecuteInsert(inserts);
         }
         db.DBClose("bxfz");
     }
     
     public void upSchemeDescInfo(String str) throws ParserConfigurationException, SAXException, IOException, SQLException{
    	 DBHelper db = new DBHelper("bxfz");
         for (int i = 0; i < XmlStorageHelper.GetNodeInfoByNodeName(str, "GlobalInfo").size(); i++)
         {
             List<String> NodeList = new ArrayList<String>();
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "ScenarioID").get(i));
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "FederationName").get(i));
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Public_time").get(i));
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "SchemeAuthor").get(i));
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Modlfied_time").get(i));
             NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Count").get(i));
             String inserts="insert into Scheme_desc_info values('"+NodeList.get(0).toString()+"','"+NodeList.get(1).toString()+"','"+NodeList.get(2)
            		 .toString()+"','"+NodeList.get(3).toString()+"','','"+NodeList.get(4).toString()+"','','0','0','"+NodeList.get(5).toString()+"','')";
             db.ExecuteInsert(inserts);
         }
         db.DBClose("bxfz");
     }
     
     /**
      * 上传模型verification
      */
     
     public void upModelVerification(String str){
    	 
    	 try{
    		 List<String> NodeList = new ArrayList<String>();
    		 NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Model_vid").get(0));
    		 NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Muid").get(0));
    		 NodeList.add(XmlStorageHelper.GetNodeInfoByNodeName(str, "Model_category").get(0));
    		 String inserts="insert into Model_verification values('"+NodeList.get(0).toString()+"','"+NodeList.get(1).toString()+"','"+NodeList.get(2).toString()+"','2')";
             DBHelper db = new DBHelper("bxfz");
             db.ExecuteInsert(inserts);
             db.DBClose("bxfz");
    		 
    	 }catch (Exception e)
         {
             System.out.print("操作失败");
          }
    	 
     }

     /**
      * 查找数据表最大ID
      * @param table
      * @return
      * @throws SQLException
      */
     public int findMax(String table) throws SQLException
     {
         List<Integer> ID = new ArrayList<Integer>();
         DBHelper db = new DBHelper("bxfz");
         ResultSet rs=db.ExecuteQuery(table);
         int j=0;
         while(rs.next())
         {
      	     ID.add(Integer.valueOf(rs.getString(1)));
      	   j++;
         }
         max = ID.get(0);
         for (int i = 0; i < ID.size(); i++)
         {
             if (ID.get(i) > max)
             {
                 max = ID.get(i);
             }
         }
         return max;
     }


}
