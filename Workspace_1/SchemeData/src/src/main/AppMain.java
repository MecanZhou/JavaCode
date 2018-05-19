package src.main;

import java.sql.SQLException;

/*
 * 方案数据管理主函数
 */

import java.util.Scanner;

public class AppMain {
	
	
		
	public void chooseService(int n) throws SQLException{
		SchemeService service= new SchemeService();
		if (n==1) {
			System.out.println("ChooseFunction(1)");
			System.out.println("最大值："+service.findMax("SIMU_SCHEME"));
		} else if(n==2){
			System.out.println("ChooseFunction(2)");
			System.out.println("getScheme_DESC_Info():");
			service.getScheme_DESC_Info();
		}
	}

	public static void main(String[] args) throws SQLException {
		
		AppMain appMain = new AppMain();
		String sqlName="admin";
		String password="root";	
		DBExecute dbe = new DBExecute();
		dbe.connectDB(sqlName, password);
		System.out.println("plz choose Service you need:\n"+
		"1.fun(1)\t\t\t\t2.fun(2)\t\t\t\t0.Exit\n");
		
		Scanner scanner= new Scanner(System.in);
		int n= scanner.nextInt();
		
		while(n!=0){
			appMain.chooseService(n);
			System.out.println("plz choose function you need:\n"+
					"1.fun(1)\t\t\t\t2.fun(2)\t\t\t\t0.Exit\n");
			n= scanner.nextInt();
		}
		dbe.closeDB(sqlName);
		
	}

}
