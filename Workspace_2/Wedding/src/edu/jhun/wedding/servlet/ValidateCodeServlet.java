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
		//��ͼ����
		Graphics g = image.getGraphics();
		//������ɫ
		g.setColor(Color.LIGHT_GRAY);
		g.setFont(new Font("΢���ź�", Font.PLAIN, 20));
		//��ͼ
		g.fillRect(0, 0, WIDTH, HEIGHT);
		//�����ĸ����������֤��
		Random random = new Random();
		char[] codes = new char[4];
		for (int i = 0; i < 4; i++) {
			//���ѡ��һ������
			codes[i] = CODES.charAt(random.nextInt(CODES.length()));
			//������ɫ�������ַ�
			g.setColor(COLORS[random.nextInt(COLORS.length)]);
			g.drawString(codes[i]+"", 10+i*20, 20);
			//���ø�����
			g.setColor(COLORS[random.nextInt(COLORS.length)]);
			g.drawLine(random.nextInt(WIDTH), random.nextInt(HEIGHT),
					random.nextInt(WIDTH), random.nextInt(HEIGHT));
		}
		//�����������Ϊ������
		resp.setHeader("Cache-controll", "no-cache");
		//��������ύ��֤��
		req.getSession().setAttribute("code", new String(codes));
		//ͨ�����������ͼƬ
		ImageIO.write(image, "png", resp.getOutputStream());
		

		resp.flushBuffer();


		
	}

}
