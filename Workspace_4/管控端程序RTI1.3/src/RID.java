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
	public void rid(String fedName) throws SQLException, ClassNotFoundException, IOException{//rid() ��ȡ���ݿ���
		int RIDvalue;
		//System.out.println("select RID from "+fedName);
		Class.forName("com.mysql.jdbc.Driver");//�������ݿ�����
		 conn=DriverManager.getConnection("jdbc:mysql://172.16.73.110:3306/"+"data_analysis");
		 st=conn.createStatement();//��conn���󴴽�st������ 
		ResultSet rs=st.executeQuery("select RID from "+fedName);
		
		
		//���fedName���ݿⲻ���ڣ��򴴽�
			
		
		
		
		
		while(rs.next())
		{
			RID_Arr.add(rs.getInt("RID"));
			//System.out.println(RID);
		}
		System.out.println("AAAAAAAAA+"+RID_Arr.size());
		if(RID_Arr.size()==0)//��������RIDһ��û������
		{
		    RIDvalue=0;
			RIDvalue++;
			System.out.println("RIDvalueΪ1");
		}
		else//���RIDһ�������ݣ�ȡ���ֵ
		{
//			System.out.println(RID_Arr);
			RIDvalue=Collections.max(RID_Arr);
			RIDvalue++;
			System.out.println("RIDvalueΪ "+RIDvalue);
		}
			File file = new File("E:\\ftp\\"+"RID"+".txt");
			if(!file.exists())
			{file.createNewFile();};
			//System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA    ����RID��TXT�ļ�");
		 FileWriter fw= new FileWriter(file,false);//ͬʱ�������ļ�  
		 BufferedWriter bf = new BufferedWriter(fw);   
		//���������ַ����������  
		bf.append(RIDvalue+""); 
		//System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA    ����1��RID.TXT�ļ���");
		bf.flush();  
		bf.close();  
		//return RIDvalue;
		//System.out.println("RIDvalue="+RIDvalue);
		conn.close();
		
		//�������fedName��txt����ɾ��
		File file1 = new File("E:\\ftp", fedName+".txt");
		if(file1.exists()){
			file1.delete();
		}
		}
	public void createTable(String fedName) throws SQLException, ClassNotFoundException {
		// TODO Auto-generated method stub
		Class.forName("com.mysql.jdbc.Driver");//�������ݿ�����
		conn=DriverManager.getConnection("jdbc:mysql://172.16.73.110:3306/"+"data_analysis");
		 st=conn.createStatement();//��conn���󴴽�st������ 
		ResultSet rs1=conn.getMetaData().getTables(null, null,fedName, null );//�жϱ��Ƿ���ڣ������򲻴����������ھʹ���
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