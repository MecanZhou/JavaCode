package edu.jhun.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.jhun.bean.Users;
import edu.jhun.dao.UsersDAO;
import edu.jhun.util.DBHelper;

public class UsersDAOImpl implements UsersDAO {

	@Override
	public List<Users> findUsersByName(String userName) {
		
		List<Users> list = new ArrayList<Users>();
		Connection con= new DBHelper().getDBConnection();
		String sql="SELECT * FROM users WHERE userName = ?";
		
		PreparedStatement pstm=null;
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, userName);
			ResultSet rs= pstm.executeQuery();
			while(rs.next())
			{
				Users user = new Users(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
				list.add(user);
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		
		return list;
	}

	
}
