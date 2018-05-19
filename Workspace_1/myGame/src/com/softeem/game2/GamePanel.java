package com.softeem.game2;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * ��Ϸ��������(������ϷԪ��:�������л����ӵ�����ҷɻ�..)
 * @author Administrator
 *
 */
public class GamePanel extends JPanel implements MouseMotionListener{

	BufferedImage bg;//����ͼƬ����
	Hero hero = new Hero();
	Timer timer;//������ʱ��
	//����һ���洢�����ӵ��ļ��϶���
	static ArrayList<Bullet> bs = new ArrayList<Bullet>();
	
	public GamePanel() {
		//Ϊ����������¼�
		addMouseMotionListener(this);
		bg = ImageLoader.load("background.png");
		timer = new Timer();
		timer.schedule(new RefreshTask(),0,100);
	}
	
	class RefreshTask extends TimerTask{
		@Override
		public void run() {
			//�����ػ�
			repaint();
		}
	}
	
	//���Ʒ���
	@Override
	public void paint(Graphics g) {
		//���Ʊ���
		g.drawImage(bg, 0, 0, null);
		//������ҷɻ�
		g.drawImage(hero.img,hero.x,hero.y,hero.width,hero.height,null);
		//�����ӵ�
		for(int i = 0;i<bs.size();i++)
		{
			Bullet b = bs.get(i); 
			g.drawImage(b.img, b.x, b.y,b.width,b.height,null);
		}
	}

	//�����ק
	@Override
	public void mouseDragged(MouseEvent e) {
		System.out.println("��ק��ûɶ��!");
	}
	//����ƶ�
	@Override
	public void mouseMoved(MouseEvent e) {
		//�ı�ɻ�λ��
		int x = e.getX();
		int y = e.getY();
		System.out.println("����ƶ�:"+x+","+y);
		hero.move(x, y);
		//�����ػ�
//		repaint();
	}
	
	
}
