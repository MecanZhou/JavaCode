package psm.Models.BusinessModel;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.net.ftp.FTPClient;

public class FTPManage {
	public static String server = null;// ="172.16.73.108"; //�����FTP��������IP��ַ
	public static String user = null;// "admin"; //��¼FTP���������û���
	public static String password = null;// "admin"; //��¼FTP���������û����Ŀ���
	public static String path = null;// "E:\ftp"; //FTP�������ϵ�·��

	public static boolean uploadFileByApacheByBinary(String fileName) {
		FTPClient ftpClient = new FTPClient();
		try {

			String encording = "UTF-8";
			InputStreamReader read = new InputStreamReader(new FileInputStream(
					"src\\psm\\Image\\FTP.txt"), encording);
			BufferedReader bufferedReader = new BufferedReader(read);
			String linetext = null;
			while ((linetext = bufferedReader.readLine()) != null) {
				if (linetext.equals("server")) {
					server = bufferedReader.readLine();
				}
				if (linetext.equals("user")) {
					user = bufferedReader.readLine();
				}
				if (linetext.equals("password")) {
					password = bufferedReader.readLine();
				}
				if (linetext.equals("path")) {
					path = bufferedReader.readLine();
				}
			}
			InputStream is = null;
			// String encording="UTF-8";
			InputStreamReader read1 = new InputStreamReader(
					new FileInputStream("src\\psm\\FomOrScheme\\"+fileName), encording);
			BufferedReader bufferedReader1 = new BufferedReader(read1);
			String string = bufferedReader1.readLine();
			is = new ByteArrayInputStream(string.toString().getBytes());
			ftpClient.connect(server);
			ftpClient.login(user, password);
			ftpClient.changeWorkingDirectory(path);
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			File f = new File("E:\\ftp");

			if (f.exists()) {// f.delete();
				delFolder("E:\\ftp\\"+fileName); // ɾ���ļ�
			}
			f.mkdir();
			ftpClient.storeFile(new String(fileName.getBytes("GBK"),
					"iso-8859-1"), is);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	private static void delFolder(String folderPath) {
		// TODO Auto-generated method stub {
		try {
			delAllFile(folderPath); // ɾ����������������
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // ɾ�����ļ���

		} catch (Exception e) {
			System.out.println("ɾ���ļ��в�������");
			e.printStackTrace();

		}

	}

	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// ��ɾ���ļ���������ļ�
				delFolder(path + "/" + tempList[i]);// ��ɾ�����ļ���
			}
		}
	}
	


}
