package edu.jhun.wedding.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.jhun.wedding.bean.User;
import edu.jhun.wedding.service.UserService;

@WebServlet("/login.do")
public class UserLoginServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String user_tel = req.getParameter("user_tel");
		String user_password = req.getParameter("user_password");
		String user_code = req.getParameter("code");
		if (user_tel==null || user_password == null) {
			throw new IllegalArgumentException("�û���������Ϊ�գ�");
		}
		//��ȡ����������֤��
		String code = (String) req.getSession().getAttribute("code");
		if (code!=null&&!code.equalsIgnoreCase(user_code)) {
			req.setAttribute("msg", "��֤�����");
			req.getRequestDispatcher("login.jsp").forward(req, resp);
			return;
		}
		UserService userService = new UserService();
		User user = userService.login(user_tel, user_password);
		if (user!=null) {
			req.getSession().setAttribute("user", user);
			resp.sendRedirect("index.jsp");
		}else {
			req.setAttribute("msg", "�û������������");
			req.getRequestDispatcher("login.jsp").forward(req, resp);
		}
	}

}
