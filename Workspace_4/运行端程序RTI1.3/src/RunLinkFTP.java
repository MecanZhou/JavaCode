import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Reader;

import java.net.InetAddress;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
FTP远程命令列表<br>
USER    PORT    RETR    ALLO    DELE    SITE    XMKD    CDUP    FEAT<br>
PASS    PASV    STOR    REST    CWD     STAT    RMD     XCUP    OPTS<br>
ACCT    TYPE    APPE    RNFR    XCWD    HELP    XRMD    STOU    AUTH<br>
REIN    STRU    SMNT    RNTO    LIST    NOOP    PWD     SIZE    PBSZ<br>
QUIT    MODE    SYST    ABOR    NLST    MKD     XPWD    MDTM    PROT<br>
在服务器上执行命令,如果用sendServer来执行远程命令(不能执行本地FTP命令)的话，所有FTP命令都要加上\r\n<br>
ftpclient.sendServer("XMKD /test/bb\r\n"); //执行服务器上的FTP命令<br>
ftpclient.readServerResponse一定要在sendServer后调用<br>
nameList("/test")获取指目录下的文件列表<br>
XMKD建立目录，当目录存在的情况下再次创建目录时报错<br>
XRMD删除目录<br>
DELE删除文件<br>
* 针对FTP中的所有调用使用到文件名的地方请使用完整的路径名（绝对路径开始）。
*/
public class RunLinkFTP implements Runnable {

    public static String server=null;//="172.16.73.108";   //输入的FTP服务器的IP地址
    public static  String user=null;//"admin";            //登录FTP服务器的用户名
    public static String password=null;//"admin";   //登录FTP服务器的用户名的口令
    public static  String path=null;//"E:\ftp";      //FTP服务器上的路径
    public static Statement st;
    public static String sql;
    public static String schemename;
    public static String schemeID;
    public static String modelID;
    public static String _path;

	public static ArrayList<String> route=new ArrayList<String>();
    public static ArrayList<String>dllpath=new ArrayList<String>();
    public static ArrayList<String>idList=new ArrayList<String>();
    
    public static ArrayList<String> getRoute() {
		return route; 
	}
	public static void setRoute(ArrayList<String> route) {
		RunLinkFTP.route = route;
	}
	
	public void run()
	{
		try {
			SelectScheme();		//确定本机
			System.out.println("run 方法");
			for(int i=0;i<route.size();i++)
			{
				try {
					//Thread.sleep(20000);
					String cmd="cmd /c start "+route.get(i);//创建一个控制台程序
					Runtime rt=Runtime.getRuntime();
					rt.exec(cmd);
					Thread.sleep(1000);
					
				}catch (Exception e) {
				// TODO: handle exception
				}
			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	} 
	
    public RunLinkFTP() throws IOException//读取FTP.txt里面的文件内容，用于+匹配FTP服务器的连接信息
    {
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
    			System.out.println("!server：....."+server);
    		}
    		if(linetext.equals("user")){
    			user=bufferedReader.readLine();
    		}
    		if(linetext.equals("password")){
    			password=bufferedReader.readLine();
    		}
    		if(linetext.equals("path")){
    			path=bufferedReader.readLine();
    		}
    	}
    }
	public static FTPClient getConnection()//FTP服务器的连接函数
	{
		FTPClient ftpClient=new FTPClient();
		
		try {
			ftpClient.connect(server);
			ftpClient.login(user, password);
			ftpClient.setBufferSize(1024);
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			System.out.println("FTP连接成功");

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("FTP服务器连接失败",e);
		}
		return ftpClient;
	}
	

