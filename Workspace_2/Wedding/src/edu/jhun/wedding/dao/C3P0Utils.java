package edu.jhun.wedding.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * ���ݿ����ӳع�����
 * @author Administrator
 *
 */
public class C3P0Utils {

	private static ComboPooledDataSource dataSource = 
			new ComboPooledDataSource();
	
	/**
	 * �������ݿ�����
	 * @return
	 */
	public static Connection getConnection(){
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
