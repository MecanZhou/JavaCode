package com.softeem.game;

import java.io.IOException;

import com.softeem.plane.PlaneGame;


/**
 * java:�������
 * 	    IO��(��ȡ�ز�)
 * 		���߳�(�����ӵ������ֵл�����Ļ�������ɻ��˶�Ч��)
 * 		�������
 * 		ͼ�ν���(Swing��AWT)
 * 
 * @author Administrator
 *
 */
public class MyGame {

	public static void main(String[] args) throws IOException
	{
		//������Ϸ����
		PlaneGame pg = new PlaneGame();
		pg.setTitle("ȫ���ɻ�");//������Ϸ����
		pg.showGameBg();//��ʾ��Ϸ����
		pg.showMyPlane();//��ʾ��ҷɻ�
		pg.showEnemys();//��ʾ�л�
		pg.startGame();//������Ϸ
	}
}
