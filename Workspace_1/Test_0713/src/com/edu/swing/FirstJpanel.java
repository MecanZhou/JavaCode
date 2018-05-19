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
		// TODO �Զ����ɵķ������
		super.paintComponent(g);
		
		//֧��2Dͼ�λ���
		Graphics2D g2= (Graphics2D) g;
		//��ʼ������
		Font font= new Font("΢���ź�", Font.BOLD, 20);
		g2.setFont(font);
		
		//��ʼ����ɫ
//		Color color = new Color(200, 200, 200);
		g2.setColor(Color.YELLOW);
		
		//�����ַ���
		g2.drawString("��ӭ����SMCS,JHUN", 30, 20);
		
		//���ƾ���
		Rectangle2D rec= new  Rectangle2D.Double(30, 30, 200, 50);
		Rectangle2D rec2=new  Rectangle2D.Float(30, 90, 200, 50);
		Rectangle2D rec3=new  Rectangle2D.Double(30, 150, 200, 50);
		Rectangle2D rec4=new  Rectangle2D.Double(30, 210, 200, 50);
		
		//���Ʊ߿�
		g2.draw(rec);
		//�������
		g2.fill(rec2);
		
		//�ı�߿���ʽ
		g2.setStroke(new BasicStroke(3, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
		g2.draw(rec3);
		
		//���ƽ���
		Paint paint = new GradientPaint(30, 210, Color.WHITE, 160, 210, Color.YELLOW);
		g2.setPaint(paint);
		g2.fill(rec4);
	}
	
	

}
