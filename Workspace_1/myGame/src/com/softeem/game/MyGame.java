package com.softeem.game;

import java.io.IOException;

import com.softeem.plane.PlaneGame;


/**
 * java:面向对象
 * 	    IO流(读取素材)
 * 		多线程(发射子弹，出现敌机，屏幕滚动，飞机运动效果)
 * 		任务调度
 * 		图形界面(Swing，AWT)
 * 
 * @author Administrator
 *
 */
public class MyGame {

	public static void main(String[] args) throws IOException
	{
		//创建游戏对象
		PlaneGame pg = new PlaneGame();
		pg.setTitle("全民打飞机");//设置游戏标题
		pg.showGameBg();//显示游戏背景
		pg.showMyPlane();//显示玩家飞机
		pg.showEnemys();//显示敌机
		pg.startGame();//启动游戏
	}
}
