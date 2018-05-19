/**
 * 
 */
package edu.jhun.utils;

import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @author Administrator
 * @time 2018��1��8������2:12:09
 *	C3P0���ݿ����ӳع�����
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
			System.out.println("���ݿ������쳣��");
			e.printStackTrace();
		}
		return null;
	}
}
