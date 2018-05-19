package src.main;

/*
 * 方案管理服务类
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SchemeService {	
	
	public void getScheme_DESC_Info() throws SQLException {
		DBExecute dbe = new DBExecute();
		String sql = "select * from SIMU_SCHEME";
		ResultSet rs= dbe.ExecuteQuery(sql);
		int col = rs.getMetaData().getColumnCount();
        System.out.println("============================");
        for(int c=1;c<=col;c++){
        	if (c==3||c==4) {
        		System.out.print(rs.getMetaData().getColumnName(c)+"\t\t");
			} else {
				System.out.print(rs.getMetaData().getColumnName(c)+"\t");
			}
        	
        }
        System.out.println();
        while (rs.next()) {
        	
            for (int i = 1; i <= col; i++) {
            	if (rs.getMetaData().getColumnName(i).equals("BEGIN_TIME")||
            			rs.getMetaData().getColumnName(i).equals("END_TIME")) {
            		System.out.printf(rs.getString(i) + "\t");
				} else {
					System.out.printf(rs.getString(i) + "\t\t");
				}
                
             }
            System.out.println("");
        }
            System.out.println("============================");
            
	}
	
	public static int findMax(String table) throws SQLException
	   {
	       List<Integer> ID = new ArrayList<Integer>();
	       DBExecute dbe = new DBExecute();
	       String sql="select * from "+table;
	       ResultSet rs=dbe.ExecuteQuery(sql);
	       while(rs.next())
	       {
	    	   ID.add(Integer.valueOf(rs.getString("SCHEME_ID")));
	       }
	       int max = ID.get(0);
	       for (int i = 0; i < ID.size(); i++)
	       {
	           if (ID.get(i) > max)
	           {
	               max = ID.get(i);
	           }
	       }
	       System.out.print(max);
	       return max;
	   }

}
