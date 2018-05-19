package com.softeem.game2;

import java.awt.image.BufferedImage;

/**
 * 子弹类（线程）
 * @author Administrator
 */
public class Bullet extends Thread{

	BufferedImage img;
	int x;
	int y;
	int width;
	int height;
	int speed = 25;//子弹飞行速度
	
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
			//判断子弹是否已经移出到屏幕最顶部
			if(y < 0){
				//从集合中移除子弹
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
