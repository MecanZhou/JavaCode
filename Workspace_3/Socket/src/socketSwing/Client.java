package socketSwing;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;

import org.omg.CORBA.PUBLIC_MEMBER;

public class Client extends JFrame{
	
	JPanel jPanel_1;
	private static JTextArea jTextArea;
	private JTextField jTextField;
	JButton jButton;
	static Socket socket;
	static String infoString="";
	
	public Client() {
		
		jPanel_1= new JPanel();
		jTextArea = new JTextArea();
		jTextField = new JTextField();
		jTextField.setColumns(15);
		jButton = new JButton("Send");
		
		jPanel_1.add(jTextField);
		jPanel_1.add(jButton);
		
		getContentPane().add(jTextArea, BorderLayout.CENTER);
		getContentPane().add(jPanel_1,BorderLayout.SOUTH);
		
		this.setSize(300, 400);
		//动态设置对话框位置，使其保持在屏幕中间
		int windowWidth= this.getWidth();
		int windowHeight= this.getHeight();
		Toolkit kit = Toolkit.getDefaultToolkit(); 
		Dimension screenSize = kit.getScreenSize(); 
		int screenWidth = screenSize.width; 
		int screenHeight = screenSize.height; 
		this.setLocation(screenWidth/2-windowWidth/2-150, screenHeight/2-windowHeight/2);
		this.setTitle("Client");
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		
		jButton.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent arg0) {
				
			String cstr=jTextField.getText();
			PrintWriter cos= null;
			try {
				cos= new PrintWriter(socket.getOutputStream(),true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			cos.println(cstr);
			jTextField.setText("");
			infoString= infoString+"Client: "+cstr+"\n";
			jTextArea.setText(infoString);
			}
		});
	}
	public static void main(String[] args) throws UnknownHostException, IOException {
			Client client=new Client();		
		
			socket=new Socket("localhost", 8888);
			String socketAdress=socket.getInetAddress().getHostAddress();
			System.out.println("***Linked with Server***");
			jTextArea.setText("Linked with Server,now you can communicate with it!");
		
		while(true){
			try {
				BufferedReader cis=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String s=cis.readLine();
				infoString=infoString+"Server:"+s+"\n";
				jTextArea.setText(infoString);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
