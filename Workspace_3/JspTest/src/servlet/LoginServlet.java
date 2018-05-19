package servlet;

import java.io.IOException;
import java.rmi.ServerException;

import javabean.Loginbean;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.userDao;
import dao.impl.userDaoImpl;

public class LoginServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8552683134862056264L;
	
	protected void doGet(HttpServletRequest request,HttpServletResponse response)
			throws ServerException,IOException{
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		
		Loginbean loginbean =new Loginbean();
		loginbean.setName(username);
		loginbean.setPassword(password);
		userDao logindao=new userDaoImpl();
		String message= logindao.login(loginbean);
		if(message.equals("error")){
			String url="failureJump.jsp";
			url=new String(url.getBytes("UTF-8"), "ISO-8859-1");
			response.sendRedirect(url);
		}else {
			String url="successJump.jsp?message="+message;
			url=new String(url.getBytes("UTF-8"), "ISO-8859-1");
			response.sendRedirect(url);
		}
		
	}
	protected void doPost(HttpServletRequest request,HttpServletResponse response)
			throws ServerException,IOException {
		this.doGet(request, response);
	}
}
