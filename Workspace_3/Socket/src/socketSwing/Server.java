package socketSwing;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.*;

public class Server extends JFrame{

	JPanel jPanel_1;
	private static JTextArea jTextArea;
	private JTextField jTextField;
	JButton jButton;
	static Socket socket;
	static String infoString="";
	
	public Server() {	
		
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
		//��̬���öԻ���λ�ã�ʹ�䱣������Ļ�м�
		int windowWidth= this.getWidth();
		int windowHeight= this.getHeight();
		Toolkit kit = Toolkit.getDefaultToolkit(); 
		Dimension screenSize = kit.getScreenSize(); 
		int screenWidth = screenSize.width; 
		int screenHeight = screenSize.height; 
		this.setLocation(screenWidth/2-windowWidth/2+150, screenHeight/2-windowHeight/2);
		this.setTitle("Server");
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		
		jButton.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent arg0) {
				
			String sstr=jTextField.getText();
			PrintWriter sos= null;
			try {
				sos= new PrintWriter(socket.getOutputStream(),true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sos.println(sstr);
			jTextField.setText("");
			infoString= infoString+"Server: "+sstr+"\n";
			jTextArea.setText(infoString);
			}
		});
	}
	
	public static void main(String[] args) throws IOException {
		
		Server server =new Server();
		
		try {
			ServerSocket serverSocket = new ServerSocket(8888);
			System.out.println("***The Port is openning***");
			jTextArea.setText("Waitting for linking now...");
			socket=serverSocket.accept();
			System.out.println(socket.getInetAddress().getHostAddress()+"was linking...");
			jTextArea.setText(socket.getInetAddress().getHostAddress()+"was linking...");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (true) {
			String sstr;
			
				BufferedReader sis= new BufferedReader(new InputStreamReader(socket.getInputStream()));
				sstr = sis.readLine();
				infoString=infoString+"Clinet("+socket.getInetAddress().getHostAddress()+"):"+sstr+"\n";
				jTextArea.setText(infoString);
			
		}

	}

}
