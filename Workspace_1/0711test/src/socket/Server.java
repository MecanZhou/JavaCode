package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		ServerSocket server = null;
		try {
			server = new ServerSocket(8888);
			Socket socket = null;
			System.out.println("***服务器即将启动，等待客户端连接***");
			socket = server.accept();
			InetAddress address = socket.getInetAddress();
			System.out.println("***客户端已连接，现在可以进行通讯***");
			
		
//			String line;
//			line = sin.readLine();
			
			BufferedReader is=null;
			while(true){
				BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
			    String str=sin.readLine();
				PrintWriter os = new PrintWriter(socket.getOutputStream());
				os.println(str);
				System.out.println("Server:"+str);
				os.flush();
				
				 is= new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String str1=is.readLine();
				System.out.println("Client("+address.getHostAddress()+"):"+str1);
//				line = sin.readLine();
			}
//			System.out.println("***服务器终止连接***");
//			os.close();
//			is.close();
//			socket.close();
//			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
