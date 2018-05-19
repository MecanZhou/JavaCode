package edu.jhun.wedding.dao;

import java.sql.Connection;
import java.sql.ResultSet;

import edu.jhun.wedding.bean.User;

public class UserDAO extends BaseDAO{
	
	public User login(String user_tel, String user_password) {
		
		try (Connection conn = C3P0Utils.getConnection()) {
			ResultSet rs=query(conn, "select * from tb_user where user_tel = ? and user_password = ?", user_tel,user_password);
			if (rs.first()) {
				User user = new User();
				user.setUser_id(rs.getInt("user_id"));
				user.setUser_nickname(rs.getString("user_nickname"));
				user.setUser_tel(rs.getString("user_tel"));
				user.setUser_password(rs.getString("user_password"));
				return user;
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

}
