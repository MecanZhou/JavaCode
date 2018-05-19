package psm.Models.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;

import com.sun.rowset.CachedRowSetImpl;

public class DBHelper implements InterfaceDBHelper 
{
	//JDBC��URL
	public  String url=" jdbc:mysql://172.16.73.110:3306/bxfz";
	 static Connection conn;
	//ִ���������ݿ�Ĳ���
   public DBHelper(String sqlName)
   {
		try {
			Class.forName("com.mysql.jdbc.Driver"); 
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("�޷��������ݿ�����");
			e.printStackTrace();
		}
		
		try{
			conn=DriverManager.getConnection("jdbc:mysql://172.16.73.110:3306/bxfz","root","root");
		}
		catch(SQLException e)
		{
			System.out.println("�������ݿ����");
		   e.printStackTrace();
		}
		
   }
   
   public void DBClose(String sqlName) throws SQLException{
	   conn.close();
	   
   }
   
   public static void main(String args[]) throws SQLException {  
	   DBHelper db = new DBHelper("bxfz");
	   Statement stmt=conn.createStatement();
   }
   
   
   //ִ�в�ѯ
   public ResultSet ExecuteQuery(String sql) throws SQLException
   {
	   
	   
	   Statement stmt=conn.createStatement();
	   ResultSet rs=stmt.executeQuery(sql);
	   return rs;
   }
   
   public static int findMax(String table) throws SQLException
   {
       List<Integer> ID = new ArrayList<Integer>();
       DBHelper db = new DBHelper("bxfz");
       ResultSet rs=db.ExecuteQuery(table);
       while(rs.next())
       {
    	   ID.add(Integer.valueOf(rs.getString("muid")));
       }
       int max = ID.get(0);
       for (int i = 0; i < ID.size(); i++)
       {
           if (ID.get(i) > max)
           {
               max = ID.get(i);
           }
       }
       System.out.print(max);
       return max;
   }
   
   public void ExecuteInsert(String sql) throws SQLException
   {
	   Statement stmt=conn.createStatement();
	   stmt.executeUpdate(sql);   //��������
   }
   
   //ɾ������
   public void ExecyteDelete(String sql) throws SQLException
   {
	   try{	   
		    Statement stmt=conn.createStatement();
		    stmt.execute(sql);
		   }catch(SQLException e){
			   e.printStackTrace();
		   }

   }

}
