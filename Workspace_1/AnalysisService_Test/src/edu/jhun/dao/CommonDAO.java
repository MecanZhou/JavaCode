/**
 * 
 */
package edu.jhun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import edu.jhun.utils.C3P0Utils;

/**
 * @author Administrator
 * @time 2018年1月8日下午2:19:55
 *	公共数据访问对象，提供数据操作方法
 */
public class CommonDAO {
	
	/**
	 * 执行增删改
	 * @param sql
	 * @param params
	 * @return
	 */
	public boolean update(String sql,Object... params){
		//通过连接池获得数据库连接
		try(Connection conn = C3P0Utils.getConnection()){
			PreparedStatement ps = conn.prepareStatement(sql);
			for(int i = 0;i < params.length;i++){
				ps.setObject(i + 1, params[i]);
			}
			int rows = ps.executeUpdate();
			return rows > 0;
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 执行查询
	 * @param conn 数据库连接
	 * @param sql
	 * @param params
	 * @return 结果集（包含查询到的数据）
	 */
	public ResultSet query(Connection conn,String sql,Object... params){
		//通过连接池获得数据库连接
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

}
