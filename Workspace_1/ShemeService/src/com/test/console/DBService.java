package com.test.console;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBService {
	
	private static String url= "jdbc:mysql://localhost:3306/scenariodb";
	private static Connection connection;
	
	
	public DBService(String sqlName, String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("�޷��������ݿ�����");
			e.printStackTrace();			
		}
		try {
			connection = (Connection) DriverManager.getConnection(url, sqlName, password);
			System.out.println("���ݿ����ӳɹ�");
		} catch (SQLException e) {
			System.out.println("�������ݿ����");
			e.printStackTrace();
		}
	}
	
	public void ExecuteInsert(String sql) {
		try {
			Statement stmt=connection.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println("ExecuteInsert()Exception:SQLException");
			e.printStackTrace();
		}
		
	}
	
	public void ExecuteDelete(String sql){
		try{	   
			Statement stmt=connection.createStatement();
			stmt.execute(sql);
		}catch(SQLException e){
			System.out.println("ExecuteDelete()Exception:SQLException");
			e.printStackTrace();
		}
	}
	
	public void ExecuteUpdate(String sql) {
		
		try {
			Statement stmt=connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("ExecuteUpdate()Exception:SQLException");
			e.printStackTrace();
		}
	}
	
	public ResultSet ExecuteQuery(String sql) throws SQLException {		   
		  
		Statement stmt=connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		return rs;
	   }
	
	
}
