package com;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;

public class FTPtest {

	public static String server;
	public static String user;
	public static String password;
	
	private static FTPClient ftpClient;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FTPtest ftpTest=new FTPtest();
		server="172.16.73.100";
		user="admin";
		password="admin";
		
		ftpTest.getConnection();
		try {
			ftpTest.Download();
			System.out.println("成功。。。");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("失败。。。");
		}

	}
	
	public void getConnection()
	{
		ftpClient=new FTPClient();
		
		try {
			
			ftpClient.connect(server,21);
			ftpClient.login(user, password);
			ftpClient.setBufferSize(1024);
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			//ftpClient.setControlEncoding("utf8"); 
			System.out.println("FTP连接成功");

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("FTP服务器连接失败",e);
		}
	}
	public void Download() throws IOException
	{
		//getConnection();
		ftpClient.changeWorkingDirectory("/sim-info/"+"simulation-test"+"");
		//ftpClient.changeWorkingDirectory("");
		String sourceFile="Model50/som.xml";
		FileOutputStream fos=new FileOutputStream("F:/cun/wu33kai.xml");
		
		
		try{
			ftpClient.retrieveFile(sourceFile,fos);
			
			ftpClient.retrieveFile("",fos);
			
			System.out.println("下载成功");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("下载失败！！！");
		}
		ftpClient.disconnect();
		
	}

}
