/**
 * 
 */
package edu.jhun.dao;

import java.sql.Connection;
import java.sql.ResultSet;

import edu.jhun.bean.Attribute;
import edu.jhun.utils.C3P0Utils;

/**
 * @author Administrator
 * @time 2018年1月16日下午5:39:55
 *
 */
public class AttributeDAO extends CommonDAO{
	
	//通过属性ID找出属性输出信息
	public void findAttrOutput(String str) {
		String[] ss= new String[str.length()];
		ss=str.split("-");
//		for(int i=0;i<ss.length;i++){
//			System.out.println(ss[i]);
//		}
		try(Connection connection = C3P0Utils.getConnection()) {
			ResultSet rs = query(connection, "select * from datacollect0 where FederationId=? and MemberId=? and step between ? and ?;",ss[0],ss[1],ss[3],ss[4]);
			while (rs.next()) {
				Attribute attr = new Attribute();
				attr.setShemeId(ss[0]);
				attr.setMemberId(ss[1]);
				attr.setAttrId(ss[2]);
				attr.setStep(rs.getInt("step"));
				attr.setOutputvalue(rs.getDouble("Outputvalue"));
				System.out.println(rs.getRow()+" :"+attr.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
