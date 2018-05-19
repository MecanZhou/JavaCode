package com.gsafety.test.unit18;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ThreadAndSwing extends JFrame {

    private Thread thread;
    private JButton jb1 , jb2;
    private static int count = 1;
    private static boolean flag = false;

    public static boolean isFlag() {
        return flag;
    }

    public static void setFlag(boolean flag) {
        ThreadAndSwing.flag = flag;
    }

    public ThreadAndSwing() throws HeadlessException {

        //初始化并添加按钮到面板中
        jb1 = new JButton("开始");
        jb2 = new JButton("结束");

        this.getContentPane().add(jb1,BorderLayout.WEST);
        this.getContentPane().add(jb2,BorderLayout.EAST);

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
               while (true){
                   synchronized ("") {
                       if (isFlag() == true) {
                           System.out.println("开始在控制台第" + count + "次打印本段文字，按“结束”停止打印！");
                           count++;
                           try {
                               Thread.sleep(2000);
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                           }
                       }
                   }
                }
            }
        });
        thread.start();

        jb1.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("开始打印！");
                setFlag(true);
            }
        });

        jb2.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFlag(false);
                System.out.println("暂停打印！");
            }
        });
    }

    //初始化窗口
    public static void init(JFrame jFrame){
        jFrame.setBounds(500,500,200,100);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }

    //主运行类
    public static void main(String[] args) {
        ThreadAndSwing.init(new ThreadAndSwing());
    }
}
