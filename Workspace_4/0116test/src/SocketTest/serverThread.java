package SocketTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class serverThread extends Thread {
	Socket socket = null;
	ServerSocket server = null;
	public  void severThread(Socket socket){
		this.socket = socket;
	}
	public void run(){
		String line = null;
		BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader is = null;
		try {
			is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e3) {
			// TODO 自动生成的 catch 块
			e3.printStackTrace();
		}
		InetAddress address = socket.getInetAddress();
		
				PrintWriter os = null;
				try {
					os = new PrintWriter(socket.getOutputStream());
				} catch (IOException e2) {
					// TODO 自动生成的 catch 块
					e2.printStackTrace();
				}
		
		try {
			System.out.println("Client("+address.getHostAddress()+"):"+is.readLine());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			line = sin.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(!line.equals("bye")){
			os.println(line);
			os.flush();
			System.out.println("Server:"+line);
			try {
				System.out.println("Client("+address.getHostAddress()+"):"+is.readLine());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				line = sin.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(os!=null)
				os.close();
				if(is!=null)
				try {
					is.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(socket!=null)
					try {
						socket.close();
					} catch (IOException e) {
					e.printStackTrace();
					} 
				if(server!=null)
					try {
						server.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
	}
}
