/**
 * 
 */
package edu.jhun.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.jhun.bean.Member;
import edu.jhun.utils.C3P0Utils;

/**
 * @author Administrator
 * @time 2018年1月8日下午2:32:10
 *	成员类数据访问对象
 */
public class MemberDAO extends CommonDAO{
	
	//查询成员信息并输出
	/*public void findMemberInfo() {
		
		try(Connection conn = C3P0Utils.getConnection()) {
			ResultSet rs  = query(conn,"select * from datacollect0 where FederationId='1' and MemberId='1' and step between 1 and 50;");
			//System.out.println(rs.toString());
			while (rs.next()) {
				Member m = new Member();
				m.setMemberId(rs.getString("MemberId"));
				m.setOutputvalue(rs.getString("Outputvalue"));
				m.setStep(rs.getInt("step"));
				System.out.println(rs.getString("FederationId")+" :"+m.toString());
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}*/

}
