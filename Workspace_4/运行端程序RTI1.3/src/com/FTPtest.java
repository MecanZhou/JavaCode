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
			System.out.println("�ɹ�������");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ʧ�ܡ�����");
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
			System.out.println("FTP���ӳɹ�");

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("FTP����������ʧ��",e);
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
			
			System.out.println("���سɹ�");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("����ʧ�ܣ�����");
		}
		ftpClient.disconnect();
		
	}

}
