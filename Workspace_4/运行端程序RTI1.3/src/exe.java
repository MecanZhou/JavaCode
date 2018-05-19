import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Timer;

import org.omg.CORBA.Request;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class exe {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static MulticastSocket ms;
	public static InetAddress group;
	String str1=null;
	public static boolean downloadFlag=false;
	public static  boolean multicastflag=false;
	public static String str=null;
	public static ArrayList<String>route=new ArrayList<String>();
	public static String ip=null;
	public static void main(String[] args) throws IOException, InterruptedException {
//		RunLinkFTP runftp=new RunLinkFTP();
		Timer timer = new Timer();     
        timer.schedule(new MyTask(), 1000, 2000000);
		

		try {
			group = InetAddress.getByName("228.5.6.7");
			try {
				ms = new MulticastSocket(6789);
				ms.joinGroup(group);
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("接收多播中......");
		recv();
		
	
	}
//	  
	private static void recv() {
		// TODO Auto-generated method stub
	
		while(true)
		{	
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			byte[] buf = new byte[65535];
			DatagramPacket recv = new DatagramPacket(buf,buf.length);
	
			try {
				
				ms.receive(recv);
				str="";
				str= new String (recv.getData(),0,recv.getLength());
				System.out.println("serverIP ->:"+str);
				
				if(!str.equals("")){
					multicastflag = true;
					System.out.println("true");
					new Thread(new RunLinkFTP()).start();//每一次都启动一个线程进行文件下载操作
					//break;
				}
			} catch (IOException e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	}
	static class MyTask extends java.util.TimerTask{      
        public void run(){     
        	new Thread(new WindowsInfo()).start();   
        }     
	}
}


