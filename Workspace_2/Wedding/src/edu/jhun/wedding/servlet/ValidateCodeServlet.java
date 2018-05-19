package edu.jhun.wedding.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ValidateCode.do")
public class ValidateCodeServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 100;
	private static final int HEIGHT = 30;
	private static final String CODES="0123456789QWERTYUIOPASDFGHJKLZXCVBNM";
	private static final Color[] COLORS={Color.BLACK,Color.RED,Color.YELLOW,Color.GREEN};
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		//绘图对象
		Graphics g = image.getGraphics();
		//设置颜色
		g.setColor(Color.LIGHT_GRAY);
		g.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		//绘图
		g.fillRect(0, 0, WIDTH, HEIGHT);
		//创建四个随机文字验证码
		Random random = new Random();
		char[] codes = new char[4];
		for (int i = 0; i < 4; i++) {
			//随机选择一个文字
			codes[i] = CODES.charAt(random.nextInt(CODES.length()));
			//设置颜色并绘制字符
			g.setColor(COLORS[random.nextInt(COLORS.length)]);
			g.drawString(codes[i]+"", 10+i*20, 20);
			//设置干扰线
			g.setColor(COLORS[random.nextInt(COLORS.length)]);
			g.drawLine(random.nextInt(WIDTH), random.nextInt(HEIGHT),
					random.nextInt(WIDTH), random.nextInt(HEIGHT));
		}
		//缓存控制设置为不缓存
		resp.setHeader("Cache-controll", "no-cache");
		//向服务器提交验证码
		req.getSession().setAttribute("code", new String(codes));
		//通过输出流返回图片
		ImageIO.write(image, "png", resp.getOutputStream());
		

		resp.flushBuffer();


		
	}

}
