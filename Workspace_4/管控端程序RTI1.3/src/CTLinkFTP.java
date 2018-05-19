import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;

//import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.commons.net.ftp.FTPClient;

//import se.pitch.explorer.a.f;


public class CTLinkFTP {

    public static String server=null;//="172.16.73.108";   //输入的FTP服务器的IP地址
    public static  String user=null;//="admin";            //登录FTP服务器的用户名
    public static String password=null;//="admin";   //登录FTP服务器的用户名的口令
    public static  String path=null;//="E:\ftp";      //FTP服务器上的路径

    public CTLinkFTP() throws IOException{
//    	System.out.println("OK");
        String encording="UTF-8";
    	String filepath="FTP.txt";
    	File file=new File("FTP.txt");
    	InputStreamReader read=new InputStreamReader(new FileInputStream("FTP.txt"),encording);

//    	FileOutputStream fos=new FileOutputStream(file);
    	BufferedReader bufferedReader=new BufferedReader(read);
    	String linetext=null;
    	while((linetext=bufferedReader.readLine())!=null)
    	{
//    		System.out.println(linetext);
    		if(linetext.equals("server")){
    			server=bufferedReader.readLine();
    			System.out.println(server);
    		}
    		if(linetext.equals("user")){
    			user=bufferedReader.readLine();
//    			System.out.println(user);
    		}
    		if(linetext.equals("password")){
    			password=bufferedReader.readLine();
//    			System.out.println(password);
    		}
    		if(linetext.equals("path")){
    			path=bufferedReader.readLine();
//    			System.out.println(path);
    		}
    	}
    }
	public static FTPClient getConnection()
	{
		
		FTPClient ftpClient=new FTPClient();
		try {
//			System.out.println(server);
			ftpClient.connect(server);
//			System.out.println("test OK");
			ftpClient.login(user, password);
			ftpClient.setBufferSize(1024);
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			System.out.println("连接成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("FTP服务器连接失败",e);
		}

		return ftpClient;
	}
}
