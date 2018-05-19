package SocketTest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
public class Server{
	public static void main(String args[]){
		try{
			ServerSocket server = null;
			try{
				server = new ServerSocket(4700);
			}catch(Exception e){
				System.out.println("Can not listen to:"+e);
			}
			Socket socket = null;
			int count = 0;
			System.out.println("***服务器即将启动，等待客户端连接***");
			while(true){
					socket = server.accept();
					count++;
					System.out.println("当前客户端数量："+count);
					serverThread sT = new serverThread();
					sT.start();
			
//			String line;
//			BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
//			BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//			PrintWriter os = new PrintWriter(socket.getOutputStream());
//			System.out.println("Client:"+is.readLine());
//			line = sin.readLine();
//			while(!line.equals("bye")){
//				os.println(line);
//				os.flush();
//				System.out.println("Server:"+line);
//				System.out.println("Client:"+is.readLine());
//				line = sin.readLine();
//			}
//			os.close();
//			is.close();
//			socket.close();
//			server.close();
			}
		}catch(Exception e){
			System.out.println("Error"+e);
		}
	}	
}