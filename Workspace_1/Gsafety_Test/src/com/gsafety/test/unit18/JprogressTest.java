package com.gsafety.test.unit18;

import javax.swing.*;
import java.awt.*;

public class JprogressTest extends JFrame {

    private JPanel jPanel;
    private JProgressBar jProgressBar;
    private Thread thread;
    private static final String METAL = "javax.swing.plaf.metal.MetalLookAndFeel";
    private static final String	MOTIF = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
    private static final String	NIMBUS = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
    private static final String WINDOWS = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

    public JprogressTest() throws HeadlessException {

        Toolkit kit= Toolkit.getDefaultToolkit();
        Dimension screenSize=kit.getScreenSize();
        int screenWidth=screenSize.width;
        int screenHeight= screenSize.height;

        setSize(screenWidth/2-300, screenHeight/2-160);
        setLocation(screenWidth/2-getWidth()/2, screenHeight/2-getWidth()/2);
        try {
            UIManager.setLookAndFeel(WINDOWS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        jProgressBar = new JProgressBar();
        jProgressBar.setStringPainted(true);

        jPanel = new JPanel(null);
        getContentPane().add(jPanel,BorderLayout.CENTER);
        jPanel.add(jProgressBar);
        jProgressBar.setBounds(0,this.getHeight()/2-40,this.getWidth(), 20);

        thread = new Thread(new Runnable() {
            int count = 0;
            @Override
            public void run() {
                while (true){
                    jProgressBar.setValue(++count);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        thread.start();
    }

    //初始化窗口
    public static void init(JFrame jFrame){

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }

    public static void main(String[] args) {
        init(new JprogressTest());
    }
}
