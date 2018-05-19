package SocketTest;
import java.io.*;
import java.net.*;
public class Client2{
	public static void main(String args[]){
		try{
				Socket socket = new Socket("172.16.73.162",4700);
				BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
				BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter os = new PrintWriter(socket.getOutputStream());
				String readline;
				readline = sin.readLine();
				while(!readline.equals("bye")){
					os.println(readline);
					os.flush();
					System.out.println("Client2:"+readline);
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
