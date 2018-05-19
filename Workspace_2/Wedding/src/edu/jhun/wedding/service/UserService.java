package edu.jhun.wedding.service;

import edu.jhun.wedding.bean.User;
import edu.jhun.wedding.dao.UserDAO;

public class UserService {
	
	private UserDAO userDAO = new UserDAO();
	
	public User login(String user_tel, String user_password){
		
		return userDAO.login(user_tel, user_password);
	}

}
