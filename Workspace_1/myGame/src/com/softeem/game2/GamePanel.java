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
 * 游戏的主界面(绘制游戏元素:背景，敌机，子弹，玩家飞机..)
 * @author Administrator
 *
 */
public class GamePanel extends JPanel implements MouseMotionListener{

	BufferedImage bg;//背景图片对象
	Hero hero = new Hero();
	Timer timer;//声明定时器
	//声明一个存储所有子弹的集合对象
	static ArrayList<Bullet> bs = new ArrayList<Bullet>();
	
	public GamePanel() {
		//为面板添加鼠标事件
		addMouseMotionListener(this);
		bg = ImageLoader.load("background.png");
		timer = new Timer();
		timer.schedule(new RefreshTask(),0,100);
	}
	
	class RefreshTask extends TimerTask{
		@Override
		public void run() {
			//界面重绘
			repaint();
		}
	}
	
	//绘制方法
	@Override
	public void paint(Graphics g) {
		//绘制背景
		g.drawImage(bg, 0, 0, null);
		//绘制玩家飞机
		g.drawImage(hero.img,hero.x,hero.y,hero.width,hero.height,null);
		//绘制子弹
		for(int i = 0;i<bs.size();i++)
		{
			Bullet b = bs.get(i); 
			g.drawImage(b.img, b.x, b.y,b.width,b.height,null);
		}
	}

	//鼠标拖拽
	@Override
	public void mouseDragged(MouseEvent e) {
		System.out.println("别拽，没啥用!");
	}
	//鼠标移动
	@Override
	public void mouseMoved(MouseEvent e) {
		//改变飞机位置
		int x = e.getX();
		int y = e.getY();
		System.out.println("鼠标移动:"+x+","+y);
		hero.move(x, y);
		//立即重绘
//		repaint();
	}
	
	
}
