/**
 * 
 */
package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * @author Administrator
 * @time 2018��1��7������4:42:21
 *
 */
public class DBHelper {
	static boolean DBflag=true;//Ĭ������ORACLE�����ݿ�
	static String DBType;
//	public static void main(String[] args)
//	{
//		DBHelper db=new DBHelper();
//		System.out.println("=====�������ݿ�=====");
//		Connection con=db.getConnection();
//		System.out.println("=====��ʼִ��=====");
//		try {
//			db.ExecuteQuery("select * from tb_contact_lenses;", con);
//			//db.ExecuteDelete("TRUNCATE TABLE tb_contact_lenses", con);
//			con.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
	//��ѯ����
	public ResultSet query(Connection conn,String sql,Object... params){
		//ͨ�����ӳػ�����ݿ�����
		try{
			PreparedStatement ps = conn.prepareStatement(sql);
			for(int i = 0;i < params.length;i++){
				ps.setObject(i + 1, params[i]);
			}
			ResultSet rs = ps.executeQuery();
			return rs;
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		return null;
	}
	public void ExecuteQuery(String sql,Connection con) throws SQLException {		   

		Statement stmt=con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		System.out.println("=====��ѯ���=====");
		//System.out.println("MemberId\t "+"Outputvalue\t"+"Step\t");
		while (rs.next()) {
			System.out.print(rs.getString(1)+"\t");
			System.out.print(rs.getString(2)+"\t");
			System.out.print(rs.getString(3)+"\t");
			System.out.print(rs.getString(4)+"\t");
			System.out.print(rs.getString(5)+"\n");
		}
		rs.close();
	}
	//ɾ������
	public void ExecuteDelete(String sql,Connection con){
		System.out.println("ȷ��ִ��ɾ�����ԣ�\nY���ǣ� N����");
		Scanner scanner  = new Scanner(System.in);
		String str = scanner.nextLine();
		scanner.close();
		if (str.equalsIgnoreCase("Y")) {
			try{	   
				Statement stmt=con.createStatement();
				stmt.execute(sql);
				System.out.println("ɾ���ɹ���");
			}catch(SQLException e){
				e.printStackTrace();
			}
		}else {
			System.out.println("��ֹɾ����");
			return;
		}
	}
	//��ȡ���ݿ�����
	public static Connection getConnection()

	{
		Connection con = null;
		try {
			String encording="UTF-8";
			String lineTxt=null;
			String serverIP=null;
			String username=null;
			String password=null;
			String database=null;
			File file=new File("DB_INFO/DBType.txt");
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
				File file1=new File("DB_INFO/OracleDBset.txt");
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
				File file1=new File("DB_INFO/MySQLDBset.txt");
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
			if(DBflag==true)//���ӵ���Oracle�����ݿ�
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con = (Connection) DriverManager.getConnection("jdbc:oracle:thin:@"+serverIP+":1521:xe", username, password);
				System.out.println("���ݿ�Oracle���ӳɹ�");
			}
			else//���ӵ���MySql �����ݿ�
			{
				//System.out.println("LocalHostIP :"+InetAddress.getLocalHost().getHostAddress());
				Class.forName("com.mysql.jdbc.Driver");
				con=(Connection) DriverManager.getConnection("jdbc:mysql://"+serverIP+":3306/"+database,username,password);
				System.out.println("���ݿ�MySql���ӳɹ�,���ӿ�Ϊ��"+database);
				System.out.println("jdbc:mysql://"+serverIP+":3306/"+database);
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("�������ݿ�ʧ��"+e.getMessage());
		}
		return con;
	}
}
