package test;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends JFrame{
	private JTextField textField;
	private static JTextArea textArea;
	static Socket socket;
	static String info="";
	public Server() {
		
		textArea = new JTextArea();
		getContentPane().add(textArea, BorderLayout.CENTER);
		
		textField = new JTextField();
		getContentPane().add(textField, BorderLayout.SOUTH);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Send");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PrintWriter sos =null;
				try {
					sos = new PrintWriter(socket.getOutputStream(),true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String sstr= textField.getText();
//				if(sstr!="bye"){
					sos.println(sstr);
					textField.setText("");
					info=info+"Server:"+sstr+"\n";
					textArea.setText(info);
//				}
			}
		});
		getContentPane().add(btnNewButton, BorderLayout.EAST);
	}

	public static void main(String[] args) throws IOException {
		Server frame= new Server();
		frame.setBounds(0, 0, 400, 400);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.show();
		
		ServerSocket server=new ServerSocket(8888);
		System.out.println("***服务器端口已开启，等待客户端连接***");
		textArea.setText("***服务器端口已开启，等待客户端连接***");
		socket=server.accept();
		System.out.println(socket.getInetAddress().getHostAddress()+"已与服务器连接");
		textArea.setText(socket.getInetAddress().getHostAddress()+"已与服务器连接");
		
		while (true) {
			BufferedReader cis= new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String cstr= cis.readLine();
			info=info+"Clinet:"+cstr+"\n";
			textArea.setText(info);
		}
	}
}
