package com.edu.swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class FaceJpanel extends JPanel {

	@Override
	protected void paintComponent(Graphics arg0) {
		super.paintComponent(arg0);	

		//确定坐标常量
		double x=this.getWidth()/2;
		double y=this.getHeight()/2;
		double radius=180;
		
		//绘制2D图形工具
		Graphics2D g2= (Graphics2D) arg0;
		
		//绘制轮廓，使用一个圆形，由圆心和半径确定
		Ellipse2D face = new Ellipse2D.Double();
		face.setFrameFromCenter(x, y, x+radius, y+radius);
		g2.setPaint(Color.YELLOW);
		g2.fill(face);
		
		//左眼绘制
		Ellipse2D eyeL = new Ellipse2D.Double();
		eyeL.setFrameFromCenter(x-80, y-80, x-80+20, y-80+20);
		g2.setPaint(Color.BLACK);
		g2.fill(eyeL);
		
		//绘制右眼
		Ellipse2D eyeR = new Ellipse2D.Double();
		eyeR.setFrameFromCenter(x+70, y-80, x+70+20, y-80+20);
		g2.setPaint(Color.BLACK);
		g2.fill(eyeR);
		
		//绘制嘴巴，使用弧形
		Arc2D mouth = new Arc2D.Double(x-130, y-150, 270, 270, 0, -180, Arc2D.OPEN);
		//设置嘴巴线条宽度10像素，圆角
		g2.setStroke(new BasicStroke(10, 
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2.draw(mouth);
		
		//在矩形中绘制字符串
		String str=new String("Smiling~");
		Font font =new Font("Segoe UI", Font.BOLD, 30);
		g2.setFont(font);
		//得到矩形对象
		FontRenderContext frc = g2.getFontRenderContext();
		Rectangle2D rec= font.getStringBounds(str, frc);
		
		//确定矩形外框，使矩形随窗体大小改变
		double x1 = (this.getWidth()-rec.getWidth())/2;
		double y1 = (this.getHeight()-rec.getHeight())*12/13;
		g2.setPaint(Color.GREEN);
		g2.drawString(str, (int)x1, (int)(y1+50));
	
	}
	
	

}
