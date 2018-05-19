package SocketTest;
import java.awt.BorderLayout;
import java.io.*;
import java.net.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
//public class Client1 extends JFrame{
//	public Client1 (){
//		JFrame jFrame=new JFrame();
//		JPanel panel1=new JPanel();
//		JPanel panel2=new JPanel();
//		JTextArea area=new JTextArea();
//		JTextField field=new JTextField();
//		JButton button=new JButton("·¢ËÍ");
//		
//		jFrame.setBounds(0, 0, 300, 300);
//		jFrame.setLocationRelativeTo(null);
//		jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
//		
//		jFrame.add(panel1,BorderLayout.NORTH);
//		jFrame.add(panel2,BorderLayout.SOUTH);
//		
//		
//	}
public class Client1{
	public static void main(String args[]){
		try{
//		        Client1 client1=new Client1();
				Socket socket = new Socket("172.16.73.162",4700);
				BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
				BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter os = new PrintWriter(socket.getOutputStream());
				String readline;
				readline = sin.readLine();
				while(!readline.equals("bye")){
					os.println(readline);
					os.flush();
					System.out.println("Client1:"+readline);
					System.out.println("Server:"+is.readLine());
					readline = sin.readLine();
				}
				os.close();
				is.close();
				socket.close();
		}catch(Exception e) {
			System.out.println("Error"+e);
		}
	}
}
