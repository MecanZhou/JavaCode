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
 * @time 2018��1��8������2:32:10
 *	��Ա�����ݷ��ʶ���
 */
public class MemberDAO extends CommonDAO{
	
	//��ѯ��Ա��Ϣ�����
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
