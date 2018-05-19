package com.edu.swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class FirstJpanel extends JPanel {

	@Override
	protected void paintComponent(Graphics g) {
		// TODO 自动生成的方法存根
		super.paintComponent(g);
		
		//支持2D图形绘制
		Graphics2D g2= (Graphics2D) g;
		//初始化字体
		Font font= new Font("微软雅黑", Font.BOLD, 20);
		g2.setFont(font);
		
		//初始化颜色
//		Color color = new Color(200, 200, 200);
		g2.setColor(Color.YELLOW);
		
		//绘制字符串
		g2.drawString("欢迎进入SMCS,JHUN", 30, 20);
		
		//绘制矩形
		Rectangle2D rec= new  Rectangle2D.Double(30, 30, 200, 50);
		Rectangle2D rec2=new  Rectangle2D.Float(30, 90, 200, 50);
		Rectangle2D rec3=new  Rectangle2D.Double(30, 150, 200, 50);
		Rectangle2D rec4=new  Rectangle2D.Double(30, 210, 200, 50);
		
		//绘制边框
		g2.draw(rec);
		//绘制填充
		g2.fill(rec2);
		
		//改变边框样式
		g2.setStroke(new BasicStroke(3, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
		g2.draw(rec3);
		
		//绘制渐变
		Paint paint = new GradientPaint(30, 210, Color.WHITE, 160, 210, Color.YELLOW);
		g2.setPaint(paint);
		g2.fill(rec4);
	}
	
	

}
