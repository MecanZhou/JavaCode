package src.main;

/*
 * 数据库操作执行类
 */

import java.sql.*;

public class DBExecute {
	
	public static String url= "jdbc:mysql://localhost:3306/scenariodb";
	static Connection connection;
	
	public void connectDB(String sqlName, String password) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("无法加载数据库驱动");
			e.printStackTrace();
			
		}
		try {
			connection = DriverManager.getConnection(url, sqlName, password);
			System.out.println("数据库连接成功");
		} catch (SQLException e) {
			System.out.println("创建数据库出错");
			e.printStackTrace();
		}
	}
	
	public void closeDB(String sqlName) throws SQLException{
		connection.close();
	}
	
	public ResultSet ExecuteQuery(String sql) throws SQLException{
		
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
//		int col = rs.getMetaData().getColumnCount();
//        System.out.println("============================");
//        for(int c=1;c<=col;c++){
//        	System.out.print(rs.getMetaData().getColumnName(c)+"\t");
//        }
//        System.out.println();
//        while (rs.next()) {
//        	
//            for (int i = 1; i <= col; i++) {
//                System.out.printf(rs.getString(i) + "\t");
//                if ((i == 2) && (rs.getString(i).length() < 8)) {
//                    System.out.print("\t");
//                }
//             }
//            System.out.println("");
//        }
//            System.out.println("============================");

//		return null;
		
//		PreparedStatement pstmt;
//	    try {
//	        pstmt = (PreparedStatement)connection.prepareStatement(sql);
//	        ResultSet rs = pstmt.executeQuery();
//	        int col = rs.getMetaData().getColumnCount();
//	        System.out.println("============================");
//	        while (rs.next()) {
//	            for (int i = 1; i <= col; i++) {
//	                System.out.print(rs.getString(i) + "\t");
//	                if ((i == 2) && (rs.getString(i).length() < 8)) {
//	                    System.out.print("\t");
//	                }
//	             }
//	            System.out.println("");
//	        }
//	            System.out.println("============================");
//	    	} catch (SQLException e) {
//	    		System.out.println("数据输出异常！");
//	        e.printStackTrace();
//	    	}
//	    connection.close();
	    return rs;
		
	}
}
