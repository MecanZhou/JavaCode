package edu.jhun.wedding.test;

import org.junit.Test;

import edu.jhun.wedding.bean.User;
import edu.jhun.wedding.dao.UserDAO;

public class UserDAOTest {
	
	@Test
	public void testLogin(){
		UserDAO userDAO = new UserDAO();
		User user= new User();
		user=userDAO.login("027", "123");
		System.out.println(user);
	}

}
