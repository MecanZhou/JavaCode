package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;

public class Client{

	public static void main(String[] args) {
			try {
			Socket socket = new Socket("172.16.73.162",8888);
			InetAddress address = socket.getInetAddress();
			System.out.println("***������������ӣ����ڿ��Խ���ͨѶ***");
			
			//������̨����д��socket�ܵ�
			
			
			BufferedReader is = null;
			while(true){
				BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
				String str=sin.readLine();
				PrintWriter os = new PrintWriter(socket.getOutputStream());
				os.println(str);
				System.out.println("Client("+address.getHostAddress()+"):"+str);
				os.flush();
				
				
				 is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String str1=is.readLine();
				
				System.out.println("Server:"+str1);
//				readline = sin.readLine();
			}
			
			
//			System.out.println("***����������Ͽ�����***");
//			os.close();
//			is.close();
//			socket.close();
			} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
