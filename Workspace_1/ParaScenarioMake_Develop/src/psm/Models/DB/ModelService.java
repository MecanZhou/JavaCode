package psm.Models.DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.omg.CORBA.OBJ_ADAPTER;

public class ModelService {
	public static ResultSet GetModels() throws SQLException{
		 DBHelper dbHlp = new DBHelper("bxfz");
         String strSql = "select * from model_info";
         return dbHlp.ExecuteQuery(strSql);   //执行查询语句
	}
	
	public static ResultSet GetModelParaInfo() throws SQLException{
		 DBHelper dbHlp = new DBHelper("bxfz");
        String strSql = "select * from model_para_info";
        return dbHlp.ExecuteQuery(strSql);   //执行查询语句
	}
	
	public static ResultSet GetNameModels() throws SQLException{
		 DBHelper dbHlp = new DBHelper("bxfz");  
        String strSql = "select muid,model_name from model_info";   
        return dbHlp.ExecuteQuery(strSql);   //执行查询语句
        
	} 
	
	public static ResultSet GetModelPictures() throws SQLException{
		DBHelper dbHlp=new DBHelper("bxfz");
		String strSql = "select picture from model_picture";
		return dbHlp.ExecuteQuery(strSql);	 //执行查询语句
	}
	
	public static ResultSet GetModelsAllInfo() throws SQLException
	{
		DBHelper dbHlp = new DBHelper("bxfz");
	    String strSql = "select * from model_info,model_para_info,para_info";
		return dbHlp.ExecuteQuery(strSql);
		
	}
	
	/// <summary>
    /// 获取一个模型的参数表，具体参数信息，交互信息等。。。
    /// </summary>
    /// <param name="modelUID">表中的UID</param>
    /// <returns>查询的表名Model</returns>
	public static ResultSet GetModelInfo(int id ,List<String> tableNames) throws SQLException
	{
		DBHelper dbHlp = new DBHelper("bxfz");    
        String strSql = "select * from " + tableNames.get(0) + " where Muid=" + id;            
     	return dbHlp.ExecuteQuery(strSql);
	}
	
	//从model_verification中获取指定muid的数据
    public static ResultSet GetModelVerfi(int muid) throws SQLException
    {
        DBHelper dbHlp = new DBHelper("bxfz");
        String strSql = "select *from model_verification where muid=" + muid;
        return dbHlp.ExecuteQuery(strSql);
    }
    
    //从model_info删除模型
    public static void DleteModelInfoNode(String MUID) throws SQLException{
    	DBHelper db=new DBHelper("");						 
		String sql = "delete from model_info where MUID="+MUID;						    							 
		 try {
			db.ExecyteDelete(sql);		     //在数据库中删除节点										
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    }



}