	public static boolean SelectScheme() throws IOException//每一个运行端运行都会进行的初始化操作
	{
		Connection con=RunReadDB.getConnection();
		FileOutputStream fos=null;
		String remoteFilename=null;
		String pathtxt=null;
		String childpath=null;
		String schemeID=null;
		String schemename=null;
	
		try {
//			sql="select * from model_destribute where IP='"+InetAddress.getLocalHost().getHostAddress()+"'";
			sql="select * from model_destribute where IP='"+InetAddress.getLocalHost().getHostAddress()+"'";
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(sql);
			System.out.println(InetAddress.getLocalHost().getHostAddress());
			while(rs.next())
			{
				System.out.println("True Test......");
				idList.add(rs.getString(3));//将成员ID放入arraylist中
				schemeID=rs.getString(1);
				schemename=rs.getString(2);//获取方案名
				System.out.println("需要在本机中运行的成员ID： "+rs.getString(3));
				System.out.println("方案ID： "+schemeID);
			}
			try {
				File f=new File("C:\\HLA");
				if(!f.exists())		//此文件若不存在，则创建此文件目录
				{
					f.mkdir();
				}
				f=new File("C:\\HLA\\"+schemename);//以方案名在C盘HLA路径下创建文件夹
				if(f.exists())
				{
					delFolder("C:\\HLA\\"+schemename);		//删除文件
				}
				f.mkdir();
			} catch (Exception e) {
				// TODO: handle exception
			}
			remoteFilename="path.txt";
			pathtxt="C:/HLA/"+schemename+"/"+remoteFilename;
			_path=pathtxt;//将_path赋值为一个C盘方案中的一个路径变量
			
			for(int i=0;i<idList.size();i++)
			{
				modelID=idList.get(i);
				System.out.println("get ModeLID");

				Download(modelID, schemename,schemeID); 
			}
			
		} 

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return true;
		
	}

