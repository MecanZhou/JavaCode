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
FTPԶ�������б�<br>
USER    PORT    RETR    ALLO    DELE    SITE    XMKD    CDUP    FEAT<br>
PASS    PASV    STOR    REST    CWD     STAT    RMD     XCUP    OPTS<br>
ACCT    TYPE    APPE    RNFR    XCWD    HELP    XRMD    STOU    AUTH<br>
REIN    STRU    SMNT    RNTO    LIST    NOOP    PWD     SIZE    PBSZ<br>
QUIT    MODE    SYST    ABOR    NLST    MKD     XPWD    MDTM    PROT<br>
�ڷ�������ִ������,�����sendServer��ִ��Զ������(����ִ�б���FTP����)�Ļ�������FTP���Ҫ����\r\n<br>
ftpclient.sendServer("XMKD /test/bb\r\n"); //ִ�з������ϵ�FTP����<br>
ftpclient.readServerResponseһ��Ҫ��sendServer�����<br>
nameList("/test")��ȡָĿ¼�µ��ļ��б�<br>
XMKD����Ŀ¼����Ŀ¼���ڵ�������ٴδ���Ŀ¼ʱ����<br>
XRMDɾ��Ŀ¼<br>
DELEɾ���ļ�<br>
* ���FTP�е����е���ʹ�õ��ļ����ĵط���ʹ��������·����������·����ʼ����
*/
public class RunLinkFTP implements Runnable {

