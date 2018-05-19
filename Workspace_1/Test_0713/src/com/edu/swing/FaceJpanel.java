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

		//ȷ�����곣��
		double x=this.getWidth()/2;
		double y=this.getHeight()/2;
		double radius=180;
		
		//����2Dͼ�ι���
		Graphics2D g2= (Graphics2D) arg0;
		
		//����������ʹ��һ��Բ�Σ���Բ�ĺͰ뾶ȷ��
		Ellipse2D face = new Ellipse2D.Double();
		face.setFrameFromCenter(x, y, x+radius, y+radius);
		g2.setPaint(Color.YELLOW);
		g2.fill(face);
		
		//���ۻ���
		Ellipse2D eyeL = new Ellipse2D.Double();
		eyeL.setFrameFromCenter(x-80, y-80, x-80+20, y-80+20);
		g2.setPaint(Color.BLACK);
		g2.fill(eyeL);
		
		//��������
		Ellipse2D eyeR = new Ellipse2D.Double();
		eyeR.setFrameFromCenter(x+70, y-80, x+70+20, y-80+20);
		g2.setPaint(Color.BLACK);
		g2.fill(eyeR);
		
		//������ͣ�ʹ�û���
		Arc2D mouth = new Arc2D.Double(x-130, y-150, 270, 270, 0, -180, Arc2D.OPEN);
		//��������������10���أ�Բ��
		g2.setStroke(new BasicStroke(10, 
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2.draw(mouth);
		
		//�ھ����л����ַ���
		String str=new String("Smiling~");
		Font font =new Font("Segoe UI", Font.BOLD, 30);
		g2.setFont(font);
		//�õ����ζ���
		FontRenderContext frc = g2.getFontRenderContext();
		Rectangle2D rec= font.getStringBounds(str, frc);
		
		//ȷ���������ʹ�����洰���С�ı�
		double x1 = (this.getWidth()-rec.getWidth())/2;
		double y1 = (this.getHeight()-rec.getHeight())*12/13;
		g2.setPaint(Color.GREEN);
		g2.drawString(str, (int)x1, (int)(y1+50));
	
	}
	
	

}
