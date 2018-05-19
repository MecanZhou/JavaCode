import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;


public class RID {
	Connection conn;
	Statement st;
	ArrayList<Integer> RID_Arr=new ArrayList<Integer>();
	public void rid(String fedName) throws SQLException, ClassNotFoundException, IOException{//rid() 读取数据库中
		int RIDvalue;
		//System.out.println("select RID from "+fedName);
		Class.forName("com.mysql.jdbc.Driver");//加载数据库驱动
		 conn=DriverManager.getConnection("jdbc:mysql://172.16.73.110:3306/"+"data_analysis");
		 st=conn.createStatement();//用conn对象创建st语句对象 
		ResultSet rs=st.executeQuery("select RID from "+fedName);
		
		
		//如果fedName数据库不存在，则创建
			
		
		
		
		
		while(rs.next())
		{
			RID_Arr.add(rs.getInt("RID"));
			//System.out.println(RID);
		}
		System.out.println("AAAAAAAAA+"+RID_Arr.size());
		if(RID_Arr.size()==0)//如果表里的RID一栏没有数据
		{
		    RIDvalue=0;
			RIDvalue++;
			System.out.println("RIDvalue为1");
		}
		else//如果RID一栏有数据，取最大值
		{
//			System.out.println(RID_Arr);
			RIDvalue=Collections.max(RID_Arr);
			RIDvalue++;
			System.out.println("RIDvalue为 "+RIDvalue);
		}
			File file = new File("E:\\ftp\\"+"RID"+".txt");
			if(!file.exists())
			{file.createNewFile();};
			//System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA    生成RID。TXT文件");
		 FileWriter fw= new FileWriter(file,false);//同时创建新文件  
		 BufferedWriter bf = new BufferedWriter(fw);   
		//创建缓冲字符输出流对象  
		bf.append(RIDvalue+""); 
		//System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA    输入1到RID.TXT文件中");
		bf.flush();  
		bf.close();  
		//return RIDvalue;
		//System.out.println("RIDvalue="+RIDvalue);
		conn.close();
		
		//如果存在fedName。txt，则删除
		File file1 = new File("E:\\ftp", fedName+".txt");
		if(file1.exists()){
			file1.delete();
		}
		}
	public void createTable(String fedName) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		Class.forName("com.mysql.jdbc.Driver");//加载数据库驱动
		conn=DriverManager.getConnection("jdbc:mysql://172.16.73.110:3306/"+"data_analysis");
		 st=conn.createStatement();//用conn对象创建st语句对象 
		ResultSet rs1=conn.getMetaData().getTables(null, null,fedName, null );//判断表是否存在，存在则不创建，不存在就创建
		String sql="create table "+fedName+
				"(FederateName varchar(20), " +
				"ModelID varchar(20)," +
				"RID int(20),"+
				"Value varchar(20)," +
				"RunningTime varchar(50))";
		if(!rs1.next())
		{
			st.execute(sql);
		}		
		conn.close();
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		RID rid=new RID();
		rid.rid("kkk");
	}
}