import hla.rti.IllegalTimeArithmetic;
import hla.rti.InvalidFederationTime;
import hla.rti.InvalidLookahead;
import hla.rti.LogicalTime;
import hla.rti.LogicalTimeInterval;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import se.pitch.prti.LogicalTimeDouble;
import se.pitch.prti.LogicalTimeIntervalDouble;




public class DataCollection {

	/**
	 * @param args
	 */
	public static  Statement stmt;
	public static  int Frequency=1;
	public static  DatabaseMetaData md=null;
	public static  ResultSet rs=null;
	public static  String MemberID="A";
	public static  String MemberID1="B";
	public static  String DllAfter="123.56";
	public static  String ModelName="test";
	public static  int Proportion=30;
	public static  LogicalTime currentTime;
	public static  boolean flag=true;
	public static  boolean flag1=false;
	public static  ArrayList<String> data=new ArrayList<String>();
	public static  ArrayList<String> MemberIDList=new ArrayList<String>();
	public static  ArrayList<String> ModelNameList=new ArrayList<String>();
	public static  ArrayList<String> TimeList=new ArrayList<String>();
	static Connection con =COMReadDB.getConnection();
	public static  void main(String[] args) throws SQLException, InvalidFederationTime, InvalidLookahead, IllegalTimeArithmetic {
		LogicalTime currentTime=new LogicalTimeDouble(0.0);
		LogicalTimeInterval lookhead=new LogicalTimeIntervalDouble(0.1);
for(int i=0;i<200;i++)
{currentTime.increaseBy(lookhead);
Collection(MemberID,DllAfter,ModelName,currentTime);
Collection(MemberID1,DllAfter,ModelName,currentTime);
}
	
}
	public static void Collection(String MemberID,String DllAfter,String FederationName,LogicalTime currentTime) throws SQLException, InvalidFederationTime
	{ 
		
		//在存放数据的数据库中创建按方案ID创建的表格
//		ResultSet rs1=con.getMetaData().getTables(null, null,FederationName, null );//判断表是否存在，存在则不创建，不存在就创建
//		Statement st=con.createStatement();
//		String sql="create table "+FederationName+
//				"(FederateName varchar(20), " +
//				"ModelID varchar(20)," +
//				"RID int(20),"+
//				"Value varchar(20)," +
//				"RunningTime varchar(50))";
//		if(!rs1.next())//如果数据库中没有当前联邦名的表单，则创建
//		{
//			st.execute(sql);
//			System.out.println("创建表成功");
//		}		
		
         if(flag)
          {ArrayList<String> RIDList=new ArrayList<String>();
        	 stmt =  con.createStatement();  
        	 ResultSet rs=stmt.executeQuery("select RID from "+FederationName);
        	 while(rs.next())
        	 {
        		 RIDList.add(rs.getString("RID"));
        	 }
        	if(RIDList.size()==0)
        	{
        		Frequency=1;
        	}
        	
        		if((RIDList.size()%30==0)&&(RIDList.size()!=0))
        		{
        			
        			Frequency=Integer.parseInt(top(RIDList))+1;
        			System.out.println(Frequency+"sssssssssssss");
        		}
        		if((RIDList.size()!=0)&&(RIDList.size()%30!=0))
        		{
        			Frequency=Integer.parseInt(RIDList.get(RIDList.size()-1));
        			System.out.println(Frequency+"zzzzzzzzzzzzzzzzzzz");
        		}
        	
        		//System.out.println(Frequency+"sssssssssssssssssssssssssss");
	                flag=false;
              }
	String time=currentTime+"";
	String temp[]=time.split("<");
	String CT ="";
	for(int i=0;i<temp[1].length()-1;i++)
	{	 
		CT=CT+String.valueOf(temp[1].charAt(i));
	}
	System.out.println(CT);
	double current=Double.valueOf(CT);
	int a=Transformation(current);
	
	System.out.println(a+"           "+current);
	if(a%Proportion==0)
	{
		data.add(DllAfter);
		MemberIDList.add(MemberID);
		ModelNameList.add(FederationName);
		TimeList.add(time+"");
		String s="";
		for(int i=0;i<data.size();i++)
		{
			
			
			
			
				if(i==0){s="('"+ModelNameList.get(i)+"','"
				              +MemberIDList.get(i)+"','"
						       +Frequency+"','"
				              +data.get(i)+"','"
						       +TimeList.get(i)+"'),";}
				else{s=s+"('"+ModelNameList.get(i)+"','"
						       +MemberIDList.get(i)+"','"
						       +Frequency+"','"
						       +data.get(i)+"','"
						       +TimeList.get(i)+"'),";}				
			
			
		}
		s=s.substring(0, s.length()-1);
		stmt =  con.createStatement();  
		stmt.executeUpdate("insert into "+FederationName+" value "+s+"");
		System.out.println("cs");
		data.clear();
		MemberIDList.clear();
		ModelNameList.clear();
		TimeList.clear();
	}
	else
	{
		data.add(DllAfter);
		MemberIDList.add(MemberID);
		ModelNameList.add(FederationName);
		TimeList.add(time);
	}
    }
	public static int Transformation(double current)
	{
		String str=current+"";
	    String str1="";
	    int temp=0;
	for(int i=0;i<str.length();i++)
	{	 
	
		
		if(String.valueOf(str.charAt(i)).equals("."))
		{ 
			if((str.length()-i)>3)
		{
			if(Integer.parseInt(str.charAt(i+2)+"")>4)
			{
				str1=Integer.parseInt(str1)*10+1+Integer.parseInt(str.charAt(i+1)+"")+"";
				return Integer.parseInt(str1);
			}
		}
			
       str1=Integer.parseInt(str1)*10+Integer.parseInt(String.valueOf(str.charAt(i+1)))+"";
			
	        return Integer.parseInt(str1);
		}
		
		str1=str1+String.valueOf(str.charAt(i));
	}
	return 1;	

   }

public static String top(ArrayList<String> RIDList)
{         String temp="";
	for(int i=0;i<RIDList.size();i++)
	{      temp=RIDList.get(0);
		if(Integer.parseInt(temp)<Integer.parseInt(RIDList.get(i)))
		{
			temp=RIDList.get(i);
		}
	}
	return temp;
	
}
}