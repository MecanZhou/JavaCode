import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;


public class CTReadDB {

	static boolean DBflag=true;//Ĭ������ORACLE�����ݿ�
	static String DBType;
//	public static void main(String[] args)
//	{
//		CTReadDB db=new CTReadDB();
//		Connection con=db.getConnection();
//	}
	public static Connection getConnection()
	{
		Connection con=null;
		try {
			String encording="UTF-8";
			String lineTxt=null;
			String serverIP=null;
			String username=null;
			String password=null;
			String database=null;
			File file=new File("DBType.txt");
			InputStreamReader read=new InputStreamReader(new FileInputStream(file),encording);
			//			FileOutputStream fos=new FileOutputStream(file);
			BufferedReader bufferedReader=new BufferedReader(read);//��ȡ���ݿ����͵��ļ���
			while((lineTxt=bufferedReader.readLine())!=null)
			{
				if(lineTxt.equals("DBType"))
				{
					String Style=bufferedReader.readLine();
					if(Style.equalsIgnoreCase("MYSQL"))
					{
						DBType="MYSQL";
						DBflag=false;
						System.out.println("���ݿ�����ΪMySql");
					}
					else if(Style.equalsIgnoreCase("ORACLE"))
					{
						DBType="ORACLE";
						System.out.println("���ݿ�����ΪOracle");
					}
				}
			}
			bufferedReader.close();
			if(DBflag==true)//���ӵ���Oracle �����ݿ�
			{
				File file1=new File("OracleDBset.txt");
				InputStreamReader read1=new InputStreamReader(new FileInputStream(file1),encording);
				BufferedReader bufferedReader1=new BufferedReader(read1);
				while((lineTxt=bufferedReader1.readLine())!=null)
				{		
					if(lineTxt.equals("ServerIP")){
						serverIP=bufferedReader1.readLine();
						System.out.println("DB_IP: "+serverIP);
					}
					if(lineTxt.equals("username")){
						username=bufferedReader1.readLine();
						System.out.println(username);
					}
					if(lineTxt.equals("password")){
						password=bufferedReader1.readLine();
						System.out.println(password);
					}
				}
			}
			else//���ӵ���MYSQL �����ݿ�
			{
				File file1=new File("MySQLDBset.txt");
				InputStreamReader read1=new InputStreamReader(new FileInputStream(file1),encording);
				BufferedReader bufferedReader1=new BufferedReader(read1);
				while((lineTxt=bufferedReader1.readLine())!=null)
				{		
					if(lineTxt.equals("ServerIP")){
						serverIP=bufferedReader1.readLine();
						System.out.println("DB_IP: "+serverIP);
					}
					if(lineTxt.equals("username")){
						username=bufferedReader1.readLine();
						//System.out.println(username);
					}
					if(lineTxt.equals("password")){
						password=bufferedReader1.readLine();
						//System.out.println(password);
					}
					if(lineTxt.equals("database")){
						database=bufferedReader1.readLine();
						//System.out.println(database);
					}
				}
			}
			if(DBflag==true)//���ӵ���Oracle �����ݿ�
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				//con=DriverManager.getConnection("jdbc:mysql://"+serverIP+":3306/"+database,username,password);
				con = DriverManager.getConnection("jdbc:oracle:thin:@"+serverIP+":1521:xe", username, password);
				System.out.println("���ݿ�Oracle���ӳɹ�");
			}
			else//���ӵ���MySql �����ݿ�
			{
				System.out.println("LocalHostIP :"+InetAddress.getLocalHost().getHostAddress());
				Class.forName("com.mysql.jdbc.Driver");
				con=DriverManager.getConnection("jdbc:mysql://"+serverIP+":3306/"+database,username,password);
				System.out.println("���ݿ�MySql���ӳɹ�");
			}

		} catch (Exception e) {
			// TODO: handle exception
			//_textAreaHandler.append("���ݿ�����ʧ��");
			JOptionPane.showMessageDialog(null, "���ݿ�����ʧ��", "��ʾ", 2);
			System.out.println("�������ݿ�ʧ��"+e.getMessage());
		}
		return con;
	}

}
