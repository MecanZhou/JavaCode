package com.softeem.game2;

import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ��ҷɻ�
 * @author Administrator
 */
public class Hero {

	BufferedImage img;	//������Ⱦ��ҷɻ���ͼƬ����
	int x;		//�ɻ����Ƶ�x��
	int y;		//�ɻ����Ƶ�y��
	int width;	//�ɻ��Ŀ��
	int height;	//�ɻ��ĸ߶�
	Timer timer;//������ʱ��
	int flag = 1; //�ɻ����еı�־λ
	
	public Hero() {
		//����ͼƬ
		img = ImageLoader.load("hero1.png");
		width = img.getWidth() / 2;//��ȡͼƬ��ԭʼ���
		height = img.getHeight() / 2;//��ȡͼƬ��ԭʼ�߶�
		y = 700 - height * 2;
		x = (450 - width)/2;
		
		//������ʱ��
		timer = new Timer();
		//������ʱ����
		timer.schedule(new FlyTask(), 0,200);
		//�����ӵ���������
		timer.schedule(new ShootTask(), 0, 200);
	}
	
	class ShootTask extends TimerTask{
		@Override
		public void run() {
			//�����ӵ�
			Bullet b = new Bullet(x+width/2,y);
			//���ӵ�װ�뼯��
			GamePanel.bs.add(b);
			//�����ӵ������߳�
			b.start();
		}
	}
	
	//��ʱ������(�ڲ���)
	class FlyTask extends TimerTask{
		@Override
		public void run() {
			if(flag == 1){
				flag = 2;
			}else{
				flag = 1;
			}
			//������ͼƬ֮�������л�
			img = ImageLoader.load("hero"+flag+".png");
		}
	}
	
	public void move(int x,int y)
	{
		this.x = x - width/2;
		this.y = y - height/2;
	}
}