	public static void Download(String modelID,String schemename,String schemeID) throws SQLException//参数依次为成员ID，方案名和方案ID
	{	

		System.out.println("~~~~~~~~~~~OK");
//		FTPClient ftpClient=null;
		FTPClient ftpClient=getConnection();
			FileOutputStream fos=null;
			String remoteFilename=null;
			String pathtxt=_path;
			String childpath=null;
			Connection con=RunReadDB.getConnection();
			String SomName=null;
			String DllName=null;
			File downloadFile=null;

		  try {
			ftpClient.connect(server);
			ftpClient.login(user, password);
			System.out.println("schemename ->:"+schemename);
			ftpClient.changeWorkingDirectory("/sim-info/"+schemename+"/Model"+modelID);
			ftpClient.changeWorkingDirectory("");
			remoteFilename="path.txt";

			File file=new File("C:\\HLA\\"+schemename+"\\"+remoteFilename);//在方案下创建path.txt文件
			if(file.exists())
			{
				file.delete();
			}
			file.createNewFile();
			try {
				File f=new File("C:/HLA/"+schemename+"/Model"+modelID);//在路径C盘HLA下创建新的model的文件夹
				if(f.exists())
				{
//					System.out.println("OK");
					f.delete();
				}
				f.mkdir();
			} catch (Exception e) {
				// TODO: handle exception
			}
			ftpClient.disconnect();
			try {
				ftpClient.connect(server);
				ftpClient.login(user, password);
				remoteFilename=schemename+"Som.xml";
				System.out.println("开始下载"+remoteFilename+"......");
				SomName=remoteFilename;

				fos=new FileOutputStream("C:/HLA/"+schemename+"/Model"+modelID+"/"+remoteFilename);//下载Som文件至C盘中的运行文件中

				childpath="C:/HLA/"+schemename+"/Model"+modelID+"/"+remoteFilename;
				downloadFile=new File(childpath);
				if(downloadFile.exists())
				{
					downloadFile.delete();
				}
				

				ftpClient.setBufferSize(1024);
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
				ftpClient.retrieveFile(remoteFilename, fos);//下载xml，前面的参数是文件名，后面的参数是文件流
				System.out.println("testing......"+pathtxt+"......"+childpath);
				writePath(pathtxt, childpath);//下载文件，前一个参数是下载到的文件路径，后面是要下载文件的文件路径
				System.out.println("下载som成功......");
//				ftpClient.quit();
				ftpClient.disconnect();
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				ftpClient.connect(server);
				ftpClient.login(user, password);
				remoteFilename="CommFRTI1.3.jar";
				System.out.println("开始下载"+remoteFilename+"......");
				fos=new FileOutputStream("C:/HLA/"+schemename+"/Model"+modelID+"/"+remoteFilename);
				childpath="C:/HLA/"+schemename+"/Model"+modelID+"/"+remoteFilename;
				downloadFile=new File(childpath);
				if(downloadFile.exists())
				{
					downloadFile.delete();
				}
				ftpClient.setBufferSize(1024);
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
				ftpClient.retrieveFile(remoteFilename, fos);//下载COMF的jar包
				writePath(pathtxt, childpath);
				System.out.println("下载CommFRTI1.3成功......");
				ftpClient.disconnect();
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {//下载ServerIP文件，其中存放的是Start。bat里面的仿真联邦的IP地址
				ftpClient.connect(server);
				ftpClient.login(user, password);

				remoteFilename="ServerIP.txt";	//sourceFilename是要被下载的文件
				System.out.println("开始下载"+remoteFilename+"......");
				fos=new FileOutputStream("C:/HLA/"+schemename+"/Model"+modelID+"/"+remoteFilename);
				childpath="C:/HLA/"+schemename+"/Model"+modelID+"/"+remoteFilename;
				downloadFile=new File(childpath);
				if(downloadFile.exists())
				{
					downloadFile.delete();
				}
				ftpClient.setBufferSize(1024);
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
				ftpClient.retrieveFile(remoteFilename, fos);//下载COMMF.EXE
				writePath(pathtxt, childpath);
				System.out.println("下载ServerIP.txt成功......");
			} catch (Exception e) {
				// TODO: handle exception
			}

			try {
				sql="select COM_NAME from scheme_model_info where SCHEME_MODEL_ID="+modelID+" AND SCHEME_ID="+schemeID;
				System.out.println(sql);
				st=(Statement)con.createStatement();
				ResultSet rs=st.executeQuery(sql);

				while(rs.next())
				{
					remoteFilename=rs.getString(1);
					
					System.out.println("$$$$$$$"+remoteFilename);
				}
				String[] dllname=remoteFilename.split("-");
				for(int i=0;i<dllname.length;i++)
				{
				ftpClient.connect(server);
				ftpClient.login(user, password);
				System.out.println("开始下载"+remoteFilename+"......"+ftpClient.isConnected());
				

					System.out.println("dllname.length  "+dllname.length);
				DllName=dllname[i];
				fos=new FileOutputStream("C:/HLA/"+schemename+"/Model"+modelID+"/"+DllName);
				childpath="C:/HLA/"+schemename+"/Model"+modelID+"/"+DllName;
				System.out.println("!!!!!"+childpath);

				ftpClient.setBufferSize(1024);
				System.out.println("OK");
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

				ftpClient.retrieveFile(DllName, fos);//下载dll
				ftpClient.disconnect();
				
				writePath(pathtxt, childpath); 
				
				System.out.println("dll下载成功......"+ftpClient.isConnected());
				fos.close();
				}

			} catch (Exception e) {
				// TODO: handle exception 
			}

			
			try {
				ftpClient.connect(server);
				ftpClient.login(user, password);
				String batpath="C:/HLA/"+schemename+"/Model"+modelID;
				childpath="C:/HLA/"+schemename+"/Model"+modelID+"/"+"start.bat";
				downloadFile=new File(childpath);
				if(downloadFile.exists())
				{
					downloadFile.delete();
				}
				downloadFile.createNewFile();
				BufferedWriter output=new BufferedWriter(new FileWriter(downloadFile));
				
				String encording="UTF-8";
//		    	String filepath=batpath+"/ServerIP.txt";
//		    	//InputStreamReader read1=new InputStreamReader(new FileInputStream(filepath),encording);
//		    	FileReader fr=new FileReader(filepath);
//		    	String ch=null;
//		    	ch=fr.toString();
//		    	System.out.println(ch);
				File file1=new File(batpath+"/ServerIP.txt");
//				File file1=new File("ServerIP.txt");
				BufferedReader reader=new BufferedReader(new FileReader(file1));
				String ServerIP=reader.readLine();
				System.out.println(ServerIP);
		    	
				//制作。bat文件，网其中写入通用成员运行需要的参数
		    	InputStreamReader read=new InputStreamReader(new FileInputStream("FTP.txt"),encording);
				output.write("for %%i in (%0) do set aa=%%~dpi \r\n");
				output.write("cd %aa% \r\n");
				output.write("java -jar CommFRTI1.3.jar "+modelID+" "+batpath+"/"+SomName+" "+batpath+"/"+DllName+" "+ DllName+" "+ServerIP+"\r\n");
				output.close();
				

				System.out.println("bat 制作完成");
				writePath(pathtxt, childpath);
				System.out.println("11111");
				writePath(pathtxt, "\r\n");
				System.out.println("22222");

				ftpClient.disconnect();
				System.out.println("33333");

//				ftpClient.quit();
				System.out.println("44444");

			sql="delete from Model_Destribute where Model_ID= '"+modelID+"'";//将当前成员在Model_Destribute表中的信息删除
			System.out.println("55555");

			st=con.createStatement();
			System.out.println("66666");

			st.executeUpdate(sql);
			System.out.println("77777");

			route.add(childpath);
			System.out.println("88888");

			System.out.println("删除数据库临时表成功......");
			System.out.println("999999");

			Thread.sleep(1000);

			}
			catch (Exception e) {
				// TODO: handle exception
			}
		  }catch (Exception e) {
			// TODO: handle exception
		}



		

	}

	public  static void  delFolder(String  folderPath)  {    
	       try  {    
	           delAllFile(folderPath);  //删除完里面所有内容    
	           String  filePath  =  folderPath;    
	           filePath  =  filePath.toString();    
	           java.io.File  myFilePath  =  new  java.io.File(filePath);    
	           myFilePath.delete();  //删除空文件夹    
	   
	       }    
	       catch  (Exception  e)  {    
	           System.out.println("删除文件夹操作出错");    
	           e.printStackTrace();    
	   
	       }    
	   
	   }    
	   
	   /**    
	     *  删除文件夹里面的所有文件   
	     *  @param  path  String  文件夹路径  如  c:/fqf   
	     */    
	   public  static void  delAllFile(String  path)  {    
	       File  file  =  new  File(path);    
	       if  (!file.exists())  {    
	           return;    
	       }    
	       if  (!file.isDirectory())  {    
	           return;    
	       }    
	       String[]  tempList  =  file.list();    
	       File  temp  =  null;    
	       for  (int  i  =  0;  i  <  tempList.length;  i++)  {    
	           if  (path.endsWith(File.separator))  {    
	               temp  =  new  File(path  +  tempList[i]);    
	           }    
	           else  {    
	               temp  =  new  File(path  +  File.separator  +  tempList[i]);    
	           }    
	           if  (temp.isFile())  {    
	               temp.delete();    
	           }    
	           if  (temp.isDirectory())  {    
	               delAllFile(path+"/"+  tempList[i]);//先删除文件夹里面的文件    
	               delFolder(path+"/"+  tempList[i]);//再删除空文件夹    
	           }    
	       }    
	   }    

	public static void writePath(String path,String childpath) throws IOException//实现下载文件的函数
	{
		String filePath=path;
		String cpath=childpath;
		String encording="UTF-8";
		String lineTxt=null;

		RandomAccessFile mm=null;
		File file=new File(filePath);
		InputStreamReader read=new InputStreamReader(new FileInputStream(file),encording);
		BufferedReader bufferedReader=new BufferedReader(read);
		mm=new RandomAccessFile(filePath, "rw");
		while((lineTxt=bufferedReader.readLine())!=null){
			
			mm.writeBytes(lineTxt+"\r\n");		
			
		}
		{
			mm.writeBytes(cpath+"\r\n");
		}
		
		bufferedReader.close();
		read.close();
		mm.close();
		
	}
	
}		  



