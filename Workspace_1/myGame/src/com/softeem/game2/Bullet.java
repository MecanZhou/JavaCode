package com.softeem.game2;

import java.awt.image.BufferedImage;

/**
 * �ӵ��ࣨ�̣߳�
 * @author Administrator
 */
public class Bullet extends Thread{

	BufferedImage img;
	int x;
	int y;
	int width;
	int height;
	int speed = 25;//�ӵ������ٶ�
	
	public Bullet(int x,int y){
		img = ImageLoader.load("bullet1.png");
		width = img.getWidth() / 2;
		height = img.getHeight() / 2;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void run() {
		while(true){
			y = y - speed;
			//�ж��ӵ��Ƿ��Ѿ��Ƴ�����Ļ���
			if(y < 0){
				//�Ӽ������Ƴ��ӵ�
				GamePanel.bs.remove(Bullet.this);
				break;
			}
			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
