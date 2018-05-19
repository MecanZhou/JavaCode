/**
 * 
 */
package edu.jhun.utils;

import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * @author Administrator
 * @time 2018年1月8日下午2:12:09
 *	C3P0数据库连接池工具类
 */
public class C3P0Utils {
	
	private static ComboPooledDataSource dataSource = 
			new ComboPooledDataSource();
	
	/**
	 * 返回数据库连接
	 * @return
	 */
	public static Connection getConnection(){
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			System.out.println("数据库连接异常！");
			e.printStackTrace();
		}
		return null;
	}
}
