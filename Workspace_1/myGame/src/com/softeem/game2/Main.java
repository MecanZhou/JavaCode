package com.softeem.game2;

import javax.swing.JFrame;

/**
 * ������
 * @author Administrator
 *
 */
public class Main extends JFrame{

	public Main()
	{
		this.add(new GamePanel());
		this.setTitle("�ɻ���սv1.0");//���ñ���
		this.setSize(450, 700);//���ô����С
		this.setResizable(false);//���ô����ֹ�ı��С
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//������ر�ʱ�Զ��˳�java�����
		this.setLocationRelativeTo(null);//���ô�����ʾ�����λ��:Ĭ����Ļ���м�
		this.setVisible(true);//��ʾ����
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
