package com.softeem.game2;

import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 玩家飞机
 * @author Administrator
 */
public class Hero {

	BufferedImage img;	//用于渲染玩家飞机的图片对象
	int x;		//飞机绘制的x轴
	int y;		//飞机绘制的y轴
	int width;	//飞机的宽度
	int height;	//飞机的高度
	Timer timer;//声明定时器
	int flag = 1; //飞机飞行的标志位
	
	public Hero() {
		//加载图片
		img = ImageLoader.load("hero1.png");
		width = img.getWidth() / 2;//获取图片的原始宽度
		height = img.getHeight() / 2;//获取图片的原始高度
		y = 700 - height * 2;
		x = (450 - width)/2;
		
		//创建定时器
		timer = new Timer();
		//启动定时任务
		timer.schedule(new FlyTask(), 0,200);
		//启动子弹发射任务
		timer.schedule(new ShootTask(), 0, 200);
	}
	
	class ShootTask extends TimerTask{
		@Override
		public void run() {
			//产生子弹
			Bullet b = new Bullet(x+width/2,y);
			//将子弹装入集合
			GamePanel.bs.add(b);
			//启动子弹飞行线程
			b.start();
		}
	}
	
	//定时任务类(内部类)
	class FlyTask extends TimerTask{
		@Override
		public void run() {
			if(flag == 1){
				flag = 2;
			}else{
				flag = 1;
			}
			//在两张图片之间来回切换
			img = ImageLoader.load("hero"+flag+".png");
		}
	}
	
	public void move(int x,int y)
	{
		this.x = x - width/2;
		this.y = y - height/2;
	}
}
