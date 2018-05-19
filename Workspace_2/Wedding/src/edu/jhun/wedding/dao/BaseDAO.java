package edu.jhun.wedding.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ���ݷ��ʸ���
 * @author Administrator
 *
 */
public class BaseDAO {

	/**
	 * ִ����ɾ��
	 * @param sql
	 * @param params
	 * @return
	 */
	public boolean update(String sql,Object... params){
		//ͨ�����ӳػ�����ݿ�����
		try(Connection conn = C3P0Utils.getConnection()){
			PreparedStatement ps = conn.prepareStatement(sql);
			for(int i = 0;i < params.length;i++){
				ps.setObject(i + 1, params[i]);
			}
			int rows = ps.executeUpdate();
			return rows > 0;
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		return false;
	}
	
	/**
	 * ִ�в�ѯ
	 * @param conn ���ݿ�����
	 * @param sql
	 * @param params
	 * @return �������������ѯ�������ݣ�
	 */
	public ResultSet query(Connection conn,String sql,Object... params){
		//ͨ�����ӳػ�����ݿ�����
		try{
			PreparedStatement ps = conn.prepareStatement(sql);
			for(int i = 0;i < params.length;i++){
				ps.setObject(i + 1, params[i]);
			}
			ResultSet rs = ps.executeQuery();
			return rs;
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		return null;
	}
}