    public static String server=null;//="172.16.73.108";   //�����FTP��������IP��ַ
    public static  String user=null;//"admin";            //��¼FTP���������û���
    public static String password=null;//"admin";   //��¼FTP���������û����Ŀ���
    public static  String path=null;//"E:\ftp";      //FTP�������ϵ�·��
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
			SelectScheme();		//ȷ������
			System.out.println("run ����");
			for(int i=0;i<route.size();i++)
			{
				try {
					//Thread.sleep(20000);
					String cmd="cmd /c start "+route.get(i);//����һ������̨����
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
	
    public RunLinkFTP() throws IOException//��ȡFTP.txt������ļ����ݣ�����+ƥ��FTP��������������Ϣ
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
    			System.out.println("!server��....."+server);
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
	public static FTPClient getConnection()//FTP�����������Ӻ���
	{
		FTPClient ftpClient=new FTPClient();
		
		try {
			ftpClient.connect(server);
			ftpClient.login(user, password);
			ftpClient.setBufferSize(1024);
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			System.out.println("FTP���ӳɹ�");

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("FTP����������ʧ��",e);
		}
		return ftpClient;
	}
	

	public static boolean SelectScheme() throws IOException//ÿһ�����ж����ж�����еĳ�ʼ������
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
				idList.add(rs.getString(3));//����ԱID����arraylist��
				schemeID=rs.getString(1);
				schemename=rs.getString(2);//��ȡ������
				System.out.println("��Ҫ�ڱ��������еĳ�ԱID�� "+rs.getString(3));
				System.out.println("����ID�� "+schemeID);
			}
			try {
				File f=new File("C:\\HLA");
				if(!f.exists())		//���ļ��������ڣ��򴴽����ļ�Ŀ¼
				{
					f.mkdir();
				}
				f=new File("C:\\HLA\\"+schemename);//�Է�������C��HLA·���´����ļ���
				if(f.exists())
				{
					delFolder("C:\\HLA\\"+schemename);		//ɾ���ļ�
				}
				f.mkdir();
			} catch (Exception e) {
				// TODO: handle exception
			}
			remoteFilename="path.txt";
			pathtxt="C:/HLA/"+schemename+"/"+remoteFilename;
			_path=pathtxt;//��_path��ֵΪһ��C�̷����е�һ��·������
			
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

	public static void Download(String modelID,String schemename,String schemeID) throws SQLException//��������Ϊ��ԱID���������ͷ���ID
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

			File file=new File("C:\\HLA\\"+schemename+"\\"+remoteFilename);//�ڷ����´���path.txt�ļ�
			if(file.exists())
			{
				file.delete();
			}
			file.createNewFile();
			try {
				File f=new File("C:/HLA/"+schemename+"/Model"+modelID);//��·��C��HLA�´����µ�model���ļ���
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
				System.out.println("��ʼ����"+remoteFilename+"......");
				SomName=remoteFilename;

				fos=new FileOutputStream("C:/HLA/"+schemename+"/Model"+modelID+"/"+remoteFilename);//����Som�ļ���C���е������ļ���

				childpath="C:/HLA/"+schemename+"/Model"+modelID+"/"+remoteFilename;
				downloadFile=new File(childpath);
				if(downloadFile.exists())
				{
					downloadFile.delete();
				}
				

				ftpClient.setBufferSize(1024);
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
				ftpClient.retrieveFile(remoteFilename, fos);//����xml��ǰ��Ĳ������ļ���������Ĳ������ļ���
				System.out.println("testing......"+pathtxt+"......"+childpath);
				writePath(pathtxt, childpath);//�����ļ���ǰһ�����������ص����ļ�·����������Ҫ�����ļ����ļ�·��
				System.out.println("����som�ɹ�......");
//				ftpClient.quit();
				ftpClient.disconnect();
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				ftpClient.connect(server);
				ftpClient.login(user, password);
				remoteFilename="CommFRTI1.3.jar";
				System.out.println("��ʼ����"+remoteFilename+"......");
				fos=new FileOutputStream("C:/HLA/"+schemename+"/Model"+modelID+"/"+remoteFilename);
				childpath="C:/HLA/"+schemename+"/Model"+modelID+"/"+remoteFilename;
				downloadFile=new File(childpath);
				if(downloadFile.exists())
				{
					downloadFile.delete();
				}
				ftpClient.setBufferSize(1024);
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
				ftpClient.retrieveFile(remoteFilename, fos);//����COMF��jar��
				writePath(pathtxt, childpath);
				System.out.println("����CommFRTI1.3�ɹ�......");
				ftpClient.disconnect();
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {//����ServerIP�ļ������д�ŵ���Start��bat����ķ��������IP��ַ
				ftpClient.connect(server);
				ftpClient.login(user, password);

				remoteFilename="ServerIP.txt";	//sourceFilename��Ҫ�����ص��ļ�
				System.out.println("��ʼ����"+remoteFilename+"......");
				fos=new FileOutputStream("C:/HLA/"+schemename+"/Model"+modelID+"/"+remoteFilename);
				childpath="C:/HLA/"+schemename+"/Model"+modelID+"/"+remoteFilename;
				downloadFile=new File(childpath);
				if(downloadFile.exists())
				{
					downloadFile.delete();
				}
				ftpClient.setBufferSize(1024);
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
				ftpClient.retrieveFile(remoteFilename, fos);//����COMMF.EXE
				writePath(pathtxt, childpath);
				System.out.println("����ServerIP.txt�ɹ�......");
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
				System.out.println("��ʼ����"+remoteFilename+"......"+ftpClient.isConnected());
				

					System.out.println("dllname.length  "+dllname.length);
				DllName=dllname[i];
				fos=new FileOutputStream("C:/HLA/"+schemename+"/Model"+modelID+"/"+DllName);
				childpath="C:/HLA/"+schemename+"/Model"+modelID+"/"+DllName;
				System.out.println("!!!!!"+childpath);

				ftpClient.setBufferSize(1024);
				System.out.println("OK");
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);

				ftpClient.retrieveFile(DllName, fos);//����dll
				ftpClient.disconnect();
				
				writePath(pathtxt, childpath); 
				
				System.out.println("dll���سɹ�......"+ftpClient.isConnected());
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
		    	
				//������bat�ļ���������д��ͨ�ó�Ա������Ҫ�Ĳ���
		    	InputStreamReader read=new InputStreamReader(new FileInputStream("FTP.txt"),encording);
				output.write("for %%i in (%0) do set aa=%%~dpi \r\n");
				output.write("cd %aa% \r\n");
				output.write("java -jar CommFRTI1.3.jar "+modelID+" "+batpath+"/"+SomName+" "+batpath+"/"+DllName+" "+ DllName+" "+ServerIP+"\r\n");
				output.close();
				

				System.out.println("bat �������");
				writePath(pathtxt, childpath);
				System.out.println("11111");
				writePath(pathtxt, "\r\n");
				System.out.println("22222");

				ftpClient.disconnect();
				System.out.println("33333");

//				ftpClient.quit();
				System.out.println("44444");

			sql="delete from Model_Destribute where Model_ID= '"+modelID+"'";//����ǰ��Ա��Model_Destribute���е���Ϣɾ��
			System.out.println("55555");

			st=con.createStatement();
			System.out.println("66666");

			st.executeUpdate(sql);
			System.out.println("77777");

			route.add(childpath);
			System.out.println("88888");

			System.out.println("ɾ�����ݿ���ʱ��ɹ�......");
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
	           delAllFile(folderPath);  //ɾ����������������    
	           String  filePath  =  folderPath;    
	           filePath  =  filePath.toString();    
	           java.io.File  myFilePath  =  new  java.io.File(filePath);    
	           myFilePath.delete();  //ɾ�����ļ���    
	   
	       }    
	       catch  (Exception  e)  {    
	           System.out.println("ɾ���ļ��в�������");    
	           e.printStackTrace();    
	   
	       }    
	   
	   }    
	   
	   /**    
	     *  ɾ���ļ�������������ļ�   
	     *  @param  path  String  �ļ���·��  ��  c:/fqf   
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
	               delAllFile(path+"/"+  tempList[i]);//��ɾ���ļ���������ļ�    
	               delFolder(path+"/"+  tempList[i]);//��ɾ�����ļ���    
	           }    
	       }    
	   }    

	public static void writePath(String path,String childpath) throws IOException//ʵ�������ļ��ĺ���
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



