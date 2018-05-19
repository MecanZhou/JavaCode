import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;


public class CTCreateFed {
	public static String fedName;
	public static ArrayList<String>value=new ArrayList<String>();
	public CTCreateFed(String text) throws IOException
	{
//		File fname=new File("E:/HLA/chat.fed");
////		System.out.println(fname.exists());
//		try {
//			fname.createNewFile();
//		} catch (IOException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
		fedName=text;
		String remotename="FED.xml";
		File file=new File(remotename);
		if(file.exists())
		{
			file.delete();
		}
		file.createNewFile();

		CTLinkFTP ftp=new CTLinkFTP();
		System.out.println("OK");
//		FTPClient ftpClient=ftp.getConnection();
		FileOutputStream fos=new FileOutputStream(remotename);
		ftp.getConnection().retrieveFile(remotename, fos);
		fos.close();
		System.out.println("下载Fed OK");
//		file=new File();
//		if(file.)
		File f=new File("");
		String a=f.getCanonicalPath();
		
//		System.out.println("执行到这一步："+a);
	
		File f2=new File(a);
//		System.out.println(f2.getCanonicalPath());
		String list[]=f2.list();
		
		System.out.println(list.length);
		for(int i=0;i<list.length;i++)
		{
			if(list[i].contains("Fom"))
			{
//				System.out.println(list[i]);
				new File(list[i]).delete();
//				System.out.println("处理成功！！~~~");
//				fff.
			}
			if(list[i].contains("Som"))
			{
//				System.out.println(list[i]);
				new File(list[i]).delete();
//				System.out.println("处理成功！！~~~");
//				fff.
			}
		}
		fos=new FileOutputStream(fedName+"Fom.xml");
		ftp.getConnection().retrieveFile(fedName+"Fom.xml", fos);
		fos.close();
		
		System.out.println("下载Fom OK");
		fos=new FileOutputStream(fedName+"Som.xml");
		ftp.getConnection().retrieveFile(fedName+"Som.xml", fos);
		System.out.println("下载Som OK");
		fos.close();
		ftp.getConnection().quit();
		CTReadFOM fom=new CTReadFOM(fedName);

//		readTextFile();
	}
//	private void readTextFile() throws IOException {
//		// TODO Auto-generated method stub
//		String filePath="E:/download/chat.fed";
//		String encording="UTF-8";
//		File file=new File(filePath);
//		if(file.exists())
//		{
//			file.delete();
//		}
//		else{
//			file.createNewFile();
//		}
//		try {
////			System.out.println(file.isFile());
//			if(file.isFile()&&file.exists())
//			{
//				InputStreamReader read=new InputStreamReader(new FileInputStream(filePath),encording);
//				String lineTxt=null;
//				BufferedReader bufferedReader=new BufferedReader(read);
//				RandomAccessFile mm=null;
//				File f=new File("E:/HLA");
//				if(f.exists())
//				{
//					f.delete();
//				}
//				f.mkdir();
//				f=new File("E:/HLA/chat.fed");
//				if(f.exists())
//				{
//					f.delete();
//				}
//				f.createNewFile();
//				mm=new RandomAccessFile("E:/HLA/chat.fed", "rw");
//				value=CTReadFOM.ReadXML();
////				System.out.println(bufferedReader.readLine());
//				while((lineTxt=bufferedReader.readLine())!=null){
//			
//					if(lineTxt.equals("//"))
//					{
//						for(int i=0;i<value.size();i++)
//						{
//							mm.writeBytes("(class "+value.get(i)+")\r\n");
//
//						}
//					}
//					mm.writeBytes(lineTxt+"\r\n");
//				}
//				bufferedReader.close();
//				read.close();
//			}else{
//				System.out.println("File not exist!");
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			System.out.println("读取文件内容错误");
//			e.printStackTrace();
//		}
//	}

}
