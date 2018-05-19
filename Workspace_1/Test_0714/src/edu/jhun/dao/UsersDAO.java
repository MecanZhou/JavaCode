package edu.jhun.dao;

import java.util.List;

import edu.jhun.bean.Users;

public interface UsersDAO {
	
	public List<Users> findUsersByName(String userName);

}
