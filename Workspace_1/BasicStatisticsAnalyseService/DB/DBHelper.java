ackage simulationDataAcquisition;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.sql.DriverManager;
import com.mysql.jdbc.Connection;
/**
 * 
 * @author yc
 * 鑾峰彇鏁版嵁搴撹繛鎺ワ紝杩斿洖杩炴帴瀵硅薄
 * 
 *
 */
public class DBHelper {
	static boolean DBflag=true;//榛樿杩炴帴ORACLE鐨勬暟鎹簱
	static String DBType;
	public static void main(String[] args)
	{
		DBHelper db=new DBHelper();
		Connection con=db.getConnection();
	}
	public static Connection getConnection()
	{
		Connection con = null;
		try {
			String encording="UTF-8";
			String lineTxt=null;
			String serverIP=null;
			String username=null;
			String password=null;
			String database=null;
			File file=new File("DBType.txt");
			InputStreamReader read=new InputStreamReader(new FileInputStream(file),encording);
			//			FileOutputStream fos=new FileOutputStream(file);
			BufferedReader bufferedReader=new BufferedReader(read);//璇诲彇鏁版嵁搴撶被鍨嬬殑鏂囦欢娴�
			while((lineTxt=bufferedReader.readLine())!=null)
			{
				if(lineTxt.equals("DBType"))
				{
					String Style=bufferedReader.readLine();
					if(Style.equalsIgnoreCase("MYSQL"))
					{
						DBType="MYSQL";
						DBflag=false;
						System.out.println("鏁版嵁搴撶被鍨嬩负MySql");
					}
					else if(Style.equalsIgnoreCase("ORACLE"))
					{
						DBType="ORACLE";
						System.out.println("鏁版嵁搴撶被鍨嬩负Oracle");
					}
				}
			}
			bufferedReader.close();
			if(DBflag==true)//杩炴帴鐨勬槸Oracle 鐨勬暟鎹簱
			{
				File file1=new File("OracleDBset.txt");
				InputStreamReader read1=new InputStreamReader(new FileInputStream(file1),encording);
				BufferedReader bufferedReader1=new BufferedReader(read1);
				while((lineTxt=bufferedReader1.readLine())!=null)
				{		
					if(lineTxt.equals("ServerIP")){
						serverIP=bufferedReader1.readLine();
						System.out.println("DB_IP: "+serverIP);
					}
					if(lineTxt.equals("username")){
						username=bufferedReader1.readLine();
						System.out.println(username);
					}
					if(lineTxt.equals("password")){
						password=bufferedReader1.readLine();
						System.out.println(password);
					}
				}
			}
			else//杩炴帴鐨勬槸MYSQL 鐨勬暟鎹簱
			{
				File file1=new File("MySQLDBset.txt");
				InputStreamReader read1=new InputStreamReader(new FileInputStream(file1),encording);
				BufferedReader bufferedReader1=new BufferedReader(read1);
				while((lineTxt=bufferedReader1.readLine())!=null)
				{		
					if(lineTxt.equals("ServerIP")){
						serverIP=bufferedReader1.readLine();
						System.out.println("DB_IP: "+serverIP);
					}
					if(lineTxt.equals("username")){
						username=bufferedReader1.readLine();
						//System.out.println(username);
					}
					if(lineTxt.equals("password")){
						password=bufferedReader1.readLine();
						//System.out.println(password);
					}
					if(lineTxt.equals("database")){
						database=bufferedReader1.readLine();
						//System.out.println(database);
					}
				}
			}
			if(DBflag==true)//杩炴帴鐨勬槸Oracle鐨勬暟鎹簱
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con = (Connection) DriverManager.getConnection("jdbc:oracle:thin:@"+serverIP+":1521:xe", username, password);
				System.out.println("鏁版嵁搴揙racle杩炴帴鎴愬姛");
			}
			else//杩炴帴鐨勬槸MySql 鐨勬暟鎹簱
			{
				//System.out.println("LocalHostIP :"+InetAddress.getLocalHost().getHostAddress());
				Class.forName("com.mysql.jdbc.Driver");
				con=(Connection) DriverManager.getConnection("jdbc:mysql://"+serverIP+":3306/"+database,username,password);
				System.out.println("鏁版嵁搴揗ySql杩炴帴鎴愬姛");
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("杩炴帴鏁版嵁搴撳け璐�"+e.getMessage());
		}
		return con;
	}

}

