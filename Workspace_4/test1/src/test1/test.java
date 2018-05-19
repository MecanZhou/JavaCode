package test1;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
public class test {
	public static void main(String[] args) throws IOException, InterruptedException {
		FTPClient ftpClient=new FTPClient();
		FileOutputStream fos=null;
		File downloadFile =null;
      try {
			
			ftpClient.connect("172.16.73.162");
			ftpClient.login("admin", "admin");
			ftpClient.setBufferSize(1024);
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			System.out.println("FTP连接成功");
			fos=new FileOutputStream("E:/test/2.doc");//下载到的目标地址
		
//			 downloadFile = new File("E:/test/123.txt");
//			if(downloadFile.exists())
//			{
//				downloadFile.delete();
//			}
			
			ftpClient.setBufferSize(1024);
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.retrieveFile("1.doc", fos);//下载xml
	
			//writePath(pathtxt,"E:/123.txt");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("FTP服务器连接失败",e);
		}

	}

}
