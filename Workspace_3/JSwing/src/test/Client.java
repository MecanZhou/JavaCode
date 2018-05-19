package test;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends JFrame {
	private JTextField textField;
	private static JTextArea textArea;
	static Socket socket;
	static String info="";
	public Client() {
		
		textArea = new JTextArea();
		getContentPane().add(textArea, BorderLayout.CENTER);
		
		textField = new JTextField();
		getContentPane().add(textField, BorderLayout.SOUTH);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Send");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PrintWriter cos=null;
				try {
					 cos= new PrintWriter(socket.getOutputStream(),true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String cstr=textField.getText();
				cos.println(cstr);
				textField.setText("");
				info=info+"Client:"+cstr+"\n";
				textArea.setText(info);
			}
		});
		getContentPane().add(btnNewButton, BorderLayout.EAST);
	}

	public static void main(String[] args) throws IOException {
		Client frame= new Client();
		frame.setBounds(400, 0, 400, 400);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.show();
		
		socket= new Socket("127.0.0.1", 8888);
		System.out.println("***已与服务器连接，可以进行通讯***");
		textArea.setText("***已与服务器连接，可以进行通讯***");
		
		while (true) {
			BufferedReader sis=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String sstr=sis.readLine();
			info=info+"Server:"+sstr+"\n";
			textArea.setText(info);
		}
	}

}
