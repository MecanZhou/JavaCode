package edu.jhun.wedding.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据访问父类
 * @author Administrator
 *
 */
public class BaseDAO {

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
