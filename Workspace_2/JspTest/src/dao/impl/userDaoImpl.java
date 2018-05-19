package dao.impl;

import javabean.Loginbean;
import dao.userDao;

public class userDaoImpl implements userDao {

	public String login(Loginbean loginbean) {
		String message=null;
		if(loginbean.getName().equals("Mecan")&&loginbean.getPassword().equals("123")){
			message="µÇÂ¼³É¹¦£¬»¶Ó­Äú£¬"+loginbean.getName();
		}else{
			message="error";
		}
		return message;
	}

}
