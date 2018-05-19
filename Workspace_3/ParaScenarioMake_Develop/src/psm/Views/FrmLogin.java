package psm.Views;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import com.sun.awt.AWTUtilities;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

import psm.Component.BackgroundPanel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;
import java.awt.Font;

public class FrmLogin extends JFrame {
    
    private JPanel contentPane;
    private JTextField userText;
    private JTextField passwordText;
    private JTextField verificationText;
    
    private Point pressedPoint;
    private boolean isDraging=false;
    private JLabel codeLabel;

    
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FrmLogin frame = new FrmLogin();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    /**
     * Create the frame.
     */
    public FrmLogin() {
        setBounds(100, 100, 500, 350);// ���ô���λ��
        setUndecorated(true);
        AWTUtilities.setWindowOpaque(this, false);//���ô����͸����
        validate();//ȷ�����������Ч�Ĳ���
        
        
        contentPane = new JPanel();// �����������
        setContentPane(contentPane);// ���ô����������
        contentPane.setLayout(new BorderLayout(0, 0));
        
        BackgroundPanel backgroundPanel = new BackgroundPanel();// �����������
        backgroundPanel.setImage(getToolkit().getImage(getClass().getResource("/psm/Image/login.png")));// ������屳��ͼƬ
        contentPane.add(backgroundPanel);// �ѱ��������ӵ������������
        contentPane.setOpaque(false);
        
       //���������ƶ�
        backgroundPanel.addMouseListener(new MouseAdapter(){
        	public void mousePressed(MouseEvent e){
        		if(e.getButton()==e.BUTTON1){
        		isDraging=true;
        		pressedPoint = e.getPoint();// ��¼�������
        		}
        	}
        	
        	public void mouseReleased(MouseEvent E){
        		isDraging=false;
        	}
        });
        
        backgroundPanel.addMouseMotionListener(new MouseMotionAdapter(){
        	public void mouseDragged(MouseEvent e){
        		if(isDraging){
        			Point point = e.getPoint();// ��ȡ��ǰ����
        	        Point locationPoint = getLocation();// ��ȡ��������
        	        int x = locationPoint.x + point.x - pressedPoint.x;// �����ƶ����������
        	        int y = locationPoint.y + point.y - pressedPoint.y;
        	        setLocation(x, y);// �ı䴰��λ��
        		}
        	}
        });
        
        
        setVisible(true);
        
        
        userText = new JTextField();
        userText.setBounds(182, 193, 167, 21);
        backgroundPanel.add(userText);
        userText.setColumns(10);
        
        passwordText = new JTextField();
        passwordText.setBounds(182, 225, 167, 21);
        backgroundPanel.add(passwordText);
        passwordText.setColumns(10);
        
        verificationText = new JTextField();
        verificationText.setBounds(182, 256, 84, 21);
        backgroundPanel.add(verificationText);
        verificationText.setColumns(10);
        
        codeLabel = new JLabel("");
        codeLabel.setFont(new Font("Adobe Naskh Medium", Font.PLAIN, 12));
        codeLabel.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
        		if(e.getButton()==e.BUTTON1){
        			DrawAuthCode();
        		}
        	}
        });
        codeLabel.setBackground(Color.YELLOW);
        codeLabel.setBounds(295, 259, 54, 15);
        //codeLabel.
        codeLabel.setVisible(true);
        backgroundPanel.add(codeLabel);
        
        JButton sureBtn = new JButton("\u786E\u5B9A");
        sureBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(verificationText.getText().equals("")){
        			JOptionPane.showMessageDialog(new JPanel(), "��������֤�룡","��ʾ",JOptionPane.YES_NO_CANCEL_OPTION);
        		}
        		else{
        		 FrmMain window = new FrmMain();
        		//window.start();
                dispose();
        		
        		}
        	}
        });
        sureBtn.setBounds(182, 284, 84, 23);
        backgroundPanel.add(sureBtn);
        
        JButton cancelBtn = new JButton("\u53D6\u6D88");
        cancelBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		dispose();
        	}
        });
        cancelBtn.setBounds(270, 284, 79, 23);
        backgroundPanel.add(cancelBtn);
        
        DrawAuthCode();
      
        
        
    }

    public void DrawAuthCode(){
    	 Random rd = new Random();
    	 String str="1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    	 String code="";
 		 for(int i=0;i<4;i++){
 			int index=rd.nextInt(str.length()-1);
 			String rand=str.substring(index, index+1);
 			code+=rand;
 		 }
 		 codeLabel.setText(code);
    }
    }
