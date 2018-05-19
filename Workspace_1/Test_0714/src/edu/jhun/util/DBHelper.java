 package edu.jhun.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
	
	private final String sqlName ="admin";
	private final String sqlPwd = "root";
	private final String driver = "com.mysql.jdbc.Driver";
	private final String jdbc="jdbc:mysql://localhost:3306/mysql?characterEncoding=UTF-8";
	
	public DBHelper() {
		super();
		// TODO �Զ����ɵĹ��캯�����
	}

	public Connection getDBConnection() {
		
		Connection con=null;
		
		try {
			Class.forName(driver);
			con=DriverManager.getConnection(jdbc, sqlName, sqlPwd);
		}  catch (ClassNotFoundException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	
		return con;
		
	}
}
